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

import beans.Buy;
import beans.Item;
import beans.User;
import dao.BuyDAO;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminItemDetail
 */
@WebServlet("/AdminItemDetail")
public class AdminItemDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemDetail() {
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
		int adminCheck = user.getId();
		if(adminCheck != 1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		String detailId = request.getParameter("id");
		ItemDAO itemDAO = new ItemDAO();
		Item item = itemDAO.detail(detailId);

		int itemId = Integer.parseInt(detailId);
		BuyDAO buyDAO = new BuyDAO();
		ArrayList<Buy> soldHistoryList = null;
		try {
			soldHistoryList = buyDAO.soldHistory(itemId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("item",item);
		request.setAttribute("soldHistoryList", soldHistoryList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemdetail.jsp");
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
