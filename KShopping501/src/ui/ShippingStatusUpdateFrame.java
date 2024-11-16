package ui;

import dto.ShippingDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ShippingStatusUpdateFrame extends JFrame {
    private JTable shippingTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusComboBox;
    private JButton updateButton;

    // 데이터베이스 연결 설정 (Oracle)
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "scott";
    private static final String DB_PASSWORD = "tiger";

    public ShippingStatusUpdateFrame() {
        setTitle("배송 상태 변경");
        setSize(800, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout 설정
        setLayout(new BorderLayout());

        // 테이블 모델 설정
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] { "배송 ID", "주문 ID", "추적 번호", "배송 업체", "상태", "예상 배송일" });
        shippingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(shippingTable);
        add(scrollPane, BorderLayout.CENTER);

        // 배송 상태 변경 콤보박스
        statusComboBox = new JComboBox<>(new String[] { "배송중", "배송완료", "배송취소" });
        add(statusComboBox, BorderLayout.NORTH);

        // 상태 변경 버튼
        updateButton = new JButton("상태 변경");
        add(updateButton, BorderLayout.SOUTH);

        // 버튼 클릭 이벤트 처리
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStatus();
            }
        });

        // 배송 데이터 로드
        loadShippingData();
    }

    // 데이터베이스 연결
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 배송 데이터 로드
    private void loadShippingData() {
        String query = "SELECT * FROM SHIPPING_T";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ShippingDTO shipping = new ShippingDTO(
                        rs.getInt("SHIPPING_ID"),
                        rs.getInt("ORDER_ID"),
                        rs.getString("TRACKING_NUMBER"),
                        rs.getString("CARRIER"),
                        rs.getString("STATUS"),
                        rs.getTimestamp("ESTIMATED_DELIVERY")
                );

                tableModel.addRow(new Object[] {
                        shipping.getShippingId(),
                        shipping.getOrderId(),
                        shipping.getTrackingNumber(),
                        shipping.getCarrier(),
                        shipping.getStatus(),
                        shipping.getEstimatedDelivery()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 배송 상태 업데이트
    private void updateStatus() {
        int selectedRow = shippingTable.getSelectedRow();
        if (selectedRow != -1) {
            int shippingId = (int) tableModel.getValueAt(selectedRow, 0);
            String newStatus = (String) statusComboBox.getSelectedItem();

            String query = "UPDATE SHIPPING_T SET STATUS = ? WHERE SHIPPING_ID = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, newStatus);
                stmt.setInt(2, shippingId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // 테이블 상태 갱신
                    tableModel.setValueAt(newStatus, selectedRow, 4);
                    JOptionPane.showMessageDialog(this, "상태가 성공적으로 변경되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(this, "상태 변경에 실패했습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "상태 변경 중 오류가 발생했습니다.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "변경할 배송을 선택하세요.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ShippingStatusUpdateFrame frame = new ShippingStatusUpdateFrame();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
