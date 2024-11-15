package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ProductDTO;
import scott.Scott;

public class ProductRandomSelectDAO_Wjh0324 {
	public ProductDTO pick() {
		final String query = "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT *\r\n"
				+ "    FROM PRODUCT_T\r\n"
				+ "    ORDER BY DBMS_RANDOM.VALUE\r\n"
				+ ")\r\n"
				+ "WHERE ROWNUM = 1 ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try (Connection conn = Scott.getConnection()) {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			if (!rs.next()) return null;
			return new ProductDTO(
				rs.getInt("product_id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getInt("price"),
				rs.getInt("stock"),
				rs.getInt("category_id"),
				rs.getTimestamp("created_at")
			);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
