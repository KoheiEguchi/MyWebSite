package myEC;

import java.io.IOException;
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

/**
 * Servlet implementation class AdminOrder
 */
@WebServlet("/AdminOrder")
public class AdminOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインしていないユーザーはログインページへ移行
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null){
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
		//DAOで全注文記録を取得
		ArrayList<Buy>orderList = BuyDAO.allOrder();

		//カゴが空の場合
		if(orderList.size() == 0) {
			boolean noOrder = true;
			request.setAttribute("noOrder", noOrder);
		}else {
		request.setAttribute("orderList", orderList);

		//最初は購入日時を基準に並べる
		String order = "購入日時";
		request.setAttribute("orderSelect", order);
		}

		//注文一覧ページに移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminorder.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		ArrayList<Buy>orderList = new ArrayList<Buy>();

		String order = request.getParameter("order");
		String select = null;

		//指定された情報を取得
		if(order.equals("購入者")) {
        	//購入者を選んだ時
        	select = "buyer_id";

		}else if(order.equals("合計金額")) {
        	//合計金額を選んだ時
    		select = "total_price";

		}else if(order.equals("購入日時")) {
        	//購入日時を選んだ時
    		select = "buy_date";
        }

        //選んだものを基準にDAOで並べ替え
		orderList = BuyDAO.selectOrder(select);
        request.setAttribute("orderList", orderList);

        //並べ替え基準を表示
        request.setAttribute("orderSelect", order);

        //注文一覧ページに移行
      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminorder.jsp");
      	dispatcher.forward(request, response);

	}

}
