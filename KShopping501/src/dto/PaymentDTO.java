package dto;

import java.sql.Timestamp;

public class PaymentDTO {

    private Integer paymentId;         // 결제 ID
    private Integer orderId;           // 주문 ID
    private Timestamp paymentDate;     // 결제 날짜
    private String paymentMethod;      // 결제 방법 (카드, 현금 등)
    private Integer amount;            // 결제 금액

    // 기본 생성자
    public PaymentDTO() {
    }

    // 매개변수가 있는 생성자
    public PaymentDTO(Integer paymentId, Integer orderId, Timestamp paymentDate, String paymentMethod, Integer amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    // Getter 및 Setter
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", paymentDate=" + paymentDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", amount=" + amount +
                '}';
    }
}


