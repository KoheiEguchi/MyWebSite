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

/**
 * Servlet implementation class CartDelete
 */
@WebServlet("/CartDelete")
public class CartDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartDelete() {
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
			String deleteItemId = request.getParameter("id");
			ArrayList<Item> cart = (ArrayList<Item>) session.getAttribute("cart");

			String cartActionMessage = "";
			if (deleteItemId != null) {
				for (Item cartInItem : cart) {
					if (cartInItem.getId() == Integer.parseInt(deleteItemId)) {
						cart.remove(cartInItem);
						break;
					}
				}
				cartActionMessage = "削除しました";

			}
			if(cart.size() == 0) {
				boolean noCart = true;
				request.setAttribute("noCart", noCart);
			}
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
		session.removeAttribute("cart");
		response.sendRedirect("InCart");
	}
}
