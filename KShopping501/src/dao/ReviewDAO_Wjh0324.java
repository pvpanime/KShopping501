package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.ReviewDTO_Wjh0324;
import scott.Scott;

public class ReviewDAO_Wjh0324 {
	public ArrayList<ReviewDTO_Wjh0324> getReviewsFor(int productId) {
		final String query = 
				"SELECT U.USERNAME, R.COMMENT_T, R.RATING, R.CREATED_AT \r\n"
				+ "FROM REVIEW_T R \r\n"
				+ "JOIN USER_T U ON R.USER_NUM = U.USER_NUM \r\n"
				+ "WHERE R.PRODUCT_ID = ? \r\n"
				+ "";
		ArrayList<ReviewDTO_Wjh0324> reviews = new ArrayList<>(); 
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try (Connection conn = Scott.getConnection()) {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);
			
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				reviews.add(
						new ReviewDTO_Wjh0324(
								resultSet.getString("username"),
								resultSet.getInt("rating"),
								resultSet.getString("comment_t"),
								resultSet.getTimestamp("created_at")
						)
				);
			}
		} catch (SQLException sex) {
			sex.printStackTrace();
		}
		
		return reviews;
	}
}
