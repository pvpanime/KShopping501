package dao;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO_lsh1208 {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String userid = "scott";
	private String passwd = "tiger";

	public OrderDetailDAO_lsh1208() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Method to get the list of orders for a user
	public List<OrderDTO> getOrdersByUserId(Long userId) {
		List<OrderDTO> orders = new ArrayList<>();
		String query = "SELECT order_id, user_num, TO_CHAR(order_date, 'YYYY-MM-DD') AS order_date, status, total_amount "
				+ "FROM order_t WHERE user_num = ? ORDER BY order_date DESC";

		try (Connection conn = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderDTO order = new OrderDTO();
				order.setOrderId(rs.getLong("order_id"));
				order.setUserId(rs.getLong("user_num"));
				order.setOrderDate(rs.getString("order_date"));
				order.setStatus(rs.getString("status"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	// Method to get order details based on orderId and orderDate
	public List<OrderDetailDTO> getOrderDetails(Long orderId, String orderDate) {
		List<OrderDetailDTO> orderDetails = new ArrayList<>();
		String query = "SELECT p.product_id, p.name, od.quantity, od.price \r\n"
				+ "FROM o_detail_t od, product_t p, order_t o \r\n" + "WHERE od.product_id = p.product_id \r\n"
				+ "AND o.order_id = od.order_id \r\n" + "AND o.order_id = ? \r\n"
				+ "AND TRUNC(o.order_date) = TO_DATE(?, 'YYYY-MM-DD')\r\n" + "";

		try (Connection conn = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setLong(1, orderId);
			ps.setString(2, orderDate);
			ResultSet rs = ps.executeQuery();
			// getOrderDetails() 메소드에서
			while (rs.next()) {
			    OrderDetailDTO detail = new OrderDetailDTO();
			    detail.setProductId(rs.getLong("product_id"));
			    detail.setName(rs.getString("name"));  // setName 사용
			    detail.setQuantity(rs.getInt("quantity"));
			    detail.setPrice(rs.getDouble("price"));
			    orderDetails.add(detail);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDetails;
	}
}
