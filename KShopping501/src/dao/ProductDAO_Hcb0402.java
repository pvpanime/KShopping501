package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ProductDTO;

public class ProductDAO_Hcb0402 {
	ProductDTO dto;
	List<ProductDTO> dtoList = new ArrayList<ProductDTO>();

	public List<ProductDTO> loadProductDB() {
		String sql = "SELECT * from PRODUCT_T";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new ProductDTO(
						rs.getInt("PRODUCT_ID"), 
						rs.getString("NAME"), 
						rs.getString("DESCRIPTION"), 
						rs.getInt("PRICE"), 
						rs.getInt("STOCK"), 
						rs.getInt("CATEGORY_ID"), 
						rs.getTimestamp("CREATED_AT")
						);
				dtoList.add(dto);
			}
		} catch (Exception e) {

		}
		return dtoList;
	}

	public void updateDB(ProductDTO dto) {
		String sql = "UPDATE PRODUCT_T SET NAME = ?, DESCRIPTION = ?, PRICE = ?, STOCK = ?, CATEGORY_ID = ?, CREATED_AT = ? WHERE PRODUCT_ID = ?";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setInt(3, dto.getPrice());
			pstmt.setInt(4, dto.getStock());
			pstmt.setInt(5, dto.getCategoryId());
			pstmt.setTimestamp(6, dto.getCreatedAt());
			pstmt.setInt(7, dto.getProductId());
			int rs = pstmt.executeUpdate();
			if (rs>0) {
				JOptionPane.showMessageDialog(null, "update 성공");
			} else {
				JOptionPane.showMessageDialog(null, "입력값을 확인해주세요", "update 실패", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {

		}
	}
}
