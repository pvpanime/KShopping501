package dto;

import java.sql.Timestamp;

public class UserDTO {

    private Integer userId;       // 사용자 ID
    private String username;      // 사용자 이름
    private String email;         // 이메일
    private String password;      // 비밀번호
    private Timestamp createdAt;  // 생성일
    private Boolean isAdmin;      // 관리자 여부

    // 기본 생성자
    public UserDTO() {
    }

    // 매개변수가 있는 생성자
    public UserDTO(Integer userId, String username, String email, String password, Timestamp createdAt, Boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }

    // Getter 및 Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", isAdmin=" + isAdmin +
                '}';
    }
}

