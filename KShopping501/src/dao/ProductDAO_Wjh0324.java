package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ProductDTO;
import scott.Scott;

public class ProductDAO_Wjh0324 {
  
  public ProductDTO getProductById(int id) {
    final String query = 
    "SELECT PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT\r\n" + //
      "FROM PRODUCT_T\r\n" + //
      "WHERE PRODUCT_ID = ?\r\n" + //
      "";
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try (Connection conn = Scott.getConnection()) {
      stmt = conn.prepareStatement(query);
      stmt.setInt(1, id);
      rs = stmt.executeQuery();
      if (!rs.next()) return null;
      ProductDTO dto = new ProductDTO(
        rs.getInt("PRODUCT_ID"), 
        rs.getString("NAME"), 
        rs.getString("DESCRIPTION"), 
        rs.getInt("PRICE"), 
        rs.getInt("STOCK"), 
        rs.getInt("CATEGORY_ID"),
        rs.getTimestamp("CREATED_AT")
      );
      return dto;
    } catch (SQLException sex) {
      sex.printStackTrace();
      return null;
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
