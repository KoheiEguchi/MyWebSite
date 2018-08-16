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

import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class AdminCreate
 */
@WebServlet("/AdminItemCreate")
public class AdminItemCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminItemCreate() {
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

		//商品追加ページに移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//各情報を取得
		String itemName = request.getParameter("itemName");
		String itemDetail = request.getParameter("itemDetail");
		String type = request.getParameter("type");
		String strPrice = request.getParameter("price");
		String fileName = request.getParameter("fileName");

		//商品画像以外で入力されていない欄がある場合
		if(itemName.equals("") || itemDetail.equals("") || type.equals("noSelect")) {
			request.setAttribute("errMsg", "商品画像以外は全て入力してください。");
			//商品追加ページに移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		try {
			int price = Integer.parseInt(strPrice);
			//価格が1円未満の時
			if(price < 1) {
				request.setAttribute("errMsg", "価格は1円以上で入力してください。");
				//商品追加ページに移行
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
				dispatcher.forward(request, response);
				return;
			}

			ItemDAO itemDAO = new ItemDAO();

			//取得した各データを引数にしてDAOへ
			try {
				itemDAO.ItemCreate(itemName,itemDetail,type,price,fileName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//価格に数字以外が入力された場合
		} catch (NumberFormatException e) {
			request.setAttribute("errMsg", "価格を1円以上の整数で入力してください。");
			//商品追加ページに移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminitemcreate.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//管理用トップページを再表示
		response.sendRedirect("Admin");
	}

}
