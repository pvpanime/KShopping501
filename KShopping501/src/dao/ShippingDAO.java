package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import scott.Scott;

public class ShippingDAO {

  private final static int[] delays = {1, 3, 7, 14, 30};


  public boolean addShipping(int orderId, String trackingNumber, String carrier, String status) {
    // 택배사 사정에 따라 다릅니다
    Random random = new Random();
    int delay = delays[random.nextInt(delays.length)];
    String query = 
    "INSERT INTO SHIPPING_T (ORDER_ID, TRACKING_NUMBER, CARRIER, ESTIMATED_DELIVERY) " +
    "VALUES (?, ?, ?, SYSDATE + ?)"
    ;
    try (Connection conn = Scott.getConnection()) {
      conn.setAutoCommit(true);
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, orderId);
      stmt.setString(2, trackingNumber);
      stmt.setString(3, carrier);
      stmt.setInt(4, delay);
      stmt.executeUpdate();
      stmt.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
