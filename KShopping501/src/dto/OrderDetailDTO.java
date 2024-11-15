package dto;

public class OrderDetailDTO {

    private Integer orderDetailId;  // 주문 상세 ID
    private Integer orderId;        // 주문 ID
    private Integer productId;      // 상품 ID
    private Integer quantity;       // 주문 수량
    private Integer price;          // 상품 가격

    // 기본 생성자
    public OrderDetailDTO() {
    }

    // 매개변수가 있는 생성자
    public OrderDetailDTO(Integer orderDetailId, Integer orderId, Integer productId, Integer quantity, Integer price) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter 및 Setter
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderDetailId=" + orderDetailId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

