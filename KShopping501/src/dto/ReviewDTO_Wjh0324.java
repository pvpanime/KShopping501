package dto;

import java.sql.Timestamp;

public record ReviewDTO_Wjh0324(
		String userName,
		int rating,
		String comment,
		Timestamp createdAt
	) {
	
}
