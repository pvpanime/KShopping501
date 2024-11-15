package dto;

import java.sql.Timestamp;

public class ProductDTO {

    private Integer productId;      // 상품 ID
    private String name;            // 상품 이름
    private String description;     // 상품 설명
    private Integer price;          // 상품 가격
    private Integer stock;          // 재고 수량
    private Integer categoryId;     // 카테고리 ID
    private Timestamp createdAt;    // 생성일 (TIMESTAMP 타입)

    // 기본 생성자
    public ProductDTO() {
    }

    // 매개변수가 있는 생성자
    public ProductDTO(Integer productId, String name, String description, Integer price, Integer stock, Integer categoryId, Timestamp createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
    }

    // Getter 및 Setter
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", categoryId=" + categoryId +
                ", createdAt=" + createdAt +
                '}';
    }
}

