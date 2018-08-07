package myEC;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * Servlet implementation class UserCreate
 */
@WebServlet("/UserCreate")
public class UserCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//新規登録ページへ
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usercreate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//入力された各データを取得
		String loginId = request.getParameter("loginId");
		String userName = request.getParameter("userName");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String address = request.getParameter("address");

		//ログインIDの入力漏れ確認
		if(loginId.equals("")) {
			request.setAttribute("errMsg", "入力された内容は正しくありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usercreate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//既に登録されていないか確認
		UserDAO userDAO = new UserDAO();
		boolean check = userDAO.CreateCheck(loginId);

		//未登録、パスワード違い、入力漏れの確認
		if((check == true) && password1.equals(password2) && !(password1.equals("")) && !(userName.equals("")) && !(address.equals(""))){
			String password = password1;

			//各データを登録
			userDAO.UserCreate(loginId,userName,password,address);

			//ログインページへ移行
			response.sendRedirect("Login");
		//入力された内容がおかしい場合ページ再表示
		}else {
			request.setAttribute("errMsg", "入力された内容は正しくありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usercreate.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

}
