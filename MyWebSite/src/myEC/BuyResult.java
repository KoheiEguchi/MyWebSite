package myEC;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Buy;
import beans.Item;
import beans.User;
import dao.BuyDAO;
import dao.ItemDAO;

/**
 * Servlet implementation class BuyResult
 */
@WebServlet("/BuyResult")
public class BuyResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインしていないユーザーはログインページへ移行
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//想定されていない接続方法で来た場合トップページを表示
		RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			//カゴのリストを参照
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			//購入情報のセッションを取得
			Buy buy = (Buy) session.getAttribute("buy");

			//カゴ内の商品一つ一つにつきDAOで記録
			for (Item item : cart) {
				//購入者、総額、配送方法、購入日時を記録
				int buyId = BuyDAO.insertBuy(buy);

				Buy buyItem = new Buy();
				buyItem.setItemId(item.getId());
				buyItem.setCount(item.getCount());
				buyItem.setId(buyId);

				//購入商品、個数を記録
				BuyDAO.insertBuyItem(buyItem);

				//今回までの商品の販売個数を取得
				Item sold = BuyDAO.soldCheck(buyItem);
				//商品の販売個数を更新
				ItemDAO.insertSold(sold);
			}

			//DAOで一度に購入した商品をまとめる
			ArrayList<Buy> buyIdList = BuyDAO.buyIdSelect();
			//購入記録に共通の番号を振る
			Buy buyId = buyIdList.get(0);

			//番号の被り防止
			User user = (User)session.getAttribute("user");
			int userId = user.getId();

			BuyDAO.insertBuyId(buyId, userId);


			//新たに購入履歴からおすすめ商品を取得
			ArrayList<Item>itemList = ItemDAO.recommendItem(userId);
			request.setAttribute("itemList", itemList);

			//カゴのセッションを削除
			session.removeAttribute("cart");

			//購入結果ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/buyresult.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
