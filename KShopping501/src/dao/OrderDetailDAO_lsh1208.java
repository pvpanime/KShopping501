package dao;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.OrderDetailDTO_lsh1208;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO_lsh1208 {
    // 데이터베이스 연결 정보를 설정합니다.
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";  // 오라클 데이터베이스 URL
    private String userid = "scott";  // 사용자 아이디
    private String passwd = "tiger";  // 비밀번호

    // 생성자에서 데이터베이스 드라이버를 로드합니다.
    public OrderDetailDAO_lsh1208() {
        try {
            Class.forName(driver);  // 오라클 JDBC 드라이버 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // 드라이버 로드 실패 시 예외 출력
        }
    }

    // 특정 사용자의 주문 목록을 반환하는 메소드
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<OrderDTO> orders = new ArrayList<>();  // 주문 목록을 저장할 리스트
        String query = "SELECT order_id, user_num, order_date, status, total_amount "
                     + "FROM order_t WHERE user_num = ? ORDER BY order_date DESC";  // 주문을 날짜 순으로 내림차순 정렬하여 가져오는 SQL 쿼리

        try (Connection conn = DriverManager.getConnection(url, userid, passwd);  // 데이터베이스 연결
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);  // 쿼리의 ?에 userId를 설정
            ResultSet rs = ps.executeQuery();  // 쿼리 실행

            // 결과 셋을 순회하며 OrderDTO 객체에 데이터를 설정
            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_num"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getInt("total_amount"));
                orders.add(order);  // 주문 목록에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();  // SQL 예외 발생 시 출력
        }
        return orders;  // 주문 목록 반환
    }

    // 주문 ID와 주문 날짜를 기준으로 주문 상세 정보를 반환하는 메소드
    public List<OrderDetailDTO_lsh1208> getOrderDetails(Integer orderId, String orderDate) {
        List<OrderDetailDTO_lsh1208> orderDetails = new ArrayList<>();  // 주문 상세 목록을 저장할 리스트
        // 주문 상세 정보를 가져오는 SQL 쿼리
        String query = "SELECT p.product_id, p.name, od.quantity, od.price "
                     + "FROM o_detail_t od, product_t p, order_t o "
                     + "WHERE od.product_id = p.product_id "
                     + "AND o.order_id = od.order_id "
                     + "AND o.order_id = ? "
                     + "AND TRUNC(o.order_date) = TO_DATE(?, 'YYYY-MM-DD')";

        try (Connection conn = DriverManager.getConnection(url, userid, passwd);  // 데이터베이스 연결
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, orderId);  // 쿼리의 ?에 orderId 설정
            ps.setString(2, orderDate.substring(0, 10));  // 쿼리의 ?에 orderDate 설정 (날짜 형식에 맞추어 잘라냄)
            ResultSet rs = ps.executeQuery();  // 쿼리 실행

            // 결과 셋을 순회하며 OrderDetailDTO 객체에 데이터를 설정
            while (rs.next()) {
                OrderDetailDTO_lsh1208 detail = new OrderDetailDTO_lsh1208();
                detail.setProductId(rs.getInt("product_id"));
                detail.setName(rs.getString("name"));  // 제품 이름 설정
                detail.setQuantity(rs.getInt("quantity"));  // 수량 설정
                detail.setPrice(rs.getInt("price"));  // 가격 설정
                orderDetails.add(detail);  // 주문 상세 목록에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();  // SQL 예외 발생 시 출력
            System.out.println(orderDate);  // 오류 발생 시 주문 날짜 출력
        }
        return orderDetails;  // 주문 상세 목록 반환
    }
}