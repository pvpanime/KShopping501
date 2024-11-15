package dto;

import java.sql.Timestamp;

public record ReviewDTO_Wjh0324(
		String productName,
		String userName,
		Integer rating,
		String comment,
		Timestamp createdAt
	) {
	
}
