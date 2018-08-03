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

import dao.UserDAO;

/**
 * Servlet implementation class Remember
 */
@WebServlet("/PassChange")
public class PassChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		Object objUserId = session.getAttribute("userId");
		if(objUserId != null) {
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			if(password1 == null || password2 == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Login");
				dispatcher.forward(request, response);
				return;
			}
			if(!(password1.equals(password2)) || password1.equals("")) {
				request.setAttribute("errMsg", "パスワードが間違っています。");

				session.removeAttribute("userId");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
				dispatcher.forward(request, response);
				return;
			}
			String password = password1;
			String strUserId = objUserId.toString();
			int userId = Integer.parseInt(strUserId);
			UserDAO userDAO = new UserDAO();
			try {
				userDAO.PassUpdate(userId, password);

				request.setAttribute("errMsg", "パスワードを変更しました。");

				session.removeAttribute("userId");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String loginId = request.getParameter("loginId");
		String userName = request.getParameter("userName");
		String address = request.getParameter("address");

		UserDAO userDAO = new UserDAO();
			int userId;
			try {
				userId = userDAO.PassChange(loginId, userName, address);

				if(userId == 0) {
					request.setAttribute("errMsg", "このユーザーは登録されていません。");

					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
					dispatcher.forward(request, response);
					return;
				}
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);

				boolean check = true;
				request.setAttribute("check", check);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
