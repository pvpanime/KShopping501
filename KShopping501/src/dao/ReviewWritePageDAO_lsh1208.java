package dao;

import dto.ReviewDTO;
import java.sql.*;

public class ReviewWritePageDAO_lsh1208 {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String userid = "scott";
    private String passwd = "tiger";

    public ReviewWritePageDAO_lsh1208() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveReview(ReviewDTO review) throws SQLException {
        String query = "INSERT INTO review_t (product_id, user_num, rating, comment_t) " +
                "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, review.getProductId());
            ps.setLong(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // If at least one row was inserted, return true
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rethrow exception to be caught in the UI layer
        }
    }
}

