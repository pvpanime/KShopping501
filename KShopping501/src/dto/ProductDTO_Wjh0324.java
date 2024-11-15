package dto;

import java.sql.Timestamp;

public record ProductDTO_Wjh0324(
		int productId,
		String name,
		String description,
		int price,
		String category,
		Timestamp createdAt) {

}
