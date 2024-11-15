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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
            
            // Create a JPanel to hold the labels (aligned to the left by default)
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));  // Vertical layout for labels
            
            JLabel nameLabel = new JLabel("제품명: " + detail.getName());
            JLabel quantityLabel = new JLabel("개수: " + detail.getQuantity());
            JLabel priceLabel = new JLabel("금액: " + detail.getPrice());
            
            labelPanel.add(nameLabel);
            labelPanel.add(quantityLabel);
            labelPanel.add(priceLabel);

            // Create the review button and place it on the right (EAST)
            JButton reviewButton = new JButton("리뷰작성");

            // Add components to the main panel
            panel.add(labelPanel, BorderLayout.WEST);  // Labels go to the left side
            panel.add(reviewButton, BorderLayout.EAST);  // Review button goes to the right side
            
            // Add the panel to the orderDetailPanel
            orderDetailPanel.add(panel);
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
