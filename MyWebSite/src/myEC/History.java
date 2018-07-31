package myEC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Item;
import dao.BuyDAO;

/**
 * Servlet implementation class History
 */
@WebServlet("/History")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public History() {
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

		String strBuyId = request.getParameter("buyId");
		String buyDate = request.getParameter("buyDate");
		String buyTime = request.getParameter("buyTime");

		int buyId = Integer.parseInt(strBuyId);

		/*Date buyDate = null;
		Date buyTime = null;

		/*StringBuilder builderD = new StringBuilder(strBuyDate);
		int i = builderD.indexOf("年");
		builderD.replace(i, i+1, "-");
		i = builderD.indexOf("月");
		builderD.replace(i, i+1, "-");
		i = builderD.indexOf("日");
		builderD.delete(i, i+1);
		String buyDate = builderD.toString();//1月～9月はMが一桁になり合わなくなる

		StringBuilder builderT = new StringBuilder(strBuyTime);
		int j = builderT.indexOf("時");
		builderT.replace(j, j+1, ":");
		j = builderT.indexOf("分");
		builderT.replace(j, j+1, ":");
		j = builderT.indexOf("秒");
		builderT.delete(j, j+1);
		String buyTime = builderT.toString();*/

		/*try {
			SimpleDateFormat fmD = new SimpleDateFormat("yyyy-MM-dd");
	        fmD.setLenient(false);
	        fmD.applyPattern("yyyy'年'M'月'dd'日'");
			buyDate = fmD.parse(strBuyDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			SimpleDateFormat fmT = new SimpleDateFormat("k:mm:ss");
			fmT.setLenient(false);
		    fmT.applyPattern("k'時'mm'分'ss'秒'");
			buyTime = fmT.parse(strBuyTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}*/
		try {
			Item buyHistory = BuyDAO.boughtDetail(buyId, buyDate, buyTime);
			request.setAttribute("buyHistory",buyHistory);

			ArrayList<Item> buyHistoryDetailList = BuyDAO.boughtListData(buyId);
			request.setAttribute("buyHistoryDetailList",buyHistoryDetailList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
