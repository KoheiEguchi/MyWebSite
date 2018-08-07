package myEC;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Item;
import dao.FavoriteDAO;
import dao.ItemDAO;

/**
 * Servlet implementation class ItemDetail
 */
@WebServlet("/ItemDetail")
public class ItemDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemDetail() {
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

		//選択した商品のIDを取得
		String detailId = request.getParameter("id");
		ItemDAO itemDAO = new ItemDAO();
		//取得したIDを引数にしてDAOへ
		Item item = itemDAO.detail(detailId);
		int itemId = Integer.parseInt(detailId);

		//ユーザーのIDを取得
		String strUserId = request.getParameter("userId");
		int userId = Integer.parseInt(strUserId);

		FavoriteDAO favoriteDAO = new FavoriteDAO();
		boolean favorite = false;
		try {
			//商品とユーザーのIDを引数にしてDAOへ
			favorite = favoriteDAO.checkFavo(itemId, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("favorite", favorite);

		request.setAttribute("item",item);

		//トップやランキングから入った場合の表示にする
		request.setAttribute("link", "list");

		//商品詳細ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/itemdetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
