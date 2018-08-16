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
		//ログインしていないユーザーはログインページへ移行
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			request.setAttribute("errMsg", "ログインしてください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//管理者以外のユーザーはトップページを表示
		User user = (User)session.getAttribute("user");
		int adminCheck = user.getId();
		if(adminCheck != 1) {
			request.setAttribute("errMsg", "管理者以外はアクセスできません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		//商品のIDを取得
		String detailId = request.getParameter("id");
		ItemDAO itemDAO = new ItemDAO();

		//想定されていない接続方法で来た場合管理用トップページを表示
		if(detailId == null) {
		request.setAttribute("errMsg", "そのページには直接アクセスできません。");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Admin");
		dispatcher.forward(request, response);
		return;
		}

		//取得したIDを引数にしてDAOへ
		Item item = itemDAO.detail(detailId);

		request.setAttribute("item",item);

		//商品削除ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemdelete.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//商品のIDを取得
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);

		ItemDAO itemDAO = new ItemDAO();
		//取得したIDを引数にしてDAOへ
		itemDAO.ItemDelete(id);

		//管理用トップページを再表示
		response.sendRedirect("Admin");
	}

}
