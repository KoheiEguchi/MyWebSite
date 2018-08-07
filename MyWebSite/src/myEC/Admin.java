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

import beans.Item;
import beans.User;
import dao.ItemDAO;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
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

		//DAOで全商品を取得
		ArrayList<Item>itemList = ItemDAO.allItem();
		request.setAttribute("itemList", itemList);

		//管理用トップページに移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //入力された情報を取得
        String searchName = request.getParameter("searchName");
		String searchType = request.getParameter("searchType");
		String searchPrice = request.getParameter("searchPrice");

		String strSearchSold = request.getParameter("searchSold");
		boolean searchSold = Boolean.parseBoolean(strSearchSold);

		String strUserId = request.getParameter("userId");
		int userId = Integer.parseInt(strUserId);

		ItemDAO itemDAO = new ItemDAO();
		//取得した情報を引数にしてDAOへ
		ArrayList<Item> searchItemList = itemDAO.soldSearch(searchName, searchType, searchPrice, searchSold, userId);

		request.setAttribute("searchItemList", searchItemList);

		//検索後につき表示を変更
		boolean searchResult = true;
		request.setAttribute("searchResult", searchResult);

		//管理用トップページに移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		dispatcher.forward(request, response);
	}

}
