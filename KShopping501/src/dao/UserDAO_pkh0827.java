package dao;

import dto.UserDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_pkh0827 {
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_USER = "scott";
	private static final String DB_PASSWORD = "tiger";

	public Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Database connection error");
		}
	}

	public boolean login(String email, String password) {
		String sql = "SELECT * FROM USER_T WHERE email = ? AND password = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean register(UserDTO user) {
		String sql = "INSERT INTO USER_T (user_num, username, email, password, is_admin) "
				+ "VALUES (USER_T_SEQ.NEXTVAL, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getIsAdmin() ? "Y" : "N");

			int result = stmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
