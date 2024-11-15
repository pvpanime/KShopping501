package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class UserReviewCompPanel_Wjh0324 extends JPanel {
	
    private final JLabel usernameLabel;
    private final JLabel ratingLabel;
    private final JLabel timestampLabel;
    private final JTextArea commentArea;

    public UserReviewCompPanel_Wjh0324(String username, int rating, String comment, Timestamp createdAt) {
        // Set the layout for the panel
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Create and configure the username label
        usernameLabel = new JLabel(username);

        // Create and configure the rating label
        ratingLabel = new JLabel("Rating: " + rating + "/5");
        
        String timestampString = createdAt.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        timestampLabel = new JLabel("At " + timestampString);

        // Create and configure the comment area
        commentArea = new JTextArea(comment);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setEditable(false);
        commentArea.setOpaque(false);
        commentArea.setBorder(BorderFactory.createEmptyBorder()); // Remove border for a clean look
        
        

        // Arrange components within the panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        topPanel.setOpaque(false); // Match panel background
        topPanel.add(usernameLabel);
        topPanel.add(ratingLabel);
        topPanel.add(timestampLabel);

        // Add components to main panel
        add(topPanel, BorderLayout.NORTH);
        add(commentArea, BorderLayout.CENTER);
    }

}