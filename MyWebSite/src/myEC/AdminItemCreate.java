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

import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminCreate
 */
@WebServlet("/AdminItemCreate")
public class AdminItemCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemCreate() {
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

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String itemName = request.getParameter("itemName");
		String itemDetail = request.getParameter("itemDetail");
		String type = request.getParameter("type");
		String strPrice = request.getParameter("price");
		String fileName = request.getParameter("fileName");

		if(type.equals("noSelect")) {
			request.setAttribute("errMsg", "商品の種類を選択してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		int price = Integer.parseInt(strPrice);

		ItemDAO itemDAO = new ItemDAO();
		try {
			itemDAO.ItemCreate(itemName,itemDetail,type,price,fileName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("Admin");
	}

}
