package dao;

import dto.UserDTO;
import java.sql.*;

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

	// 로그인 처리
	public boolean login(String email, String password) {
		String sql = "SELECT * FROM USER_T WHERE email = ? AND password = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // 로그인 성공 여부
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 이메일로 사용자 정보 조회
	public UserDTO getUserByEmail(String email) {
		String sql = "SELECT * FROM USER_T WHERE email = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Integer userId = rs.getInt("user_num");
					String username = rs.getString("username");
					String password = rs.getString("password");
					Timestamp createdAt = rs.getTimestamp("created_at");
					Boolean isAdmin = "Y".equals(rs.getString("is_admin"));

					return new UserDTO(userId, username, email, password, createdAt, isAdmin);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean register(UserDTO user) {
		String sql = "INSERT INTO USER_T ( username, email, password, is_admin) "
				+ "VALUES ( ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getIsAdmin() ? "Y" : "N");

			int result = stmt.executeUpdate();
			return result > 0; // 성공적으로 삽입되면 true 반환
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // 오류가 발생하면 false 반환
		}
	}

	public boolean updateUser(UserDTO user) {
		String sql = "UPDATE USER_T SET username = ?, email = ?, password = ? WHERE user_num = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setInt(4, user.getUserId()); // userId로 사용자 식별

			int result = stmt.executeUpdate();
			return result > 0; // 업데이트 성공 여부 반환
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
