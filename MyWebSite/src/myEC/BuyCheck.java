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
import beans.Item;

/**
 * Servlet implementation class BuyCheck
 */
@WebServlet("/BuyCheck")
public class BuyCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyCheck() {
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
		//想定されていない接続方法で来た場合トップページを表示
		request.setAttribute("errMsg", "そのページには直接アクセスできません。");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		try {
			//入力された配送方法を取得
			String deliveryMethod = request.getParameter("deliveryMethod");

			//取得した配送方法を元に送料を取得
			int deliPrice = 0;
			switch(deliveryMethod) {
				case "normal" : deliPrice = 0;
				deliveryMethod = "通常配送";
				break;

				case "quick" : deliPrice = 500;
				deliveryMethod = "お急ぎ配送";
				break;

				case "select" : deliPrice = 200;
				deliveryMethod = "日時指定配送";
				break;
			}
			//カゴのリストを参照
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			//単価×個数を全商品分足して小計を算出
			int allPrice = 0;
			for (Item item : cart) {
				allPrice += item.getPrice() * item.getCount();
			}
			//小計に送料を足して合計金額を算出
			int totalPrice = allPrice + deliPrice;

			//ユーザーのIDを取得
			String strBuyId = request.getParameter("id");
			int buyId = Integer.parseInt(strBuyId);

			//入力された配送先住所を取得
			String deliAddress = request.getParameter("deliAddress");
			//住所が空欄の場合
			if(deliAddress.isEmpty() || deliAddress.equals(" ")) {
				request.setAttribute("errMsg", "配送先の住所を入力して下さい。");

				//カゴ内一覧ページへ移行
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/incart.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//各情報を取得
			Buy buy = new Buy();
			buy.setBuyId(buyId);
			buy.setAllPrice(allPrice);
			buy.setTotalPrice(totalPrice);
			buy.setDeliveryMethod(deliveryMethod);
			buy.setDeliPrice(deliPrice);
			buy.setDeliAddress(deliAddress);

			//購入情報をセッションに置く
			session.setAttribute("buy", buy);

			//購入確認ページへ移行
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/buycheck.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
