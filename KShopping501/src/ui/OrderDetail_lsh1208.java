package ui;

import dao.OrderDetailDAO_lsh1208;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.OrderDetailDTO_lsh1208;
import dto.UserDTO;  // Import the UserDTO class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderDetail_lsh1208 extends JFrame {
    private JComboBox<String> orderComboBox;
    private JPanel orderDetailPanel;
    private JButton prevButton, nextButton;
    private int currentPage = 0;
    private List<OrderDTO> orders;
    private OrderDetailDAO_lsh1208 orderDetailDAO;
    private List<OrderDetailDTO_lsh1208> currentOrderDetails;
    private UserDTO user;  // Add UserDTO variable to store user information

    // Constructor now takes a UserDTO object
    public OrderDetail_lsh1208(UserDTO user) {
        this.user = user;  // Store the passed user data
        setTitle("Order Detail");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        orderDetailDAO = new OrderDetailDAO_lsh1208();
        orderComboBox = new JComboBox<>();
        orderDetailPanel = new JPanel();
        orderDetailPanel.setLayout(new GridLayout(5, 1));  // Showing 5 items per page

        prevButton = new JButton("<");
        nextButton = new JButton(">");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Order: "));
        topPanel.add(orderComboBox);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        add(orderDetailPanel, BorderLayout.CENTER);

        // Load orders for the user using the userId from the passed UserDTO object
        orders = orderDetailDAO.getOrdersByUserId(user.getUserId());  // Use the userId from the UserDTO
        updateOrderComboBox();

        // Action listeners for the combo box
        orderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOrder = (String) orderComboBox.getSelectedItem();
                Long orderId = Long.parseLong(selectedOrder.split(" ")[0]);
                String orderDate = selectedOrder.split(" ")[1];
                currentOrderDetails = orderDetailDAO.getOrderDetails(orderId, orderDate);
                displayOrderDetails();
            }
        });

        // Pagination buttons
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
        for (OrderDTO order : orders) {
            String orderItem = order.getOrderId() + " " + order.getOrderDate();
            orderComboBox.addItem(orderItem);
        }
        if (orders.size() > 0) {
            orderComboBox.setSelectedIndex(0);
            String selectedOrder = (String) orderComboBox.getSelectedItem();
            Long orderId = Long.parseLong(selectedOrder.split(" ")[0]);
            String orderDate = selectedOrder.split(" ")[1];
            currentOrderDetails = orderDetailDAO.getOrderDetails(orderId, orderDate);
            displayOrderDetails();
        }
    }

    private void displayOrderDetails() {
        orderDetailPanel.removeAll();
        int startIndex = currentPage * 5;
        int endIndex = Math.min(startIndex + 5, currentOrderDetails.size());

        for (int i = startIndex; i < endIndex; i++) {
            OrderDetailDTO_lsh1208 detail = currentOrderDetails.get(i);
            
            // Create a new JPanel with BorderLayout
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(10, 10));  // Use BorderLayout to separate components
            
            // Add a border to the panel
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Adds a black line border
            
            // Create a panel to hold the labels with GridLayout
            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(1, 3));  // 1 row, 3 columns (product name, quantity, price)
            
            // Create labels for product name, quantity, and price
            JLabel nameLabel = new JLabel("제품명: " + detail.getName());
            JLabel quantityLabel = new JLabel("개수: " + detail.getQuantity());
            JLabel priceLabel = new JLabel("금액: " + detail.getPrice());

            // Set the horizontal alignment of labels
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add labels to the gridPanel
            gridPanel.add(nameLabel);
            gridPanel.add(quantityLabel);
            gridPanel.add(priceLabel);

            // Add a divider line between each column (except for the last one)
            gridPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK)); // Only add vertical borders between first two columns

            // Create the review button and place it on the right (EAST)
            JButton reviewButton = new JButton("리뷰작성");

            // Create a button panel and add the review button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());  // Using BorderLayout to manage the button
            
            // Add the review button to the buttonPanel
            buttonPanel.add(reviewButton, BorderLayout.CENTER);  // Center aligns the button in available space

            // Add the gridPanel to the center of the panel
            panel.add(gridPanel, BorderLayout.CENTER);  // Grid panel is placed in the center
            panel.add(buttonPanel, BorderLayout.EAST);  // Button panel is placed on the right (EAST)

            // Add the panel to the orderDetailPanel
            orderDetailPanel.add(panel);
            
            // Set action for the review button
            reviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Pass the product and user information to the ReviewWritePage
                    new ReviewWritePage_lsh1208(detail.getProductId(), detail.getName(), user);
                }
            });
        }

        orderDetailPanel.revalidate();
        orderDetailPanel.repaint();
    }








    public static void main(String[] args) {
        // Pass the UserDTO object when creating the OrderDetail screen
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setUsername("김철수");
        user.setEmail("kim.cheolsu@example.com");
        user.setPassword("password123");
        user.setCreatedAt("24/11/14 13:27:41.048827000");
        user.setIsAdmin(false);
        
        new OrderDetail_lsh1208(user);  // Pass user info to the constructor
    }
}
