package ui;

import dto.ProductDTO_lsh1208;
import dto.ReviewDTO;
import dto.UserDTO;
import dao.ReviewWritePageDAO_lsh1208;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewWritePage_lsh1208 extends JFrame {
    private Long productId;
    private String productName;
    private UserDTO user;
    private JLabel[] stars;
    private int selectedRating;
    private JTextArea reviewTextArea;
    private JButton saveButton;

    public ReviewWritePage_lsh1208(Long productId, String productName, UserDTO user) {
        this.productId = productId;
        this.productName = productName;
        this.user = user;
        this.selectedRating = 0; // No rating selected initially

        setTitle("리뷰 작성");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a proper font to avoid issues with Korean characters
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Font font = new Font("맑은 고딕", Font.PLAIN, 14);  // Use a font supporting Korean characters
            setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the layout to GridBagLayout for better control over the proportions
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Product name label (1st item)
        JLabel productLabel = new JLabel("제품명: " + productName);
        productLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));  // Font supporting Korean characters
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 1;  // This will give this component a weight of 1 in the vertical direction
        add(productLabel, gbc);

        // Rating stars panel (2nd item)
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new GridLayout(1, 5));
        stars = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JLabel("★");
            stars[i].setFont(new Font("맑은 고딕", Font.PLAIN, 24));  // Ensure proper font for star display
            stars[i].setForeground(Color.GRAY);
            final int rating = i + 1;
            stars[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    setRating(rating);
                }
            });
            ratingPanel.add(stars[i]);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        add(ratingPanel, gbc);

        // Review text area (3rd item)
        reviewTextArea = new JTextArea(5, 30);
        reviewTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));  // Font supporting Korean characters
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 3;  // This will give this component a weight of 3 in the vertical direction
        add(scrollPane, gbc);

        // Review write button (4th item)
        saveButton = new JButton("리뷰 작성");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 1;  // This will give this component a weight of 1 in the vertical direction
        add(saveButton, gbc);

        // Action listener for save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReview();
            }
        });

        setVisible(true);
    }

    // Method to set rating and update star color
    private void setRating(int rating) {
        selectedRating = rating;
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars[i].setForeground(Color.YELLOW); // Yellow for selected stars
            } else {
                stars[i].setForeground(Color.GRAY); // Gray for unselected stars
            }
        }
    }

    private void saveReview() {
        // Validate that a rating and comment are provided
        if (selectedRating == 0) {
            JOptionPane.showMessageDialog(this, "별점을 선택하세요.");
            return;
        }
        String comment = reviewTextArea.getText();
        if (comment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "리뷰 내용을 입력하세요.");
            return;
        }

        // Save the review to the database
        ReviewDTO review = new ReviewDTO();
        review.setProductId(productId);
        review.setUserId(user.getUserId());
        review.setRating(selectedRating);
        review.setComment(comment);

        // Call DAO to save the review (assumed ReviewWritePageDAO_lsh1208)
        ReviewWritePageDAO_lsh1208 reviewDAO = new ReviewWritePageDAO_lsh1208();
        
        try {
            boolean success = reviewDAO.saveReview(review);
            if (success) {
                JOptionPane.showMessageDialog(this, "리뷰가 저장되었습니다.");
                dispose();  // Close the review window
            } else {
                JOptionPane.showMessageDialog(this, "리뷰 저장에 실패했습니다.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "리뷰 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Dummy data for testing
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setUsername("김철수");
        user.setEmail("kim.cheolsu@example.com");
        user.setPassword("password123");
        user.setCreatedAt("24/11/14 13:27:41.048827000");
        user.setIsAdmin(false);

        new ReviewWritePage_lsh1208(1001L, "Sample Product", user);
    }
}
