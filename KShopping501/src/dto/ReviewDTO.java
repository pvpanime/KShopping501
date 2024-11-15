package dto;

import java.sql.Timestamp;

public class ReviewDTO {

    private Integer reviewId;      // 리뷰 ID
    private Integer productId;     // 상품 ID
    private Integer userId;        // 사용자 ID
    private Integer rating;        // 평점 (1~5)
    private String comment;        // 리뷰 내용
    private Timestamp createdAt;   // 리뷰 작성일

    // 기본 생성자
    public ReviewDTO() {
    }

    // 매개변수가 있는 생성자
    public ReviewDTO(Integer reviewId, Integer productId, Integer userId, Integer rating, String comment, Timestamp createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getter 및 Setter
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        return "ReviewDTO{" +
                "reviewId=" + reviewId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}


