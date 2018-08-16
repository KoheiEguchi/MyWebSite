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

import beans.Item;
import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminItemUpdate
 */
@WebServlet("/AdminItemUpdate")
public class AdminItemUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemUpdate() {
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

		//商品更新ページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemupdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//入力された各データを取得
		String strId = request.getParameter("id");
		String itemName = request.getParameter("itemName");
		String itemDetail = request.getParameter("itemDetail");
		String type = request.getParameter("type");
		String strPrice = request.getParameter("price");
		String fileName = request.getParameter("fileName");

		int id = Integer.parseInt(strId);
		try {
			int price = Integer.parseInt(strPrice);
			//価格が1円未満の時
			if(price < 1) {
				request.setAttribute("errMsg", "価格は1円以上で入力してください。");

				//商品のデータを再取得
				String detailId = request.getParameter("id");
				ItemDAO itemDAO = new ItemDAO();
				Item item = itemDAO.detail(detailId);

				request.setAttribute("item",item);
				//商品更新ページへ移行
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemupdate.jsp");
				dispatcher.forward(request, response);
				return;
			}

			ItemDAO itemDAO = new ItemDAO();
			try {
				//取得した各データを引数にしてDAOへ
				itemDAO.ItemUpdate(id,itemName,itemDetail,type,price,fileName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//価格に数字以外が入力された場合
		} catch (NumberFormatException e) {
			request.setAttribute("errMsg", "価格を1円以上の整数で入力してください。");

			//商品のデータを再取得
			String detailId = request.getParameter("id");
			ItemDAO itemDAO = new ItemDAO();
			Item item = itemDAO.detail(detailId);

			request.setAttribute("item",item);
			//商品更新ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemupdate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//管理用トップページを再表示
		response.sendRedirect("Admin");
	}

}
