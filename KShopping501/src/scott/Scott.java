package scott;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Scott {
	public static final String driver = "oracle.jdbc.driver.OracleDriver";
	public static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String userid = "scott";
	public static final String passwd = "tiger";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println("이런!! 데이터베이스 클래스를 찾는 데 실패했습니다. 라이브러리를 다시 확인해주세요.\n데이터베이스 관련 기능이 모두 동작하지 않을 것입니다.");
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, userid, passwd);
	}
}
