package myEC;

import java.io.IOException;

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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//パスワード変更時のセッションを削除
		HttpSession session = request.getSession();
		session.removeAttribute("userId");

		//ログインページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力されたログインIDとパスワードを取得
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		//取得した引数をDAOへ
		UserDAO userDAO = new UserDAO();
		User user = userDAO.Login(loginId, password);

		//対象のデータが存在しなかった場合
		if (user == null) {
			request.setAttribute("errMsg", "ログインに失敗しました。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//取得データをセッションに置く
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		//ログイン者が管理者か否かの確認
		int adminCheck = user.getId();
		//管理者は管理者用ページへ
		if(adminCheck == 1) {
			response.sendRedirect("Admin");
		//一般ユーザーはトップページへ
		}else {
			response.sendRedirect("Top");
		}
	}

}
