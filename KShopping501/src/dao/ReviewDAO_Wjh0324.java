package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.ReviewDTO_Wjh0324;
import scott.Scott;

public class ReviewDAO_Wjh0324 {
	public ArrayList<ReviewDTO_Wjh0324> getReviewsFor(int productCode) {
		final String query = "";
		ArrayList<ReviewDTO_Wjh0324> reviews = new ArrayList<>(); 
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try (Connection conn = Scott.getConnection()) {
			
		} catch (SQLException sex) {
			
		}
		
		return reviews;
	}
}
