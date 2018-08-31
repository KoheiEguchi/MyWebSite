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
 * Servlet implementation class Favorite
 */
@WebServlet("/Favorite")
public class Favorite extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Favorite() {
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

		//各種情報を取得
		String strItemId = request.getParameter("itemId");
		String strUserId = request.getParameter("userId");
		String strFavorite = request.getParameter("favorite");

		//想定されていない接続方法で来た場合トップページを表示
		if(strItemId == null || strUserId == null) {
			request.setAttribute("errMsg", "そのページには直接アクセスできません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		int itemId = Integer.parseInt(strItemId);
		int userId = Integer.parseInt(strUserId);
		boolean favoCheck = Boolean.valueOf(strFavorite);

		FavoriteDAO favoriteDAO = new FavoriteDAO();
		//対象の商品をお気に入り登録していない場合
		if(favoCheck == false) {
			try {
				//商品、ユーザー両IDを引数にしてDAOへ
				boolean favorite = favoriteDAO.insertFavo(itemId, userId);
				request.setAttribute("favorite", favorite);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//対象の商品をお気に入り登録している場合
		}else {
			try {
				//商品、ユーザー両IDを引数にしてDAOへ
				boolean favorite = favoriteDAO.deleteFavo(itemId, userId);
				request.setAttribute("favorite", favorite);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//改めて商品の情報を取得
		String detailId = request.getParameter("itemId");
		ItemDAO itemDAO = new ItemDAO();
		Item item = itemDAO.detail(detailId);

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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
