package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import base.DBManager;

public class FavoriteDAO {

	public static FavoriteDAO getInstance() {
		return new FavoriteDAO();
	}

	public boolean insertFavo(int itemId, int userId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//対象ユーザーが対象商品をお気に入りに登録
			st = con.prepareStatement("INSERT INTO favorite (item_id, user_id) VALUES (?, ?)");
			st.setInt(1, itemId);
			st.setInt(2, userId);
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
		//登録済みの動作にする
		return true;
	}

	public boolean deleteFavo(int itemId, int userId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//対象ユーザーが対象商品をお気に入りから解除
			st = con.prepareStatement("DELETE FROM favorite WHERE item_id = ? AND user_id = ?");
			st.setInt(1, itemId);
			st.setInt(2, userId);
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
		//未登録の動作にする
		return false;
	}

	public boolean checkFavo(int itemId, int userId) throws SQLException {
		//DB起動
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			//対象のユーザーが対象の商品をお気に入り登録しているかの確認
			st = con.prepareStatement(
					"SELECT * FROM favorite WHERE item_id = ? AND user_id = ?");
			st.setInt(1, itemId);
			st.setInt(2, userId);
			ResultSet rs = st.executeQuery();

			//登録していない時
			if (!(rs.next())) {
				return false;
			}

			//登録している時
			return true;

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

}
