package dao;

import dto.CartDTOShw1013;

import java.sql.*;
import java.util.ArrayList;  
import java.util.List;

public class CartDAOShw1013 {

    // 데이터베이스 연결 정보
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    // SQL 쿼리 상수
    private static final String QUERY_SELECT_CART_ITEMS =
            "SELECT c.cart_id, p.name AS product_name, p.price, c.quantity, p.stock " +
            "FROM cart_t c " +
            "JOIN product_t p ON c.product_id = p.product_id";

    private static final String QUERY_INSERT_CART_ITEM =
            "INSERT INTO cart_t (cart_id, quantity) VALUES (?, ?)";

    private static final String QUERY_UPDATE_CART_ITEM =
            "UPDATE cart_t SET quantity = ? WHERE cart_id = ?";

    private static final String QUERY_DELETE_CART_ITEM =
            "DELETE FROM cart_t WHERE cart_id = ?";

    private static final String QUERY_SELECT_ORDER_ID =
            "SELECT ORDER_SEQ.NEXTVAL AS NEW_ORDER_ID FROM DUAL";

    private static final String QUERY_INSERT_ORDER =
            "INSERT INTO ORDER_T (ORDER_ID, USER_NUM, TOTAL_AMOUNT) VALUES (?, ?, ?)";

    private static final String QUERY_INSERT_ORDER_DETAIL =
            "INSERT INTO O_DETAIL_T (ORDER_ID, PRODUCT_ID, QUANTITY, PRICE) " +
            "SELECT ?, C.PRODUCT_ID, C.QUANTITY, C.QUANTITY * P.PRICE " +
            "FROM CART_T C JOIN PRODUCT_T P ON C.PRODUCT_ID = P.PRODUCT_ID WHERE C.USER_NUM = ?";

    private static final String QUERY_SELECT_CART_FOR_USER =
            "SELECT PRODUCT_ID, QUANTITY FROM CART_T WHERE USER_NUM = ?";

    private static final String QUERY_UPDATE_PRODUCT_STOCK =
            "UPDATE PRODUCT_T SET STOCK = STOCK - ? WHERE PRODUCT_ID = ?";

    private static final String QUERY_DELETE_CART_FOR_USER =
            "DELETE FROM CART_T WHERE USER_NUM = ?";

    /**
     * 장바구니의 모든 항목을 조회합니다.
     *
     * @return 장바구니 항목 리스트
     */
    public List<CartDTOShw1013> getAllCartItems() {
        List<CartDTOShw1013> cartItems = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(QUERY_SELECT_CART_ITEMS);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 셋에서 데이터를 읽어 CartDTO 객체로 변환 후 리스트에 추가
            while (rs.next()) {
                CartDTOShw1013 item = new CartDTOShw1013(
                        rs.getInt("cart_id"),        // 장바구니 항목 ID
                        rs.getString("product_name"), // 상품명
                        rs.getInt("price"),          // 가격
                        rs.getInt("quantity"),       // 수량
                        rs.getInt("stock")           // 재고
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

   
    /**
     * 장바구니에 새 항목을 추가합니다.
     *
     * @param cartItem 추가할 장바구니 항목
     */
    public void addCartItem(CartDTOShw1013 cartItem) {
        executeUpdate(QUERY_INSERT_CART_ITEM, pstmt -> {
            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setInt(2, cartItem.getQuantity());
        });
    }

    /**
     * 장바구니 항목의 수량을 업데이트합니다.
     *
     * @param cartId   업데이트할 장바구니 항목 ID
     * @param quantity 변경할 수량
     */
    public void updateCartItem(int cartId, int quantity) {
        executeUpdate(QUERY_UPDATE_CART_ITEM, pstmt -> {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
        });
    }

    /**
     * 장바구니에서 특정 항목을 삭제합니다.
     *
     * @param cartId 삭제할 장바구니 항목 ID
     */
    public void deleteCartItem(int cartId) {
        executeUpdate(QUERY_DELETE_CART_ITEM, pstmt -> pstmt.setInt(1, cartId));
    }

    /**
     * 사용자 주문을 처리하고 장바구니 항목을 삭제합니다.
     *
     * @param userNum     사용자 번호
     * @param totalAmount 총 결제 금액
     * @return 주문 성공 여부
     */
    public boolean order(int userNum, int totalAmount) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            Integer orderId = generateOrderId(conn); // 새로운 주문 ID 생성

            insertOrder(conn, orderId, userNum, totalAmount); // ORDER 테이블에 데이터 삽입
            insertOrderDetails(conn, orderId, userNum); // ORDER_DETAIL 테이블에 데이터 삽입
            updateProductStock(conn, userNum); // 상품 재고 업데이트
            deleteCartForUser(conn, userNum); // 사용자 장바구니 비우기

            conn.commit(); // 트랜잭션 커밋
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 새로운 주문 ID를 생성합니다.
     *
     * @param conn 데이터베이스 연결 객체
     * @return 새로 생성된 주문 ID
     * @throws SQLException SQL 예외
     */
    private Integer generateOrderId(Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(QUERY_SELECT_ORDER_ID);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("NEW_ORDER_ID");
            }
            throw new SQLException("Order ID 생성 실패");
        }
    }

    /**
     * 주문 테이블에 데이터를 삽입합니다.
     *
     * @param conn        데이터베이스 연결 객체
     * @param orderId     주문 ID
     * @param userNum     사용자 번호
     * @param totalAmount 총 결제 금액
     * @throws SQLException SQL 예외
     */
    private void insertOrder(Connection conn, int orderId, int userNum, int totalAmount) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(QUERY_INSERT_ORDER)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, userNum);
            pstmt.setInt(3, totalAmount);
            pstmt.executeUpdate();
        }
    }


    /**
     * 주문 상세 테이블에 데이터를 삽입합니다.
     *
     * @param conn    데이터베이스 연결 객체
     * @param orderId 주문 ID
     * @param userNum 사용자 번호
     * @throws SQLException SQL 예외
     */
    private void insertOrderDetails(Connection conn, int orderId, int userNum) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(QUERY_INSERT_ORDER_DETAIL)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, userNum);
            pstmt.executeUpdate();
        }
    }

    /**
     * 상품 재고를 업데이트합니다.
     *
     * @param conn    데이터베이스 연결 객체
     * @param userNum 사용자 번호
     * @throws SQLException SQL 예외
     */
    private void updateProductStock(Connection conn, int userNum) throws SQLException {
        try (PreparedStatement selectStmt = conn.prepareStatement(QUERY_SELECT_CART_FOR_USER);
             PreparedStatement updateStmt = conn.prepareStatement(QUERY_UPDATE_PRODUCT_STOCK)) {

            selectStmt.setInt(1, userNum);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("PRODUCT_ID");
                    int quantity = rs.getInt("QUANTITY");

                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, productId);
                    updateStmt.executeUpdate();
                }
            }
        }
    }

    /**
     * 특정 사용자의 장바구니를 비웁니다.
     *
     * @param conn    데이터베이스 연결 객체
     * @param userNum 사용자 번호
     * @throws SQLException SQL 예외
     */
    private void deleteCartForUser(Connection conn, int userNum) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(QUERY_DELETE_CART_FOR_USER)) {
            pstmt.setInt(1, userNum);
            pstmt.executeUpdate();
        }
    }

    /**
     * 공통 쿼리 실행 메서드.
     *
     * @param query    실행할 쿼리
     * @param consumer 쿼리 파라미터 설정 람다식
     */
    private void executeUpdate(String query, SQLConsumer<PreparedStatement> consumer) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            consumer.accept(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQLConsumer 함수형 인터페이스.
     *
     * @param <T> 대상 타입
     */
    @FunctionalInterface
    interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
