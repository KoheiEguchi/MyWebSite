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
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Top");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			String deliveryMethod = request.getParameter("deliveryMethod");

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
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			int allPrice = 0;
			for (Item item : cart) {
				allPrice += item.getPrice() * item.getCount();
			}
			int totalPrice = allPrice + deliPrice;

			String strBuyId = request.getParameter("id");
			int buyId = Integer.parseInt(strBuyId);

			String strCount = request.getParameter("count");
			int count = Integer.parseInt(strCount);//カゴから一度ほかの画面に行くとcount=nullになる

			String deliAddress = request.getParameter("deliAddress");

			Buy buy = new Buy();
			buy.setBuyId(buyId);
			buy.setCount(count);
			buy.setAllPrice(allPrice);
			buy.setTotalPrice(totalPrice);
			buy.setDeliveryMethod(deliveryMethod);
			buy.setDeliPrice(deliPrice);
			buy.setDeliAddress(deliAddress);

			session.setAttribute("buy", buy);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/buycheck.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
