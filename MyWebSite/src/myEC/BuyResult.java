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
import dao.BuyDAO;
import dao.ItemDAO;

/**
 * Servlet implementation class BuyResult
 */
@WebServlet("/BuyResult")
public class BuyResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyResult() {
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
		HttpSession session = request.getSession();
		try {
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			Buy buy = (Buy) session.getAttribute("buy");

			for (Item item : cart) {
				int buyData = BuyDAO.insertBuy(buy);

				Buy buyItem = new Buy();
				buyItem.setItemId(item.getId());
				buyItem.setCount(item.getCount());
				buyItem.setId(buyData);

				BuyDAO.insertBuyItem(buyItem);

				Item sold = BuyDAO.soldCheck(buyItem);
				ItemDAO.insertSold(sold);

				/*Buy buyResult = BuyDAO.buyResult(buyData);
				request.setAttribute("buyResult", buyResult);*/

				/*ArrayList<Item> buyResultList = BuyDAO.buyResultList(buyData);
				request.setAttribute("buyResultList", buyResultList);*/
			}

			//Date buyDate = item.getBuyDate();
			ArrayList<Buy> buyIdList = BuyDAO.buyIdSelect();
			Buy buyId = buyIdList.get(0);
			BuyDAO.insertBuyId(buyId);


			ArrayList<Item>itemList = ItemDAO.allItem();
			request.setAttribute("itemList", itemList);

			session.removeAttribute("cart");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/buyresult.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
