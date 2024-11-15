package dto;

import java.sql.Timestamp;

public class OrderDTO {

    private Integer orderId;        // 주문 ID
    private Integer userId;         // 사용자 ID
    private Timestamp orderDate;    // 주문 날짜
    private String status;          // 주문 상태 (예: 배송 중, 완료)
    private Integer totalAmount;    // 총 주문 금액

    // 기본 생성자
    public OrderDTO() {
    }

    // 매개변수가 있는 생성자
    public OrderDTO(Integer orderId, Integer userId, Timestamp orderDate, String status, Integer totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getter 및 Setter
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}


