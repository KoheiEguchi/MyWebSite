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
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("INSERT INTO favorite (item_id, user_id) VALUES (?, ?)");
			st.setInt(1, itemId);
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return true;
	}

	public boolean deleteFavo(int itemId, int userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("DELETE FROM favorite WHERE item_id = ? AND user_id = ?");
			st.setInt(1, itemId);
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return false;
	}

	public boolean checkFavo(int itemId, int userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"SELECT * FROM favorite WHERE item_id = ? AND user_id = ?");
			st.setInt(1, itemId);
			st.setInt(2, userId);
			ResultSet rs = st.executeQuery();

			if (!(rs.next())) {
				return false;
			}

			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

}
