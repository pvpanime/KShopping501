package dto;

public class CartDTO {

    private Integer cartId;      // 장바구니 ID
    private Integer userId;      // 사용자 ID
    private Integer productId;   // 상품 ID
    private Integer quantity;    // 상품 수량

    // 기본 생성자
    public CartDTO() {
    }

    // 매개변수가 있는 생성자
    public CartDTO(Integer cartId, Integer userId, Integer productId, Integer quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter 및 Setter
    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "CartDTO{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}

