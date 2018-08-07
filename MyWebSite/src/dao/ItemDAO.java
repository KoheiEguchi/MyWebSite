package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DBManager;
import beans.Item;

public class ItemDAO {

	public static ItemDAO getInstance() {
		return new ItemDAO();
	}

	public static ArrayList<Item> recommendItem(int userId){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Item> itemList = new ArrayList<Item>();
	    try {
	        	con = DBManager.getConnection();
	        	//対象のユーザーが最後に買った商品と同じ種類の商品を取得
	        	st = con.prepareStatement(
	        			"SELECT * FROM item "
	        			+ "WHERE item.type = ("
	        				+ "SELECT DISTINCT item.type FROM item "
	        				+ "JOIN buy ON item.id = buy.item_id "
	        				+ "WHERE buy.buy_date = ("
	        					+ "SELECT DISTINCT max(buy.buy_date) "
	        					+ "FROM buy "
	        					+ "WHERE buy.buyer_id = ?"
	        				+ ") HAVING max(buy.id)"
	        			+ ")"
	        	);
	        	st.setInt(1, userId);
	            ResultSet rs = st.executeQuery();

	            //対象商品ごとの各種情報を取得、リストに記録
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String itemName = rs.getString("name");
	                String itemDetail = rs.getString("detail");
	                String type = rs.getString("type");
	                int price = rs.getInt("price");
	                int soldNum = rs.getInt("sold_num");
	                String fileName = rs.getString("file_Name");
	                Item item = new Item(id, itemName, itemDetail, type, price, soldNum, fileName);

	                itemList.add(item);
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
	    //リストを戻す
        return itemList;
	}

	public static ArrayList<Item> newUser(){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Item> itemList = new ArrayList<Item>();
        try {
        	con = DBManager.getConnection();
        	//売り上げ上位4種を取得
        	st = con.prepareStatement("SELECT * FROM item ORDER BY sold_num DESC LIMIT 4");
            ResultSet rs = st.executeQuery();

          //対象商品ごとの各種情報を取得、リストに記録
            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item item = new Item(id, itemName, itemDetail, price, soldNum, fileName);

                itemList.add(item);
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
        //リストを戻す
        return itemList;
	}

	public static ArrayList<Item> allItem(){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Item> itemList = new ArrayList<Item>();
        try {
        	con = DBManager.getConnection();
        	//全商品を取得
        	st = con.prepareStatement("SELECT * FROM item");
            ResultSet rs = st.executeQuery();

            //各情報を取得し商品リストに追加
            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                String type = rs.getString("type");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item item = new Item(id, itemName, itemDetail, type, price, soldNum, fileName);

                itemList.add(item);
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
        //商品リストを戻す
        return itemList;
	}

	public static ArrayList<Item> ranking(){
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
        ArrayList<Item> itemList = new ArrayList<Item>();
        try {
        	con = DBManager.getConnection();
        	//売り上げ上位6種を取得
        	st = con.prepareStatement("SELECT * FROM item ORDER BY sold_num DESC LIMIT 6");
            ResultSet rs = st.executeQuery();

            //対象商品ごとの各種情報を取得、リストに記録
            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item item = new Item(id, itemName, itemDetail, price, soldNum, fileName);

                itemList.add(item);
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
        //リストを戻す
        return itemList;
	}

	public Item detail(String detailId){
        //DB起動
		Connection conn = null;

        try {
        	 conn = DBManager.getConnection();

        	 //IDから対象商品の情報を取得
        	 String sql = "SELECT * FROM item WHERE id = ?";

        	 PreparedStatement pStmt = conn.prepareStatement(sql);
             pStmt.setString(1, detailId);
             ResultSet rs = pStmt.executeQuery();

             //対象商品が存在するかの確認
             if (!rs.next()) {
                 return null;
             }

             //各種情報を取得
             int id = rs.getInt("id");
             String itemName = rs.getString("name");
             String itemDetail = rs.getString("detail");
             String type = rs.getString("type");
             int price = rs.getInt("price");
             int soldNum = rs.getInt("sold_num");
             String fileName = rs.getString("file_name");

             return new Item(id, itemName, itemDetail, type, price, soldNum, fileName);

         } catch (SQLException e) {
             e.printStackTrace();
             return null;

         //DB切断
         } finally {
        	 if (conn != null) {
                 try {
                     conn.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                     return null;
                 }
             }
		}
	}

	public void ItemCreate(String itemName,String itemDetail,String type,int price,String fileName) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//取得したデータをDBに記録
			st = con.prepareStatement("INSERT INTO item(name,detail,type,price,file_name) VALUES(?,?,?,?,?)");
			st.setString(1, itemName);
			st.setString(2, itemDetail);
			st.setString(3, type);
			st.setInt(4, price);
			st.setString(5, fileName);
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

	public void ItemUpdate(int id, String itemName, String itemDetail, String type, int price, String fileName) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//対象商品の各データを更新
			st = con.prepareStatement("UPDATE item SET name = ?, detail = ?, type = ?, price = ?, file_name = ? WHERE id = ?");
			st.setString(1, itemName);
			st.setString(2, itemDetail);
			st.setString(3, type);
			st.setInt(4, price);
			st.setString(5, fileName);
			st.setInt(6, id);
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

	public boolean ItemDelete(int id) {
		//DB起動
		Connection conn = null;
		try {
			 conn = DBManager.getConnection();

			 //対象の商品を削除
			 String sql = "DELETE FROM item WHERE id = ?";
			 PreparedStatement pStmt = conn.prepareStatement(sql);
			 pStmt.setInt(1, id);
			 pStmt.executeUpdate();
			 pStmt.close();
			 return true;

		}catch(SQLException e) {
			 e.printStackTrace();
			 return false;

		//DB切断
		}finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 e.printStackTrace();
					 return false;
				 }
			 }
		}
	 }

	public static Item CartIn(String itemId, int count){
		//DB起動
		Connection conn = null;

        try {
        	 conn = DBManager.getConnection();

        	 //対象の商品の情報を取得
        	 String sql = "SELECT * FROM item WHERE id = ?";

        	 PreparedStatement pStmt = conn.prepareStatement(sql);
             pStmt.setString(1, itemId);
             ResultSet rs = pStmt.executeQuery();

             //対象商品が存在するかの確認
             if (!rs.next()) {
                 return null;
             }

             //各種情報を取得
             int id = rs.getInt("id");
             String itemName = rs.getString("name");
             String itemDetail = rs.getString("detail");
             int price = rs.getInt("price");
             String fileName = rs.getString("file_name");

             //単価×個数で金額を求める
             int countPrice = price * count;

             //各種情報を戻す
             return new Item(id, itemName, itemDetail, price, fileName, count, countPrice);

         } catch (SQLException e) {
             e.printStackTrace();
             return null;

         //DB切断
         } finally {
        	 if (conn != null) {
                 try {
                     conn.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                     return null;
                 }
             }
		}
	}

	public static void insertSold(Item sold) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();

			//対象の商品の販売個数を更新
			st = con.prepareStatement(
					"UPDATE item SET sold_num = ? WHERE id = ?");
			st.setInt(1, sold.getSoldNum());
			st.setInt(2, sold.getId());
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

	public ArrayList<Item> search(String searchName, String searchType, String searchPrice, boolean searchFavorite, int userId) {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		int priceLow = 0;
		int priceHigh = 999999999;

		//指定された価格範囲を設定
		switch(searchPrice) {
			case "0" : priceLow = 0; priceHigh = 999999999;
			break;

			case "1000" : priceHigh = 1000;
			break;

			case "3000" : priceLow = 1001; priceHigh = 3000;
			break;

			case "5000" : priceLow = 3001; priceHigh = 5000;
			break;

			case "10000" : priceLow = 5001; priceHigh = 10000;
			break;

			case "20000" : priceLow = 10001; priceHigh = 20000;
			break;

			case "20001" : priceLow = 20001;
			break;
		}
		ArrayList<Item> searchItemList = new ArrayList<Item>();
		try {
			con = DBManager.getConnection();

			//お気に入り絞りをしていない場合
			String sql = "SELECT * FROM item "
        				 + "WHERE name LIKE '%" + searchName + "%' "
        				 + "AND price >= '" + priceLow + "' "
        				 + "AND price <= '" + priceHigh + "'";
			//お気に入り絞りをしている場合
			if(searchFavorite == true) {
				sql = "SELECT * FROM item "
						+ "JOIN favorite ON item.id = favorite.item_id "
						+ "WHERE item.name LIKE '%" + searchName + "%' "
    						+ "AND item.price >= '" + priceLow + "' "
    						+ "AND item.price <= '" + priceHigh + "' "
    						+ "AND favorite.id IN("
    							+ "SELECT favorite.id FROM favorite "
    							+ "WHERE item.name LIKE '%" + searchName + "%' "
    								+ "AND favorite.user_id = '" + userId + "')";
			}
			//種類指定をしている場合
    		if(!(searchType.equals("all"))){
				sql = sql + "AND type = '" + searchType + "' ";
			}
			st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            //指定された条件に当てはまる商品の情報を取得
            while (rs.next()) {
            	int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item searchItem = new Item(id, itemName, itemDetail, price, soldNum, fileName);

                //情報をリストにまとめる
                searchItemList.add(searchItem);
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
		//リストを戻す
        return searchItemList;
	}

	public ArrayList<Item> rankingSearch(String searchName, String searchType, String searchPrice, boolean searchFavorite, int userId, int rankNum) {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		int priceLow = 0;
		int priceHigh = 999999999;

		//指定された価格範囲を設定
		switch(searchPrice) {
			case "0" : priceLow = 0; priceHigh = 999999999;
			break;

			case "1000" : priceHigh = 1000;
			break;

			case "3000" : priceLow = 1001; priceHigh = 3000;
			break;

			case "5000" : priceLow = 3001; priceHigh = 5000;
			break;

			case "10000" : priceLow = 5001; priceHigh = 10000;
			break;

			case "20000" : priceLow = 10001; priceHigh = 20000;
			break;

			case "20001" : priceLow = 20001;
			break;
		}
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			con = DBManager.getConnection();

			//お気に入り絞りをしていない場合
			String sql = "SELECT * FROM item "
        				 + "WHERE name LIKE '%" + searchName + "%' "
        				 + "AND price >= '" + priceLow + "' "
        				 + "AND price <= '" + priceHigh + "'";
			//お気に入り絞りをしている場合
			if(searchFavorite == true) {
				sql = "SELECT * FROM item "
						+ "JOIN favorite ON item.id = favorite.item_id "
						+ "WHERE item.name LIKE '%" + searchName + "%' "
    						+ "AND item.price >= '" + priceLow + "' "
    						+ "AND item.price <= '" + priceHigh + "' "
    						+ "AND favorite.id IN("
    							+ "SELECT favorite.id FROM favorite "
    							+ "WHERE item.name LIKE '%" + searchName + "%' "
    								+ "AND favorite.user_id = '" + userId + "')";
			}
			//種類指定をしている場合
			if(!(searchType.equals("all"))){
				sql = sql + "AND type = '" + searchType + "' ";
			}
			//表示順位を指定
    		sql = sql + "ORDER BY sold_num DESC LIMIT " + rankNum;
			st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            //指定された条件に当てはまる商品の情報を取得
            while (rs.next()) {
            	int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item searchItem = new Item(id, itemName, itemDetail, price, soldNum, fileName);

                //情報をリストにまとめる
                itemList.add(searchItem);
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
		//リストを戻す
        return itemList;
	}

	public ArrayList<Item> soldSearch(String searchName, String searchType, String searchPrice, boolean searchSold, int userId) {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;

		int priceLow = 0;
		int priceHigh = 999999999;

		//指定された価格範囲を設定
		switch(searchPrice) {
			case "0" : priceLow = 0; priceHigh = 999999999;
			break;

			case "1000" : priceHigh = 1000;
			break;

			case "3000" : priceLow = 1001; priceHigh = 3000;
			break;

			case "5000" : priceLow = 3001; priceHigh = 5000;
			break;

			case "10000" : priceLow = 5001; priceHigh = 10000;
			break;

			case "20000" : priceLow = 10001; priceHigh = 20000;
			break;

			case "20001" : priceLow = 20001;
			break;
		}
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			con = DBManager.getConnection();
				//商品を絞り込む
				String sql =
						"SELECT * FROM item "
			        	+ "WHERE name LIKE '%" + searchName + "%' "
			        		+ "AND price >= '" + priceLow + "' "
			        		+ "AND price <= '" + priceHigh + "' ";
				//種類指定をしている場合
				if(!(searchType.equals("all"))) {
					sql = sql + "AND type = '" + searchType + "' ";
				}
				//売り上げ順に並び変える場合
				if(searchSold == true) {
					sql = sql + "ORDER BY sold_num DESC";
				}
			st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            //各情報を取得し商品リストに追加
            while (rs.next()) {
            	int id = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemDetail = rs.getString("detail");
                int price = rs.getInt("price");
                int soldNum = rs.getInt("sold_num");
                String fileName = rs.getString("file_Name");
                Item item = new Item(id, itemName, itemDetail, price, soldNum, fileName);

                itemList.add(item);
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
		//商品リストを戻す
        return itemList;
	}

}
