package ui;

import dto.ProductDTO_lsh1208;
import dto.ReviewDTO;
import dto.UserDTO;
import dao.ReviewWritePageDAO_lsh1208;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;

public class ReviewWritePage_lsh1208 extends JFrame {
    private Integer productId;  // 제품 ID
    private String productName;  // 제품명
    private UserDTO user;  // 사용자 정보
    private JLabel[] stars;  // 별점 표시를 위한 레이블 배열
    private int selectedRating;  // 선택된 별점
    private JTextArea reviewTextArea;  // 리뷰 내용 입력 필드
    private JButton saveButton;  // 리뷰 저장 버튼

    // 생성자: 제품 정보와 사용자 정보를 받아 리뷰 작성 창을 초기화
    public ReviewWritePage_lsh1208(Integer productId, String productName, UserDTO user) {
        this.productId = productId;
        this.productName = productName;
        this.user = user;
        this.selectedRating = 0; // 초기 별점은 0 (선택되지 않음)

        setTitle("리뷰 작성");
        setSize(400, 300);  // 창 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 창 닫기 시 종료
        setLocationRelativeTo(null);  // 화면 가운데 배치
        setResizable(false);  // 창 크기 조절 불가

        // 한국어 폰트를 설정하여 한글 문제를 방지
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Font font = new Font("맑은 고딕", Font.PLAIN, 14);  // 한글을 지원하는 폰트 설정
            setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 레이아웃을 GridBagLayout으로 설정하여 항목 배치를 제어
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 제품명 레이블 (첫 번째 항목)
        JLabel productLabel = new JLabel("제품명: " + productName);
        productLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));  // 제품명 폰트 설정
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 1;  // 이 컴포넌트에 수직 방향 가중치를 1로 설정
        add(productLabel, gbc);

        // 별점 패널 (두 번째 항목)
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new GridLayout(1, 5, 40, 0));  // 별을 5개로 나누어 표시
        stars = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JLabel("★");
            stars[i].setFont(new Font("맑은 고딕", Font.PLAIN, 24));  // 별 표시 폰트 설정
            stars[i].setForeground(Color.GRAY);  // 기본적으로 회색으로 설정
            final int rating = i + 1;
            stars[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    setRating(rating);  // 별점 클릭 시 별점 설정
                }
            });
            ratingPanel.add(stars[i]);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(ratingPanel, gbc);

        // 리뷰 내용 입력 텍스트 영역 (세 번째 항목)
        reviewTextArea = new JTextArea(5, 30);  // 텍스트 영역 크기 설정
        reviewTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));  // 텍스트 폰트 설정
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);  // 스크롤 가능하게 설정
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 3;  // 이 컴포넌트에 수직 방향 가중치를 3으로 설정
        add(scrollPane, gbc);

        // 리뷰 저장 버튼 (네 번째 항목)
        saveButton = new JButton("리뷰 작성");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 1;  // 이 컴포넌트에 수직 방향 가중치를 1로 설정
        add(saveButton, gbc);

        // 저장 버튼의 액션 리스너
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReview();  // 리뷰 저장 메소드 호출
            }
        });

        setVisible(true);  // 창 표시
    }

    // 별점 설정 및 별 색상 업데이트
    private void setRating(int rating) {
        selectedRating = rating;
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars[i].setForeground(Color.YELLOW);  // 선택된 별은 노란색으로 표시
            } else {
                stars[i].setForeground(Color.GRAY);  // 선택되지 않은 별은 회색으로 표시
            }
        }
    }

    // 리뷰 저장 메소드
    private void saveReview() {
        // 별점과 리뷰 내용이 입력되었는지 검증
        if (selectedRating == 0) {
            JOptionPane.showMessageDialog(this, "별점을 선택하세요.");
            return;
        }
        String comment = reviewTextArea.getText();
        if (comment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "리뷰 내용을 입력하세요.");
            return;
        }

        // ReviewDTO 객체에 리뷰 정보 설정
        ReviewDTO review = new ReviewDTO();
        review.setProductId(productId);
        review.setUserId(user.getUserId());
        review.setRating(selectedRating);
        review.setComment(comment);

        // DAO 객체를 통해 리뷰를 저장
        ReviewWritePageDAO_lsh1208 reviewDAO = new ReviewWritePageDAO_lsh1208();
        
        try {
            boolean success = reviewDAO.saveReview(review);  // 리뷰 저장 시도
            if (success) {
                JOptionPane.showMessageDialog(this, "리뷰가 저장되었습니다.");
                dispose();  // 리뷰 창을 닫음
            } else {
                JOptionPane.showMessageDialog(this, "리뷰 저장에 실패했습니다.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "리뷰 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}