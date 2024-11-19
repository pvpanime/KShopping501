package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CategoryDTO;

public class CatDAO_Hcb0402 {
	CategoryDTO dto;
	List<CategoryDTO> dtoList = new ArrayList<CategoryDTO>();

	public List<CategoryDTO> loadCatDB() {
		String sql = "SELECT * from CATEGORY_T";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new CategoryDTO(rs.getInt("CATEGORY_ID"), rs.getString("NAME"), rs.getInt("PARENT_ID"));
				dtoList.add(dto);
			}
		} catch (Exception e) {

		}
		return dtoList;
	}

	public List<CategoryDTO> updateCatDB(CategoryDTO dto) {
		String sql = "UPDATE CATEGORY_T SET NAME = ? WHERE CATEGORY_ID = ?";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCategoryId());
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
				JOptionPane.showMessageDialog(null, "update 성공");
			} else {
				JOptionPane.showMessageDialog(null, "입력값을 확인해주세요", "update 실패", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoList;
	}

	public void insertCatDB(CategoryDTO dto) {
		String sql = "INSERT INTO CATEGORY_T (category_id, name) VALUES (CATEGORY_SEQ.NEXTVAL, ?)";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, dto.getName());
			int r = pstmt.executeUpdate();
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "insert 성공");
			} else {
				JOptionPane.showMessageDialog(null, "입력값을 확인해주세요", "insert 실패", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void deleteCatDB(CategoryDTO dto) {
		String sql = "DELETE FROM category_t WHERE  name = ? and category_id = ?";
		try (Connection con = scott.Scott.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getCategoryId());
			int r = pstmt.executeUpdate();
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "delete 성공");
			} else {
				JOptionPane.showMessageDialog(null, "입력값을 확인해주세요", "delete 실패", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "카테고리에 속한 상품들을 이동하거나 삭제해주세요", "delete 실패", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}