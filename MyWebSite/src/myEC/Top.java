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
 * Servlet implementation class Top
 */
@WebServlet("/Top")
public class Top extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top() {
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
		int userId = user.getId();
		//ユーザーIDを引数にしてDAOへ
		ArrayList<Item>itemList = ItemDAO.recommendItem(userId);
		//取得したリストの中身があるか確認
		boolean newCheck = itemList.isEmpty();
		//中身がない＝買ったことがない場合
		if(newCheck == true) {
			ArrayList<Item> newItemList = ItemDAO.newUser();
			request.setAttribute("itemList", newItemList);
		}else {
		request.setAttribute("itemList", itemList);
		}

		//エラーでトップページへ飛ぶ際のメッセージを取得
		String errMsg = "";
		errMsg = (String)request.getAttribute("errMsg");
		request.setAttribute("errMsg", errMsg);

		//トップページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/top.jsp");
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

		String strSearchFavorite = request.getParameter("searchFavorite");
		boolean searchFavorite = Boolean.parseBoolean(strSearchFavorite);

		String strUserId = request.getParameter("userId");
		int userId = Integer.parseInt(strUserId);

		ItemDAO itemDAO = new ItemDAO();
		//取得した情報を引数にしてDAOへ
		ArrayList<Item> searchItemList = itemDAO.search(searchName, searchType, searchPrice, searchFavorite, userId);

		request.setAttribute("searchItemList", searchItemList);

		//検索後につき表示を変更
		boolean searchResult = true;
		request.setAttribute("searchResult", searchResult);

		//トップページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/top.jsp");
		dispatcher.forward(request, response);
	}

}
