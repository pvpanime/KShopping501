package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.ProductDTO;
import dto.ProductDTOKjh_0313;
public class DAOKjh_0313 {
    private Connection conn;
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String userid = "scott";
    private String passwd = "tiger";

    public DAOKjh_0313() {
        try {
            // Load Oracle driver
            Class.forName(driver);
            // Establish the connection
            conn = DriverManager.getConnection(url, userid, passwd);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 카테고리 목록 가져오기
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT NAME FROM CATEGORY_T";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }

    // 특정 카테고리에 속한 상품 목록 가져오기
    public List<String> getProductsByCategory(String categoryName) {
        List<String> products = new ArrayList<>();
        String query = "SELECT PRODUCT_T.NAME FROM PRODUCT_T JOIN CATEGORY_T ON PRODUCT_T.CATEGORY_ID = CATEGORY_T.CATEGORY_ID WHERE CATEGORY_T.NAME = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(rs.getString("NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    
    // 특정 카테고리에 속한 상품 목록 가져오기
    public List<ProductDTO> getWholeProductsByCategoryID(String categoryName) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT PRODUCT_T.NAME FROM PRODUCT_T JOIN CATEGORY_T ON PRODUCT_T.CATEGORY_ID = CATEGORY_T.CATEGORY_ID WHERE CATEGORY_T.NAME = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDTO dto = new ProductDTO(null, categoryName, query, null, null, null, null);
                    // products.add(rs.getString("NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    // 상품 이름으로 검색
    public List<String> searchProducts(String searchKeyword) {
        List<String> products = new ArrayList<>();
        String query = "SELECT NAME FROM PRODUCT_T WHERE NAME LIKE ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchKeyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(rs.getString("NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    // 선택된 상품 정보 가져오기
    public ProductDTOKjh_0313 getProductInfo(String productName) {
    	ProductDTOKjh_0313 product = null;
        String query = "SELECT NAME, PRICE, DESCRIPTION FROM PRODUCT_T WHERE NAME = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	product = new ProductDTOKjh_0313();
                	product.setName(rs.getString("NAME"));
                	product.setPrice(rs.getInt("PRICE"));
                	product.setDescription(rs.getString("DESCRIPTION"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }

    public ProductDTO getProductInfoById(int productId) {
    	ProductDTO product = null;
        String query = "SELECT PRODUCT_ID, NAME, PRICE, DESCRIPTION FROM PRODUCT_T WHERE PRODUCT_ID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	product = new ProductDTO();
                	product.setProductId(rs.getInt("PRODUCT_ID"));
                	product.setName(rs.getString("NAME"));
                	product.setPrice(rs.getInt("PRICE"));
                	product.setDescription(rs.getString("DESCRIPTION"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }

    // 장바구니에 상품 추가
    public void addToCart(int userId, Integer productId) {
        String query = "INSERT INTO CART_T (USER_NUM, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, 1); // 기본 수량 1로 설정
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
