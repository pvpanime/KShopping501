package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.CategoryHierarchDTO_Wjh0324;
import scott.Scott;

public class CategoryHierarchDAO_Wjh0324 {
	
	public CategoryHierarchDTO_Wjh0324 getCategory(int from) {
		final String query = "SELECT category_id, name, parent_id FROM category_t WHERE category_id = ?";
		
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try(Connection conn = Scott.getConnection()) {
			ArrayList<String> hierarch = new ArrayList<String>();
			
			Integer next = from;
			
			while (next != null) {
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, next.intValue());
				
				resultSet = stmt.executeQuery();
				if(!resultSet.next()) break;
				
				next = resultSet.getObject("parent_id", Integer.class);
				String name = resultSet.getString("name");
				hierarch.add(0, name);
				
				resultSet.close();
				stmt.close();
			}
			
//			return hierarch.toArray(new String[hierarch.size()]);
			CategoryHierarchDTO_Wjh0324 p = null;
			for (int index = 0; index < hierarch.size(); index += 1) {
				CategoryHierarchDTO_Wjh0324 a = new CategoryHierarchDTO_Wjh0324(hierarch.get(index), p);
				p = a;
			}
			return p;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
//			return new String[] {};
			
		}
	}
	
}
