package myEC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Item;
import beans.User;
import dao.BuyDAO;

/**
 * Servlet implementation class OrderDetail
 */
@WebServlet("/OrderDetail")
public class OrderDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインしていないユーザーはログインページへ移行
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null){
			request.setAttribute("errMsg", "ログインしてください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//管理者以外のユーザーはトップページを表示
		User user = (User)session.getAttribute("user");
		int adminCheck = user.getId();
		if(adminCheck != 1) {
			request.setAttribute("errMsg", "管理者以外はアクセスできません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		//各情報を取得
		String strBuyerId = request.getParameter("buyerId");
		String strBuyId = request.getParameter("buyId");
		String buyDate = request.getParameter("buyDate");
		String buyTime = request.getParameter("buyTime");

		//想定されていない接続方法で来た場合管理用トップページを表示
		if(strBuyerId == null || strBuyId == null || buyDate == null) {
		request.setAttribute("errMsg", "そのページには直接アクセスできません。");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Admin");
		dispatcher.forward(request, response);
		return;
		}

		int buyerId = Integer.parseInt(strBuyerId);
		int buyId = Integer.parseInt(strBuyId);

		//取得した情報を引数にしてDAOへ
		try {
			//大まかなデータを取得
			Item buyHistory = BuyDAO.boughtDetail(buyerId, buyId, buyDate, buyTime);
			request.setAttribute("buyHistory",buyHistory);

			//購入者名を取得
			String boughtName = BuyDAO.boughtName(buyerId);
			request.setAttribute("buyerId", buyerId);
			request.setAttribute("boughtName", boughtName);

			//細かいデータを取得
			ArrayList<Item> buyHistoryDetailList = BuyDAO.boughtListData(buyerId, buyId);
			request.setAttribute("buyHistoryDetailList",buyHistoryDetailList);

			//配送完了時に使用
			request.setAttribute("buyId", buyId);

			//注文詳細ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/orderdetail.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//各情報を取得
		String strBuyerId = request.getParameter("buyerId");
		String strBuyId = request.getParameter("buyId");
		String buyDate = request.getParameter("buyDate");
		String buyTime = request.getParameter("buyTime");

		int buyerId = Integer.parseInt(strBuyerId);
		int buyId = Integer.parseInt(strBuyId);

		boolean orderItem = false; //商品が複数ある時、どれか一つでもチェックが入っているとtrueになってしまう
		String strOrderItem = request.getParameter("orderItem");
		orderItem = Boolean.parseBoolean(strOrderItem);

		//商品のチェックがなされたか確認
		if(orderItem == false) {
			request.setAttribute("errMsg", "チェックされていない商品があります。");

			request.setAttribute("buyerId", buyerId);
			request.setAttribute("buyId", buyId);
			request.setAttribute("buyDate", buyDate);
			request.setAttribute("buyTime", buyTime);

			doGet(request, response);
			return;
		}

		BuyDAO buyDAO = new BuyDAO();
		try {
			//DAOで配送確認を変更
			buyDAO.deliCheckChange(buyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//未発送一覧ページを再表示
		response.sendRedirect("AdminOrder");
	}

}
