package myEC;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Buy;
import beans.User;
import dao.BuyDAO;
import dao.UserDAO;

/**
 * Servlet implementation class UserData
 */
@WebServlet("/UserData")
public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserData() {
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
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		request.setAttribute("user",user);

		ArrayList<Buy> boughtList = null;
		try {
			//ユーザーの購入履歴をDAOで取得
			boughtList = BuyDAO.boughtData(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("boughtList", boughtList);

		//カゴでの表示変更のため変数を置く
		boolean fromData = true;
		request.setAttribute("fromData", fromData);

		//ユーザー情報ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userdata.jsp");
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
