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
import dao.ItemDAO;

/**
 * Servlet implementation class AdminItemUpdate
 */
@WebServlet("/AdminItemUpdate")
public class AdminItemUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemUpdate() {
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

		String detailId = request.getParameter("id");
		ItemDAO itemDAO = new ItemDAO();
		Item item = itemDAO.detail(detailId);

		request.setAttribute("item",item);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemupdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String strId = request.getParameter("id");
		String itemName = request.getParameter("itemName");
		String itemDetail = request.getParameter("itemDetail");
		String strPrice = request.getParameter("price");
		String fileName = request.getParameter("fileName");

		int id = Integer.parseInt(strId);
		int price = Integer.parseInt(strPrice);

		ItemDAO itemDAO = new ItemDAO();
		try {
			itemDAO.ItemUpdate(id,itemName,itemDetail,price,fileName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("Admin");
	}

}
