package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import base.DBManager;
import beans.Buy;
import beans.Item;

public class BuyDAO {

	public static BuyDAO getInstance() {
		return new BuyDAO();
	}

	public static int insertBuy(Buy buy) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		int autoIncKey = -1;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO buy(buyer_id,total_price,delivery_method,buy_date) VALUES(?,?,?,now())",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, buy.getBuyId());
			st.setInt(2, buy.getTotalPrice());
			st.setString(3, buy.getDeliveryMethod());
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				autoIncKey = rs.getInt(1);
			}

			return autoIncKey;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static void insertBuyItem (Buy buyItem) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"UPDATE buy SET item_id = ?, count = ? WHERE id = ?");
			st.setInt(1, buyItem.getItemId());
			st.setInt(2, buyItem.getCount());
			st.setInt(3, buyItem.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static ArrayList<Buy> buyIdSelect() throws SQLException, ParseException {
		Connection con = null;
		PreparedStatement preSt = null;
		PreparedStatement st = null;
		ArrayList<Buy> buyIdList = new ArrayList<Buy>();

		try {
			con = DBManager.getConnection();

			preSt = con.prepareStatement(
					"SELECT DATE_FORMAT(now(), '%Y%m%d %H%i') AS string_now");
			ResultSet preRs = preSt.executeQuery();
			if(!(preRs.next())) {
				return null;
			}

			String preSql = null;
			preSql = preRs.getString("string_now");

			StringBuilder buildSql = new StringBuilder(preSql);
			buildSql.insert(4, "-");
			buildSql.insert(7, "-");
			buildSql.insert(13, ":");
			String sql = buildSql.toString();

			st = con.prepareStatement(
					"SELECT id FROM buy WHERE buy_date LIKE '" + sql + "%'");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = (rs.getInt("id"));

				Buy buyId = new Buy(id);

				buyIdList.add(buyId);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return buyIdList;
	}

	public static void insertBuyId(Buy buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"UPDATE buy SET buy_id = ? WHERE id >= ?");
			st.setInt(1, buyId.getId());
			st.setInt(2, buyId.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static Buy buyResult(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT total_price, buy_date "
					+ "FROM buy "
					+ "WHERE id = ?");
			st.setInt(1, buyId);
			//st.setInt(2, userId);

			ResultSet rs = st.executeQuery();

			if(!(rs.next())) {
				return null;
			}
				int totalPrice = (rs.getInt("total_price"));
				//String deliveryMethod = (rs.getString("delivery_method"));
				Date subBuyDate = (rs.getDate("buy_date"));
				Time subBuyTime = (rs.getTime("buy_date"));

				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

				/*int deliPrice = 0;
				switch(deliveryMethod) {
					case "通常配送" : deliPrice = 0;
					break;

					case "お急ぎ配送" : deliPrice = 500;
					break;

					case "日時指定配送" : deliPrice = 200;
					break;
				}*/

				Buy buyResult = new Buy(totalPrice,buyDate,buyTime);

				return buyResult;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<Item> buyResultList(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		ArrayList<Item> buyResultList = new ArrayList<Item>();
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT item.id,"
					+ " item.name,"
					+ " item.type,"
					+ " item.price"
					+ " FROM buy"
					+ " JOIN item"
					+ " ON buy.item_id = item.id"
					+ " WHERE buy.id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = (rs.getInt("id"));
				String itemName = (rs.getString("name"));
				String type = (rs.getString("type"));
				int price = (rs.getInt("price"));

				Item item = new Item(id,itemName,type,price);

				buyResultList.add(item);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return buyResultList;
	}

	public static ArrayList<Buy> boughtData(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		ArrayList<Buy> boughtList = new ArrayList<Buy>();
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"SELECT DISTINCT buyer_id, total_price, delivery_method, buy_date, buy_id FROM buy WHERE buyer_id = ? GROUP BY buy_id");
			st.setString(1, userId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int buyerId = (rs.getInt("buyer_id"));
				int totalPrice = (rs.getInt("total_price"));
				String deliveryMethod = (rs.getString("delivery_method"));
				Date subBuyDate = (rs.getDate("buy_date"));
				Time subBuyTime = (rs.getTime("buy_date"));
				int buyId = (rs.getInt("buy_id"));

				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

				Buy bought = new Buy(buyerId,totalPrice,deliveryMethod,buyDate,buyTime,buyId);

				boughtList.add(bought);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return boughtList;
	}

	public static Item boughtDetail(int buyerId, int buyId, String buyDate, String buyTime) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT DISTINCT total_price, delivery_method FROM buy WHERE buyer_id = ? AND buy_id = ?");
			st.setInt(1, buyerId);
			st.setInt(2, buyId);

			ResultSet rs = st.executeQuery();

			if (!(rs.next())) {
				return null;
			}

			int totalPrice = (rs.getInt("total_price"));
			String deliveryMethod = (rs.getString("delivery_method"));

			int deliPrice = 0;
			switch(deliveryMethod) {
				case "通常配送" : deliPrice = 0;
				break;

				case "お急ぎ配送" : deliPrice = 500;
				break;

				case "日時指定配送" : deliPrice = 200;
				break;
			}

			Item buyHistory = new Item(totalPrice, buyDate, buyTime, deliveryMethod, deliPrice);

			return buyHistory;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<Item> boughtListData(int buyerId, int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		ArrayList<Item> buyHistoryDetailList = new ArrayList<Item>();

		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
				"SELECT name,price,count "
				+ "FROM item "
				+ "JOIN buy ON item.id = buy.item_id "
				+ " WHERE buy.buyer_id = ? AND buy.buy_id = ?");
			st.setInt(1, buyerId);
			st.setInt(2, buyId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Item buyHistoryDetail = new Item();
				buyHistoryDetail.setItemName(rs.getString("name"));
				buyHistoryDetail.setPrice(rs.getInt("price"));
				buyHistoryDetail.setCount(rs.getInt("count"));

				buyHistoryDetailList.add(buyHistoryDetail);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return buyHistoryDetailList;
	}

	public static Item soldCheck(Buy buyItem) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT SUM(count) FROM buy WHERE item_id = ? GROUP BY item_id");
			st.setInt(1, buyItem.getItemId());

			ResultSet rs = st.executeQuery();

			if (!(rs.next())) {
				return null;
			}

			int soldNum = (rs.getInt("SUM(count)"));
			int id = buyItem.getItemId();

			Item sold = new Item(soldNum, id);

			return sold;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public ArrayList<Buy> soldHistory(int itemId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		ArrayList<Buy> soldHistoryList = new ArrayList<Buy>();

		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
				"SELECT count,buy_date "
				+ "FROM buy "
				+ " WHERE item_id = ?");
			st.setInt(1, itemId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Buy soldHistory = new Buy();
				soldHistory.setCount(rs.getInt("count"));
				Date subBuyDate = rs.getDate("buy_date");
				Time subBuyTime = rs.getTime("buy_date");

				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

	            soldHistory.setBuyDate(buyDate);
	            soldHistory.setBuyTime(buyTime);

				soldHistoryList.add(soldHistory);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return soldHistoryList;
	}


}
