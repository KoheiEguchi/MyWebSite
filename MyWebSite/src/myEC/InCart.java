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

import beans.Item;
import dao.ItemDAO;

/**
 * Servlet implementation class InCart
 */
@WebServlet("/InCart")
public class InCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InCart() {
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
			request.setAttribute("errMsg", "ログインしてください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		try {
			//カゴのリストを参照
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
			//カゴがない場合新規に作る
			if (cart == null) {
				cart = new ArrayList<Item>();
				session.setAttribute("cart", cart);
			}

			String cartActionMessage = "";

			//カゴが空の場合
			if(cart.size() == 0) {
				boolean noCart = true;
				request.setAttribute("noCart", noCart);
			}

			//カゴの全削除をした場合
			Object objCartAD = request.getAttribute("cartAD");
			if(objCartAD != null) {
				String strCartAD = objCartAD.toString();
				Boolean cartAD = Boolean.valueOf(strCartAD);
				//Boolean cartAD = Boolean.parseBoolean(strCartAD);
				if(cartAD == true) {
					cartActionMessage = "カゴの中を全て削除しました";
				}
			}
			//カゴをセッションに置く
			session.setAttribute("cart", cart);

			request.setAttribute("cartActionMessage", cartActionMessage);

			//ユーザー情報ページから来た場合表示を変える
			String strFromData = request.getParameter("fromData");
			if(strFromData != null) {
			boolean fromData = Boolean.valueOf(strFromData);
				request.setAttribute("fromData", fromData);
			}

			//カゴ内一覧ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/incart.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		try {
			//カゴに入れる商品と個数を取得
			String itemId = request.getParameter("id");

			String strCount = request.getParameter("count");
			int count = Integer.parseInt(strCount);

			//個数が0以下の場合
			if(count <= 0) {
				request.setAttribute("errMsg", "個数は1以上入力してください。");
				//商品詳細ページを再表示
				RequestDispatcher dispatcher = request.getRequestDispatcher("ItemDetail");
				dispatcher.forward(request, response);
				return;
			}

			//取得した商品IDと個数を引数にしてDAOへ
			Item item = ItemDAO.CartIn(itemId,count);
			request.setAttribute("item", item);

			//カゴのリストを参照
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			//カゴがない場合新規に作る
			if (cart == null) {
				cart = new ArrayList<Item>();
			}
			//取得した商品情報をカゴのリストに記録
			cart.add(item);

			//カゴをセッションに置く
			session.setAttribute("cart", cart);
			request.setAttribute("cartActionMessage", "商品を追加しました");

			//カゴ内一覧ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/incart.jsp");
			dispatcher.forward(request, response);
		//個数に数字以外が入力された場合
		} catch (NumberFormatException e) {
			request.setAttribute("errMsg", "個数を1以上の整数で入力してください。");
			//商品詳細ページを再表示
			RequestDispatcher dispatcher = request.getRequestDispatcher("ItemDetail");
			dispatcher.forward(request, response);
		}
	}

}
