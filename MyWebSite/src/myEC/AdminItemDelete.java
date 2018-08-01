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
import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminItemDelete
 */
@WebServlet("/AdminItemDelete")
public class AdminItemDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemDelete() {
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

		request.setAttribute("item",item);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemdelete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);

		ItemDAO itemDAO = new ItemDAO();
		itemDAO.ItemDelete(id);

		response.sendRedirect("Admin");
	}

}
