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
 * Servlet implementation class UserDelete
 */
@WebServlet("/UserDelete")
public class UserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDelete() {
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

		//ユーザーのIDを取得
		String userId = request.getParameter("id");
		UserDAO userDAO = new UserDAO();
		User user = null;
		try {
			//取得したIDを引数にしてDAOへ
			user = userDAO.UserData(userId);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		request.setAttribute("user",user);

		//ユーザー情報更新ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userdelete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//ユーザーのIDを取得
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);

		UserDAO userDAO = new UserDAO();
		//取得したIDを引数にしてDAOへ
		userDAO.UserDelete(id);

		//残ったセッションを削除するためログアウトページへ移行
		response.sendRedirect("Logout");
	}

}
