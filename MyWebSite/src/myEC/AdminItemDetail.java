package myEC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Buy;
import beans.Item;
import beans.User;
import dao.BuyDAO;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminItemDetail
 */
@WebServlet("/AdminItemDetail")
public class AdminItemDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemDetail() {
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
		//管理者以外のユーザーはトップページを表示
		User user = (User)session.getAttribute("user");
		int adminCheck = user.getId();
		if(adminCheck != 1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
			dispatcher.forward(request, response);
			return;
		}

		//商品のIDを取得
		String detailId = request.getParameter("id");
		ItemDAO itemDAO = new ItemDAO();
		//取得したIDを引数にしてDAOへ
		Item item = itemDAO.detail(detailId);

		int itemId = Integer.parseInt(detailId);
		BuyDAO buyDAO = new BuyDAO();
		ArrayList<Buy> soldHistoryList = null;
		try {
			//DAOで商品の販売履歴を取得
			soldHistoryList = buyDAO.soldHistory(itemId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("item",item);
		request.setAttribute("soldHistoryList", soldHistoryList);

		//管理用商品詳細ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemdetail.jsp");
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
