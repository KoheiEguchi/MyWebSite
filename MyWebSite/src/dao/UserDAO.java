package dao;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import base.DBManager;
import beans.User;

public class UserDAO {

	public static UserDAO getInstance() {
		return new UserDAO();
	}

	public User Login(String loginId, String password) {
		 Connection conn = null;
	        try {
	        	 conn = DBManager.getConnection();

	        	 String sql = "SELECT id, name, address FROM user WHERE login_id = ? AND password = ?";

	        	 PreparedStatement pStmt = conn.prepareStatement(sql);
	             pStmt.setString(1, loginId);
	             pStmt.setString(2, encryption(password));
	             ResultSet rs = pStmt.executeQuery();

		        if (!rs.next()) {
	                return null;
	            }

	            int id = rs.getInt("id");
		        String userName = rs.getString("name");
		        String address = rs.getString("address");
	            return new User(id,userName,address);

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
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

	private String encryption(String password) {
		Charset charset = StandardCharsets.UTF_8;
		String algorithm = "MD5";

		byte[] bytes = null;
		try {
			bytes = MessageDigest.getInstance(algorithm).digest(password.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String result = DatatypeConverter.printHexBinary(bytes);
		return result;

	}

	public int PassChange(String loginId, String userName, String address) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT id FROM user WHERE login_id = ? AND name = ? AND address = ?");
			st.setString(1, loginId);
			st.setString(2, userName);
			st.setString(3, address);
			ResultSet rs = st.executeQuery();

			int userId = 0;

			if (!rs.next()) {
                return userId;
            }

			userId = rs.getInt("id");
			return userId;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public void PassUpdate(int userId, String password) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
			st.setString(1, encryption(password));
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
	}

	public boolean CreateCheck(String loginId){
        Connection conn = null;
        try {
        	 conn = DBManager.getConnection();

        	 String sql = "SELECT * FROM user WHERE login_id = ?";
        	 PreparedStatement pStmt = conn.prepareStatement(sql);
             pStmt.setString(1, loginId);
             ResultSet rs = pStmt.executeQuery();

             boolean isExists = rs.next();
             if(isExists == false){
                 return true;
             }return false;
         } catch (SQLException e) {
             e.printStackTrace();
             return false;
         } finally {
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

	public void UserCreate(String loginId, String userName, String password, String address) {
        Connection conn = null;
        try {
        	 conn = DBManager.getConnection();

        	 String sql = "INSERT INTO user(login_id,name,password,address,create_date,update_date)VALUES(?,?,?,?,now(),now())";
        	 PreparedStatement pStmt = conn.prepareStatement(sql);
             pStmt.setString(1, loginId);
             pStmt.setString(2, userName);
             pStmt.setString(3, encryption(password));
             pStmt.setString(4, address);
 			 pStmt.executeUpdate();
        	 pStmt.close();

        }catch(SQLException e) {
        	e.printStackTrace();
        }finally {
        	if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	public User UserData(String userId) throws ParseException{
        Connection conn = null;

        try {
        	 conn = DBManager.getConnection();

        	 String sql = "SELECT id, name, address, login_id, create_date, update_date FROM user WHERE id = ?";

        	 PreparedStatement pStmt = conn.prepareStatement(sql);
             pStmt.setString(1, userId);
             ResultSet rs = pStmt.executeQuery();

             if (!rs.next()) {
                 return null;
             }

             int id = rs.getInt("id");
             String userName = rs.getString("name");
             String address = rs.getString("address");
             String loginId = rs.getString("login_id");
             Date subCreateDate = rs.getDate("create_date");
             Time subCreateTime = rs.getTime("create_date");
             Date subUpdateDate = rs.getDate("update_date");
             Time subUpdateTime = rs.getTime("update_date");

             SimpleDateFormat fmD = new SimpleDateFormat("yyyy'年'M'月'd'日'");
             fmD.setLenient(false);
             SimpleDateFormat fmT = new SimpleDateFormat("k'時'mm'分'ss'秒'");
             fmT.setLenient(false);

             String createDate = fmD.format(subCreateDate);
             String createTime = fmT.format(subCreateTime);
             String updateDate = fmD.format(subUpdateDate);
             String updateTime = fmT.format(subUpdateTime);

             return new User(id, userName, address, loginId, createDate, createTime, updateDate, updateTime);

         } catch (SQLException e) {
             e.printStackTrace();
             return null;
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

	public boolean UserUpdate(int id, String loginId, String userName, String password, String address) {
		 Connection conn = null;
		 try {
			 conn = DBManager.getConnection();

			 String sql = "UPDATE user SET name = ?, address = ?, login_id = ?, password = ?, update_date = now() WHERE id = ?";
			 PreparedStatement pStmt = conn.prepareStatement(sql);
			 pStmt.setString(1, userName);
			 pStmt.setString(2, address);
			 pStmt.setString(3, loginId);
			 pStmt.setString(4, encryption(password));
			 pStmt.setInt(5, id);
			 pStmt.executeUpdate();
			 pStmt.close();
			 return true;

		 }catch(SQLException e) {
			 e.printStackTrace();
			 return false;
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

	public boolean UserDelete(int id) {
		 Connection conn = null;
		 try {
			 conn = DBManager.getConnection();

			 String sql = "DELETE FROM user WHERE id = ?";
			 PreparedStatement pStmt = conn.prepareStatement(sql);
			 pStmt.setInt(1, id);
			 pStmt.executeUpdate();
			 pStmt.close();
			 return true;

		 }catch(SQLException e) {
			 e.printStackTrace();
			 return false;
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

}
