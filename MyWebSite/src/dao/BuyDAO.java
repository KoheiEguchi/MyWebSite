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
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		//購入記録の通し番号を置く
		int buyId = -1;
		try {
			con = DBManager.getConnection();
			//取得した購入情報と購入日時を記録
			st = con.prepareStatement(
					"INSERT INTO buy(buyer_id,total_price,delivery_method,buy_date) VALUES(?,?,?,now())",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, buy.getBuyId());
			st.setInt(2, buy.getTotalPrice());
			st.setString(3, buy.getDeliveryMethod());
			st.executeUpdate();

			//通し番号を合わせる
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				buyId = rs.getInt(1);
			}

			//通し番号を戻す
			return buyId;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static void insertBuyItem (Buy buyItem) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//insertBuy()で作った通し番号に応じて商品情報を記録
			st = con.prepareStatement(
					"UPDATE buy SET item_id = ?, count = ? WHERE id = ?");
			st.setInt(1, buyItem.getItemId());
			st.setInt(2, buyItem.getCount());
			st.setInt(3, buyItem.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static Item soldCheck(Buy buyItem) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			//取得した商品のIDにおける売上個数の累計を取得
			st = con.prepareStatement(
					"SELECT SUM(count) FROM buy WHERE item_id = ? GROUP BY item_id");
			st.setInt(1, buyItem.getItemId());

			ResultSet rs = st.executeQuery();

			if (!(rs.next())) {
				return null;
			}

			//商品と個数を取得し戻す
			int soldNum = (rs.getInt("SUM(count)"));
			int id = buyItem.getItemId();

			Item sold = new Item(soldNum, id);

			return sold;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static ArrayList<Buy> buyIdSelect() throws SQLException, ParseException {
		//DB起動
		Connection con = null;
		PreparedStatement preSt = null;
		PreparedStatement st = null;
		ArrayList<Buy> buyIdList = new ArrayList<Buy>();

		try {
			con = DBManager.getConnection();

			//現在時刻を分単位までの数列にする
			preSt = con.prepareStatement(
					"SELECT DATE_FORMAT(now(), '%Y%m%d %H%i') AS string_now");
			ResultSet preRs = preSt.executeQuery();
			if(!(preRs.next())) {
				return null;
			}

			//数列をSQLで検索可能な形式に変更
			String preSql = null;
			preSql = preRs.getString("string_now");

			StringBuilder buildSql = new StringBuilder(preSql);
			buildSql.insert(4, "-");
			buildSql.insert(7, "-");
			buildSql.insert(13, ":");
			String sql = buildSql.toString();

			//分単位までの現在時刻における購入記録を取得
			st = con.prepareStatement(
					"SELECT id FROM buy WHERE buy_date LIKE '" + sql + "%'");
			ResultSet rs = st.executeQuery();

			//取得結果をリストにまとめる
			while (rs.next()) {
				int id = (rs.getInt("id"));

				Buy buyId = new Buy(id);

				buyIdList.add(buyId);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
		//リストを戻す
		return buyIdList;
	}

	public static void insertBuyId(Buy buyId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//取得した購入ID以下のID＝今回購入した記録のIDを全て統一する
			st = con.prepareStatement(
					"UPDATE buy SET buy_id = ? WHERE id >= ?");
			st.setInt(1, buyId.getId());
			st.setInt(2, buyId.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static ArrayList<Buy> boughtData(String userId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		ArrayList<Buy> boughtList = new ArrayList<Buy>();
		try {
			con = DBManager.getConnection();
			//対象ユーザーの購入履歴を取得
			st = con.prepareStatement(
					"SELECT DISTINCT buyer_id, total_price, delivery_method, buy_date, buy_id FROM buy WHERE buyer_id = ? GROUP BY buy_id");
			st.setString(1, userId);
			ResultSet rs = st.executeQuery();

			//各情報を取得
			while (rs.next()) {
				int buyerId = (rs.getInt("buyer_id"));
				int totalPrice = (rs.getInt("total_price"));
				String deliveryMethod = (rs.getString("delivery_method"));
				Date subBuyDate = (rs.getDate("buy_date"));
				Time subBuyTime = (rs.getTime("buy_date"));
				int buyId = (rs.getInt("buy_id"));

				//購入日時の書体を変更
				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

				Buy bought = new Buy(buyerId,totalPrice,deliveryMethod,buyDate,buyTime,buyId);

				//取得した各情報を購入リストに追加
				boughtList.add(bought);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
		//購入リストを戻す
		return boughtList;
	}

	public static Item boughtDetail(int buyerId, int buyId, String buyDate, String buyTime) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();

			//対象のユーザーの購入記録における総額と配送方法を取得
			st = con.prepareStatement(
					"SELECT DISTINCT total_price, delivery_method FROM buy WHERE buyer_id = ? AND buy_id = ?");
			st.setInt(1, buyerId);
			st.setInt(2, buyId);

			ResultSet rs = st.executeQuery();

			if (!(rs.next())) {
				return null;
			}

			//両データを取得
			int totalPrice = (rs.getInt("total_price"));
			String deliveryMethod = (rs.getString("delivery_method"));

			//取得した配送方法をもとに送料を取得
			int deliPrice = 0;
			switch(deliveryMethod) {
				case "通常配送" : deliPrice = 0;
				break;

				case "お急ぎ配送" : deliPrice = 500;
				break;

				case "日時指定配送" : deliPrice = 200;
				break;
			}

			//取得した各データを戻す
			Item buyHistory = new Item(totalPrice, buyDate, buyTime, deliveryMethod, deliPrice);

			return buyHistory;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<Item> boughtListData(int buyerId, int buyId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		ArrayList<Item> buyHistoryDetailList = new ArrayList<Item>();

		try {
			con = DBManager.getConnection();

			//対象のユーザーの購入商品の名前、価格、購入個数を取得
			st = con.prepareStatement(
				"SELECT name,price,count "
				+ "FROM item "
				+ "JOIN buy ON item.id = buy.item_id "
				+ " WHERE buy.buyer_id = ? AND buy.buy_id = ?");
			st.setInt(1, buyerId);
			st.setInt(2, buyId);

			ResultSet rs = st.executeQuery();

			//各データを購入詳細リストに追加
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

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
		//購入詳細リストを戻す
		return buyHistoryDetailList;
	}

	public ArrayList<Buy> soldHistory(int itemId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		ArrayList<Buy> soldHistoryList = new ArrayList<Buy>();

		try {
			con = DBManager.getConnection();

			//対象商品の購入個数と購入日時を取得
			st = con.prepareStatement(
				"SELECT count,buy_date "
				+ "FROM buy "
				+ " WHERE item_id = ?");
			st.setInt(1, itemId);

			ResultSet rs = st.executeQuery();

			//各データを取得
			while (rs.next()) {
				Buy soldHistory = new Buy();
				soldHistory.setCount(rs.getInt("count"));
				//購入日時は年月日と時分秒で分けて取得
				Date subBuyDate = rs.getDate("buy_date");
				Time subBuyTime = rs.getTime("buy_date");

				//購入日時の書体を変更
				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

	            soldHistory.setBuyDate(buyDate);
	            soldHistory.setBuyTime(buyTime);

				//販売履歴リストに追加
	            soldHistoryList.add(soldHistory);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);

		//DB切断
		} finally {
			if (con != null) {
				con.close();
			}
		}
		//販売履歴リストを戻す
		return soldHistoryList;
	}

	public static ArrayList<Buy> allOrder(){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Buy> orderList = new ArrayList<Buy>();
        try {
        	con = DBManager.getConnection();
        	//未発送の全注文記録を取得
        	st = con.prepareStatement(
        			"SELECT buy.buyer_id, buy.total_price, buy.buy_date, user.name FROM buy "
        			+ "JOIN user ON buy.buyer_id = user.id "
        			+ "WHERE buy.delivery_check = '0' ORDER BY buy.buy_date");
            ResultSet rs = st.executeQuery();

            //各情報を取得し注文リストに追加
            while (rs.next()) {
                int buyerId = rs.getInt("buyer_id");
                int totalPrice = rs.getInt("total_price");
                String name = rs.getString("name");
                //購入日時は年月日と時分秒で分けて取得
                Date subBuyDate = rs.getDate("buy_date");
                Time subBuyTime = rs.getTime("buy_date");

                //購入日時の書体を変更
				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

	            Buy order = new Buy(buyerId, totalPrice, name);
	            order.setBuyDate(buyDate);
	            order.setBuyTime(buyTime);

	            //注文リストに追加
	            orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        //DB切断
        } finally {
        	 if (con != null) {
                 try {
                     con.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                     return null;
                 }
             }
        }
        //注文リストを戻す
        return orderList;
	}

	public static ArrayList<Buy> selectOrder(String select){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Buy> orderList = new ArrayList<Buy>();
        try {
        	con = DBManager.getConnection();
        	//未発送の全注文記録を取得し選択された基準で並び替え
        	st = con.prepareStatement(
        			"SELECT buy.buyer_id, buy.total_price, buy.buy_date, user.name FROM buy "
        			+"JOIN user ON buy.buyer_id = user.id "
        			+"WHERE buy.delivery_check = '0' ORDER BY ?");
        	st.setString(1, select);
            ResultSet rs = st.executeQuery();

            //各情報を取得し注文リストに追加
            while (rs.next()) {
                int buyerId = rs.getInt("buyer_id");
                int totalPrice = rs.getInt("total_price");
                String name = rs.getString("name");
                //購入日時は年月日と時分秒で分けて取得
                Date subBuyDate = rs.getDate("buy_date");
                Time subBuyTime = rs.getTime("buy_date");

                //購入日時の書体を変更
				SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'dd'日'");
	            fmD.setLenient(false);
	            SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
	            fmT.setLenient(false);

	            String buyDate = fmD.format(subBuyDate);
	            String buyTime = fmT.format(subBuyTime);

	            Buy order = new Buy(buyerId, totalPrice, name);
	            order.setBuyDate(buyDate);
	            order.setBuyTime(buyTime);

	            //注文リストに追加
	            orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        //DB切断
        } finally {
        	 if (con != null) {
                 try {
                     con.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                     return null;
                 }
             }
        }
        //注文リストを戻す
        return orderList;
	}

}
