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
 * Servlet implementation class InCart
 */
@WebServlet("/InCart")
public class InCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InCart() {
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

		try {
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
			if (cart == null) {
				cart = new ArrayList<Item>();
				session.setAttribute("cart", cart);
			}

			String cartActionMessage = "";

			if(cart.size() == 0) {
				boolean noCart = true;
				request.setAttribute("noCart", noCart);
			}

			Object objCartAD = request.getAttribute("cartAD");
			if(objCartAD != null) {
				String strCartAD = objCartAD.toString();
				Boolean cartAD = Boolean.parseBoolean(strCartAD);
				if(cartAD == true) {
					cartActionMessage = "カゴの中を全て削除しました";
				}
			}
			session.setAttribute("cart", cart);

			request.setAttribute("cartActionMessage", cartActionMessage);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/incart.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		try {
			String itemId = request.getParameter("id");

			String strCount = request.getParameter("count");
			int count = Integer.parseInt(strCount);

			if(count <= 0) {
				request.setAttribute("errMsg", "個数は1以上入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("ItemDetail");
				dispatcher.forward(request, response);
				return;
			}

			Item item = ItemDAO.CartIn(itemId,count);
			request.setAttribute("item", item);

			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			if (cart == null) {
				cart = new ArrayList<Item>();
			}
			cart.add(item);

			session.setAttribute("cart", cart);
			request.setAttribute("cartActionMessage", "商品を追加しました");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/incart.jsp");
			dispatcher.forward(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("errMsg", "個数を1以上の整数で入力してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("ItemDetail");
			dispatcher.forward(request, response);
		}
	}

}
