package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.CategoryDTO;
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
    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        String query = "SELECT CATEGORY_ID, NAME FROM CATEGORY_T";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryId(rs.getInt("CATEGORY_ID"));
                categoryDTO.setName(rs.getString("NAME"));
                categories.add(categoryDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }

    // 특정 카테고리에 속한 상품 목록 가져오기
    public List<ProductDTO> getProductsByCategory(int categoryId) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT P.PRODUCT_ID, P.NAME FROM PRODUCT_T P JOIN CATEGORY_T C ON P.CATEGORY_ID = C.CATEGORY_ID WHERE C.CATEGORY_ID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductId(rs.getInt("PRODUCT_ID"));
                    productDTO.setName(rs.getString("NAME"));
                    products.add(productDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }


    // 상품 이름으로 검색
    public List<ProductDTO> searchProducts(String searchKeyword) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT PRODUCT_ID, NAME FROM PRODUCT_T WHERE NAME LIKE ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchKeyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductId(rs.getInt("PRODUCT_ID"));
                    productDTO.setName(rs.getString("NAME"));
                    products.add(productDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    // 선택된 상품 정보 가져오기
    public ProductDTOKjh_0313 getProductInfo(int productId) {
    	ProductDTOKjh_0313 product = null;
        String query = "SELECT PRODUCT_ID, NAME, PRICE, DESCRIPTION FROM PRODUCT_T WHERE PRODUCT_ID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	product = new ProductDTOKjh_0313();
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
