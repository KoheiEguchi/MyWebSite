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
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		String strItemId = request.getParameter("itemId");
		String strUserId = request.getParameter("userId");
		String strFavorite = request.getParameter("favorite");

		if(strItemId == null || strUserId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		int itemId = Integer.parseInt(strItemId);
		int userId = Integer.parseInt(strUserId);
		boolean favoCheck = Boolean.parseBoolean(strFavorite);

		FavoriteDAO favoriteDAO = new FavoriteDAO();
		if(favoCheck == false) {
			try {
				boolean favorite = favoriteDAO.insertFavo(itemId, userId);
				request.setAttribute("favorite", favorite);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				boolean favorite = favoriteDAO.deleteFavo(itemId, userId);
				request.setAttribute("favorite", favorite);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		String detailId = request.getParameter("itemId");
		ItemDAO itemDAO = new ItemDAO();
		Item item = itemDAO.detail(detailId);

		request.setAttribute("item",item);

		request.setAttribute("link", "list");

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
