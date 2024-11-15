package ui;

import dto.ShippingDTO;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ShippingUI_cyu0923 extends JFrame {

    private JTextField txtTrackingNumber;
    private JTextArea txtAreaShippingDetails;
    private JButton btnSearch;
    private JLabel lblTrackingNumber, lblShippingDetails;


    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public ShippingUI_cyu0923() {
        setTitle("배송 상세 조회");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        lblTrackingNumber = new JLabel("배송 번호:");
        txtTrackingNumber = new JTextField(20);
        lblShippingDetails = new JLabel("배송 정보:");
        txtAreaShippingDetails = new JTextArea(10, 40);
        txtAreaShippingDetails.setEditable(false);
        btnSearch = new JButton("조회");


        add(lblTrackingNumber);
        add(txtTrackingNumber);
        add(btnSearch);
        add(lblShippingDetails);
        add(new JScrollPane(txtAreaShippingDetails));


        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String trackingNumber = txtTrackingNumber.getText().trim();
                if (!trackingNumber.isEmpty()) {
                    searchShippingDetails(trackingNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "배송 번호를 입력하세요.");
                }
            }
        });
    }


    private void searchShippingDetails(String trackingNumber) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            String sql = "SELECT shipping_id, order_id, carrier, status, estimated_delivery "
                    + "FROM shipping_t WHERE tracking_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, trackingNumber);
            rs = stmt.executeQuery();


            if (rs.next()) {
                ShippingDTO shipping = new ShippingDTO();
                shipping.setShippingId(rs.getInt("shipping_id"));
                shipping.setOrderId(rs.getInt("order_id"));
                shipping.setTrackingNumber(trackingNumber);
                shipping.setCarrier(rs.getString("carrier"));
                shipping.setStatus(rs.getString("status"));
                shipping.setEstimatedDelivery(rs.getTimestamp("estimated_delivery"));


                txtAreaShippingDetails.setText("배송 ID: " + shipping.getShippingId() + "\n"
                        + "주문 ID: " + shipping.getOrderId() + "\n"
                        + "배송 업체: " + shipping.getCarrier() + "\n"
                        + "배송 상태: " + shipping.getStatus() + "\n"
                        + "예상 배송일: " + shipping.getEstimatedDelivery());
            } else {
                txtAreaShippingDetails.setText("해당 배송 번호에 대한 정보가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "배송 번호를 정확히 입력해주세요.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShippingUI_cyu0923().setVisible(true);
            }
        });
    }
}
