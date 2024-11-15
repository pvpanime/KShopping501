package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.CartDTO;
import scott.Scott;

public class CartDAO_Wjh0324 {

	public CartDTO getCartOf(int userNum, int productId) {
		final String query = "SELECT CART_ID, USER_NUM, PRODUCT_ID, QUANTITY FROM CART_T WHERE USER_NUM = ? AND PRODUCT_ID = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try (Connection conn = Scott.getConnection()) {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userNum);
			stmt.setInt(2, productId);
			rs = stmt.executeQuery();
			if (!rs.next()) return null;
			return new CartDTO(
				rs.getInt("CART_ID"),
				rs.getInt("USER_NUM"),
				rs.getInt("PRODUCT_ID"),
				rs.getInt("QUANTITY")
			);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addCart(int userNum, int productId, int quantity) {
		final String sql = "INSERT INTO CART_T (USER_NUM, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?)";
		PreparedStatement stmt = null;
		try (Connection conn = Scott.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userNum);
			stmt.setInt(2, productId);
			stmt.setInt(3, quantity);
			int count = stmt.executeUpdate();
			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {				
				if(stmt != null) stmt.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
