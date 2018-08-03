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
import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class Top
 */
@WebServlet("/Top")
public class Top extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top() {
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
		User user = (User)session.getAttribute("user");
		int userId = user.getId();
		ArrayList<Item>itemList = ItemDAO.recommendItem(userId);
		boolean newCheck = itemList.isEmpty();
		if(newCheck == true) {
			ArrayList<Item> newItemList = ItemDAO.newUser();
			request.setAttribute("itemList", newItemList);
		}else {
		request.setAttribute("itemList", itemList);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/top.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

		String searchName = request.getParameter("searchName");

		String searchType = request.getParameter("searchType");

		String searchPrice = request.getParameter("searchPrice");

		String strSearchFavorite = request.getParameter("searchFavorite");
		boolean searchFavorite = Boolean.parseBoolean(strSearchFavorite);

		String strUserId = request.getParameter("userId");
		int userId = Integer.parseInt(strUserId);

		ItemDAO itemDAO = new ItemDAO();
		ArrayList<Item> searchItemList = itemDAO.search(searchName, searchType, searchPrice, searchFavorite, userId);

		request.setAttribute("searchItemList", searchItemList);

		boolean searchResult = true;
		request.setAttribute("searchResult", searchResult);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/top.jsp");
		dispatcher.forward(request, response);
	}

}
