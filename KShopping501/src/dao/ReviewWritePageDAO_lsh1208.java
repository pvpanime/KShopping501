package dao;

import dto.ReviewDTO;
import java.sql.*;

public class ReviewWritePageDAO_lsh1208 {
    private String driver = "oracle.jdbc.driver.OracleDriver";  // Oracle JDBC 드라이버
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";  // 데이터베이스 URL
    private String userid = "scott";  // 데이터베이스 사용자 ID
    private String passwd = "tiger";  // 데이터베이스 사용자 비밀번호

    // 생성자: 데이터베이스 드라이버 로드
    public ReviewWritePageDAO_lsh1208() {
        try {
            Class.forName(driver);  // Oracle 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // 드라이버 로드 실패 시 예외 출력
        }
    }

    // 리뷰 저장 메서드
    public boolean saveReview(ReviewDTO review) throws SQLException {
        // SQL 쿼리문: review_t 테이블에 새로운 리뷰 데이터를 삽입
        String query = "INSERT INTO review_t (product_id, user_num, rating, comment_t) " +
                       "VALUES (?, ?, ?, ?)";
        
        // 데이터베이스 연결 및 PreparedStatement 생성
        try (Connection conn = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement ps = conn.prepareStatement(query)) {

            // PreparedStatement에 파라미터 설정
            ps.setInt(1, review.getProductId());  // 제품 ID 설정
            ps.setInt(2, review.getUserId());     // 사용자 ID 설정
            ps.setInt(3, review.getRating());     // 별점 설정
            ps.setString(4, review.getComment()); // 리뷰 내용 설정

            // SQL 실행 후, 영향을 받은 행의 개수 확인
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // 영향을 받은 행이 있으면 true 반환

        } catch (SQLException e) {
            e.printStackTrace();  // SQL 예외 발생 시 예외 출력
            throw e;  // 예외를 다시 던져서 UI 계층에서 처리할 수 있도록 전달
        }
    }
}
