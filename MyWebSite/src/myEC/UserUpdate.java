package myEC;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.UserDAO;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdate() {
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

		String userId = request.getParameter("id");
		UserDAO userDAO = new UserDAO();
		User user = null;
		try {
			user = userDAO.UserData(userId);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		request.setAttribute("user",user);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userupdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String strId = request.getParameter("id");
		String loginId = request.getParameter("loginId");
		String userName = request.getParameter("userName");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String address = request.getParameter("address");

		int id = Integer.parseInt(strId);

		if(password1.equals(password2) && !(password1.equals(""))) {
			String password = password1;

			UserDAO userDAO = new UserDAO();
			userDAO.UserUpdate(id,loginId,userName,password,address);

			if(true) {
				request.setAttribute("id",id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("UserData");
				dispatcher.forward(request, response);
			}

		}else {
			String userId = request.getParameter("id");
			UserDAO userDAO = new UserDAO();
			User user = null;
			try {
				user = userDAO.UserData(userId);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			request.setAttribute("user",user);

			request.setAttribute("errMsg", "入力された内容は正しくありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userupdate.jsp");
			dispatcher.forward(request, response);
		}


	}

}
