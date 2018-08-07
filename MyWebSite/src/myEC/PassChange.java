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
		//既にログイン済みの場合トップページへ移行
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}


		Object objUserId = session.getAttribute("userId");
		//ログインページから飛んできた直後なら89行目まで飛ばす
		if(objUserId != null) {
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			//ブラウザバックで戻った場合ログインページへ移行
			if(password1 == null || password2 == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Login");
				dispatcher.forward(request, response);
				return;
			}
			//パスワードの入力ミスを確認
			if(!(password1.equals(password2)) || password1.equals("")) {
				request.setAttribute("errMsg", "パスワードが間違っています。");

				session.removeAttribute("userId");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
				dispatcher.forward(request, response);
				return;
			}
			//ユーザーと新たなパスワードを取得
			String password = password1;
			String strUserId = objUserId.toString();
			int userId = Integer.parseInt(strUserId);
			UserDAO userDAO = new UserDAO();
			try {
				//ユーザーIDとパスワードを引数にしてDAOへ
				userDAO.PassUpdate(userId, password);

				request.setAttribute("errMsg", "パスワードを変更しました。");

				//取得していたユーザーIDのセッションを削除
				session.removeAttribute("userId");

				//ログインページへ移行
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//ログインページから来た直後はすぐに再登録ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//入力された値を取得
		String loginId = request.getParameter("loginId");
		String userName = request.getParameter("userName");
		String address = request.getParameter("address");

		UserDAO userDAO = new UserDAO();
			int userId;
			try {
				//ログインID、ユーザー名、住所を引数にしてDAOへ
				userId = userDAO.PassChange(loginId, userName, address);

				//未登録のユーザーならその旨伝える
				if(userId == 0) {
					request.setAttribute("errMsg", "このユーザーは登録されていません。");

					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
					dispatcher.forward(request, response);
					return;
				}
				//取得したユーザーIDをセッションに置く
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);

				//登録を確認したので表示を変更する
				boolean check = true;
				request.setAttribute("check", check);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passchange.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
