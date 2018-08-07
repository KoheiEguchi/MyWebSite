package myEC;

import java.io.IOException;

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
 * Servlet implementation class CartItemDetail
 */
@WebServlet("/CartItemDetail")
public class CartItemDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartItemDetail() {
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

		//対象商品のIDと個数を取得
		String detailId = request.getParameter("id");
		String strCount = request.getParameter("count");
		int count = Integer.parseInt(strCount);

		//取得したIDと個数を引数にしてDAOへ
		Item item = ItemDAO.CartIn(detailId,count);

		request.setAttribute("item",item);

		//カゴ内一覧から入った場合の表示にする
		request.setAttribute("link", "cart");

		//商品詳細ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/itemdetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
