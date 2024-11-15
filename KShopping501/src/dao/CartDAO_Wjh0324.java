package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import scott.Scott;

public class CartDAO_Wjh0324 {
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
