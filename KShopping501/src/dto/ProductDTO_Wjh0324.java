package dto;

import java.sql.Timestamp;

public record ProductDTO_Wjh0324(
		int productId,
		String name,
		String description,
		int price,
		int stock,
		String category,
		Timestamp createdAt) {

}
