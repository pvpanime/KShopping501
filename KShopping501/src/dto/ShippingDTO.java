package dto;

import java.sql.Timestamp;

public class ShippingDTO {

    private Integer shippingId;         // 배송 ID
    private Integer orderId;            // 주문 ID
    private String trackingNumber;      // 추적 번호
    private String carrier;             // 배송 업체
    private String status;              // 배송 상태
    private Timestamp estimatedDelivery; // 예상 배송일

    // 기본 생성자
    public ShippingDTO() {
    }

    // 매개변수가 있는 생성자
    public ShippingDTO(Integer shippingId, Integer orderId, String trackingNumber, String carrier, String status, Timestamp estimatedDelivery) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.carrier = carrier;
        this.status = status;
        this.estimatedDelivery = estimatedDelivery;
    }

    // Getter 및 Setter
    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(Timestamp estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "ShippingDTO{" +
                "shippingId=" + shippingId +
                ", orderId=" + orderId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", carrier='" + carrier + '\'' +
                ", status='" + status + '\'' +
                ", estimatedDelivery=" + estimatedDelivery +
                '}';
    }
}

