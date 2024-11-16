package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CartDTOShw1013;

public class CartDAOShw1013 {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    // 장바구니 항목 전체 조회
    public List<CartDTOShw1013> getAllCartItems() {
        List<CartDTOShw1013> cartItems = new ArrayList<>();
        String query = "SELECT c.cart_id, p.name AS product_name, p.price, c.quantity, p.stock " +
                       "FROM cart_t c " +
                       "JOIN product_t p ON c.product_id = p.product_id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CartDTOShw1013 item = new CartDTOShw1013(
                        rs.getInt("cart_id"),        // cart_id
                        rs.getString("product_name"), // product_name
                        rs.getInt("price"),          // price
                        rs.getInt("quantity"),       // quantity
                        rs.getInt("stock")           // stock
                );
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    /**
     * userNum에 해당하는 사용자가 장바구니에 담은 품목들을 모두 가져옵니다.
     * @param userNum
     * @return
     */
    public List<CartDTOShw1013> getCartItems(int userNum) {
        List<CartDTOShw1013> cartItems = new ArrayList<>();
        String query = "SELECT c.cart_id, p.name AS product_name, p.price, c.quantity, p.stock " +
                        "FROM cart_t c " +
                        "JOIN product_t p ON c.product_id = p.product_id "+
                        "WHERE c.user_num = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query);
                ) {
            pstmt.setInt(1, userNum);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CartDTOShw1013 item = new CartDTOShw1013(
                        rs.getInt("cart_id"),        // cart_id
                        rs.getString("product_name"), // product_name
                        rs.getInt("price"),          // price
                        rs.getInt("quantity"),       // quantity
                        rs.getInt("stock")           // stock
                );
                cartItems.add(item);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // 장바구니 항목 추가
    public void addCartItem(CartDTOShw1013 cartItem) {
        String query = "INSERT INTO cart_t (cart_id, quantity) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setInt(2, cartItem.getQuantity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 장바구니 항목 업데이트
    public void updateCartItem(int cartId, int quantity) {
        String query = "UPDATE cart_t SET quantity = ? WHERE cart_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 장바구니 항목 삭제
    public void deleteCartItem(int cartId) {
        String query = "DELETE FROM cart_t WHERE cart_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 결제 후 stock 업데이트
    public void updateStockAfterPayment(int userNum) {
        String selectCartQuery = "SELECT product_id, quantity FROM cart_t WHERE user_num = ?";
        String updateStockQuery = "UPDATE product_t SET stock = stock - ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 트랜잭션 시작
            conn.setAutoCommit(false);

            try (PreparedStatement selectStmt = conn.prepareStatement(selectCartQuery)) {
                selectStmt.setInt(1, userNum);
                ResultSet rs = selectStmt.executeQuery();

                try (PreparedStatement updateStmt = conn.prepareStatement(updateStockQuery)) {
                    while (rs.next()) {
                        int productId = rs.getInt("product_id");
                        int quantity = rs.getInt("quantity");

                        // stock 업데이트
                        updateStmt.setInt(1, quantity); // 감소할 수량
                        updateStmt.setInt(2, productId); // 제품 ID
                        updateStmt.executeUpdate();
                    }
                }
            }

            // 트랜잭션 커밋
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.rollback(); // 롤백 처리
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

    
    /**
     * userNum에 해당하는 사용자의 장바구니에 담긴 모든 물건을 결제합니다.
     * @param userNum
     */
    public Integer order(int userNum, int totalAmount) {
        Integer orderId;
        String idSelector = "SELECT order_seq.nextval as new_order_id FROM dual FOR UPDATE";
        String insertOrder = "INSERT INTO order_t (order_id, user_num, total_amount) VALUES (?, ?, ?)";
        String insertOrderDetail =
        "INSERT INTO o_detail_t (order_id, product_id, quantity, price) \r\n" +
        "SELECT ?, c.product_id, c.quantity, c.quantity * p.price \r\n" + 
        "FROM cart_t c JOIN user_t u ON c.user_num = u.user_num JOIN product_t p ON c.product_id = p.product_id \r\n" +
        "WHERE u.user_num = ?";
        
        String selectCartQuery = "SELECT product_id, quantity FROM cart_t WHERE user_num = ?";
        String updateStockQuery = "UPDATE product_t SET stock = stock - ? WHERE product_id = ?";
        
        String deleteCart = "DELETE FROM cart_t WHERE user_num = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try {
                // 트랜잭션 시작
                conn.setAutoCommit(false);

                // ORDER 테이블과 ORDER_DETAIL 테이블에 동시에 넣을 ORDER_ID 만들기
                try (PreparedStatement idStatement = conn.prepareStatement(idSelector)) {
                    ResultSet rs = idStatement.executeQuery();
                    rs.next();
                    orderId = rs.getInt("NEW_ORDER_ID");
                    rs.close();
                }

                // ORDER 생성
                try (PreparedStatement orderStatement = conn.prepareStatement(insertOrder)) {
                    orderStatement.setInt(1, orderId.intValue());
                    orderStatement.setInt(2, userNum);
                    orderStatement.setInt(3, totalAmount);
                    orderStatement.executeUpdate();
                }

                // ORDER_DETAIL 생성
                try (PreparedStatement odStatment = conn.prepareStatement(insertOrderDetail)) {
                    odStatment.setInt(1, orderId);
                    odStatment.setInt(2, userNum);
                    odStatment.executeUpdate();
                }

                // 장바구니에 담았던 수량만큼 각각의 제품 재고 반영하기
                try (PreparedStatement selectStmt = conn.prepareStatement(selectCartQuery)) {
                    selectStmt.setInt(1, userNum);
                    ResultSet rs = selectStmt.executeQuery();
    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateStockQuery)) {
                        while (rs.next()) {
                            int productId = rs.getInt("product_id");
                            int quantity = rs.getInt("quantity");
    
                            // stock 업데이트
                            updateStmt.setInt(1, quantity); // 감소할 수량
                            updateStmt.setInt(2, productId); // 제품 ID
                            updateStmt.executeUpdate();
                        }
                    }
                }

                // CART 삭제
                try (PreparedStatement delCartStatement = conn.prepareStatement(deleteCart)) {
                    delCartStatement.setInt(1, userNum);
                    delCartStatement.executeUpdate();
                }
                
                // 트랜잭션 커밋
                conn.commit();
                return orderId;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("트랜잭션이 롤백되었습니다.");
                conn.rollback();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.rollback(); // 롤백 처리
                System.err.println("트랜잭션이 롤백되었습니다.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return null;
        }
    }
}
