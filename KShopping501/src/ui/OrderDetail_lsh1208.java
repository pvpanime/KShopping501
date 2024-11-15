package ui;

import dao.OrderDetailDAO_lsh1208;
import dto.OrderDTO;
import dto.OrderDetailDTO_lsh1208;
import dto.UserDTO; // Import the UserDTO class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDetail_lsh1208 extends JFrame {
    private JComboBox<String> orderComboBox;
    private JPanel orderDetailPanel;
    private JButton prevButton, nextButton;
    private int currentPage = 0;
    private List<OrderDTO> orders;
    private OrderDetailDAO_lsh1208 orderDetailDAO;
    private List<OrderDetailDTO_lsh1208> currentOrderDetails;
    private UserDTO user;
    private JLabel totalAmountLabel; // Add JLabel for total amount

    public OrderDetail_lsh1208(UserDTO user) {
        this.user = user;
        setTitle("Order Detail");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        orderDetailDAO = new OrderDetailDAO_lsh1208();
        orderComboBox = new JComboBox<>();
        orderDetailPanel = new JPanel();
        orderDetailPanel.setLayout(new GridLayout(5, 1));

        prevButton = new JButton("<");
        nextButton = new JButton(">");

        totalAmountLabel = new JLabel("총금액: 0원");
        totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Use GridBagLayout for the top panel
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add vertical margin by setting the topPanel's border
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));  // 10px margin at top and bottom

        // Place the "주문 내역: " label at the left
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("주문 내역: "), gbc);

        // Place the orderComboBox next to the "주문 내역: " label
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        topPanel.add(orderComboBox, gbc);

        // Place the totalAmountLabel at the right
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(totalAmountLabel, gbc);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        add(orderDetailPanel, BorderLayout.CENTER);

        orders = orderDetailDAO.getOrdersByUserId(user.getUserId());
        updateOrderComboBox();

        // Action listeners for orderComboBox, prevButton, and nextButton
        orderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseAndDisplaySelectedOrder();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    displayOrderDetails();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * 5 < currentOrderDetails.size()) {
                    currentPage++;
                    displayOrderDetails();
                }
            }
        });

        setVisible(true);
    }

    private void updateOrderComboBox() {
        orderComboBox.removeAllItems();  // Clear previous items

        for (OrderDTO order : orders) {
            String orderItem = String.format("주문번호: %d | 총금액: %.2f | %s",
                                              order.getOrderId(),
                                              order.getTotalAmount(),
                                              order.getOrderDate());
            orderComboBox.addItem(orderItem);
        }

        if (orders.size() > 0) {
            orderComboBox.setSelectedIndex(0);
            parseAndDisplaySelectedOrder();
        }
    }

    private void parseAndDisplaySelectedOrder() {
        String selectedOrder = (String) orderComboBox.getSelectedItem();

        if (selectedOrder != null) {
            // Use regex to match and capture order number, total amount, and date
            Pattern pattern = Pattern.compile("주문번호: (\\d+) \\| 총금액: ([\\d.]+) \\| (.+)");
            Matcher matcher = pattern.matcher(selectedOrder);

            if (matcher.matches()) {
                try {
                    Long orderId = Long.parseLong(matcher.group(1));
                    String orderDate = matcher.group(3).trim();

                    currentOrderDetails = orderDetailDAO.getOrderDetails(orderId, orderDate);
                    updateTotalAmount(orderId, orderDate);  // Update total amount for the selected order
                    displayOrderDetails();
                } catch (NumberFormatException ex) {
                    System.err.println("Error parsing order data in ComboBox: " + selectedOrder);
                }
            } else {
                System.err.println("Invalid format for selected order: " + selectedOrder);
            }
        }
    }

    private void updateTotalAmount(Long orderId, String orderDate) {
        // Retrieve the total amount for the selected order
        OrderDTO selectedOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId) && order.getOrderDate().equals(orderDate))
                .findFirst().orElse(null);
        if (selectedOrder != null) {
            totalAmountLabel.setText("총금액: " + selectedOrder.getTotalAmount() + "원");
        }
    }

    private void displayOrderDetails() {
        orderDetailPanel.removeAll();
        int startIndex = currentPage * 5;
        int endIndex = Math.min(startIndex + 5, currentOrderDetails.size());

        for (int i = startIndex; i < endIndex; i++) {
            OrderDetailDTO_lsh1208 detail = currentOrderDetails.get(i);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(1, 3));

            JLabel nameLabel = new JLabel("제품명: " + detail.getName());
            JLabel quantityLabel = new JLabel("개수: " + detail.getQuantity());
            JLabel priceLabel = new JLabel("금액: " + detail.getPrice());

            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gridPanel.add(nameLabel);
            gridPanel.add(quantityLabel);
            gridPanel.add(priceLabel);

            gridPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

            JButton reviewButton = new JButton("리뷰작성");

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.add(reviewButton, BorderLayout.CENTER);

            panel.add(gridPanel, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.EAST);

            orderDetailPanel.add(panel);

            reviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReviewWritePage_lsh1208(detail.getProductId(), detail.getName(), user);
                }
            });
        }

        orderDetailPanel.revalidate();
        orderDetailPanel.repaint();
    }

    public static void main(String[] args) {
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setUsername("김철수");
        user.setEmail("kim.cheolsu@example.com");
        user.setPassword("password123");
        user.setCreatedAt("24/11/14 13:27:41.048827000");
        user.setIsAdmin(false);

        new OrderDetail_lsh1208(user);
    }
}
