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

import beans.Item;
import beans.User;
import dao.BuyDAO;

/**
 * Servlet implementation class History
 */
@WebServlet("/History")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public History() {
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

		//ユーザー情報のセッションを取得
		User user = (User)session.getAttribute("user");
		//各情報を取得
		int buyerId = user.getId();
		String strBuyId = request.getParameter("buyId");
		String buyDate = request.getParameter("buyDate");
		String buyTime = request.getParameter("buyTime");
		String deliConfirm = request.getParameter("deliConfirm");

		//想定されていない接続方法で来た場合トップページを表示
		if(strBuyId == null || buyDate == null || deliConfirm == null) {
		request.setAttribute("errMsg", "そのページには直接アクセスできません。");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
		dispatcher.forward(request, response);
		return;
		}

		int buyId = Integer.parseInt(strBuyId);

		//取得した情報を引数にしてDAOへ
		try {
			//大まかなデータを取得
			Item buyHistory = BuyDAO.boughtDetail(buyerId, buyId, buyDate, buyTime);
			request.setAttribute("buyHistory",buyHistory);

			//細かいデータを取得
			ArrayList<Item> buyHistoryDetailList = BuyDAO.boughtListData(buyerId, buyId);
			request.setAttribute("buyHistoryDetailList",buyHistoryDetailList);

			request.setAttribute("deliConfirm", deliConfirm);

			//購入履歴詳細ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
