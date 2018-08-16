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
import dao.ItemDAO;

/**
 * Servlet implementation class Ranking
 */
@WebServlet("/Ranking")
public class Ranking extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ranking() {
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

		//DAOを参照
		ArrayList<Item>itemList = ItemDAO.ranking();
		request.setAttribute("itemList", itemList);

		//表示順位の初期値を6に設定
		int rankNum = 6;
		request.setAttribute("rankNum", rankNum);

		//ランキングページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ranking.jsp");
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

		String strRankNum = request.getParameter("rankNum");
		try {
			int rankNum = Integer.parseInt(strRankNum);

			//表示順位が0以下の時
			if(rankNum <= 0) {
				request.setAttribute("errMsg", "その順位では商品を表示できません。");

				//最初と同様に表示
				ArrayList<Item>itemList = ItemDAO.ranking();
				request.setAttribute("itemList", itemList);

				rankNum = 6;

			}else {
				ItemDAO itemDAO = new ItemDAO();
				//取得したデータを引数にしてDAOへ
				ArrayList<Item> itemList = itemDAO.rankingSearch(searchName, searchType, searchPrice, searchFavorite, userId, rankNum);

				request.setAttribute("itemList", itemList);

				//検索後につき表示を変更
				boolean searchResult = true;
				request.setAttribute("searchResult", searchResult);
			}
			request.setAttribute("rankNum", rankNum);

		//表示順位に数字が入力されなかった時
		}catch(NumberFormatException e) {
			request.setAttribute("errMsg", "順位を1以上の整数で入力してください。(算用数字のみ)");

			//最初と同様に表示
			ArrayList<Item>itemList = ItemDAO.ranking();
			request.setAttribute("itemList", itemList);

			int rankNum = 6;
			request.setAttribute("rankNum", rankNum);
		}

		//ランキングページへ移行
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ranking.jsp");
		dispatcher.forward(request, response);
	}

}
