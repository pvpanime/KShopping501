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
    // UI 컴포넌트와 데이터 변수 선언
    private JComboBox<String> orderComboBox; // 주문 내역을 표시하는 콤보 박스
    private JPanel orderDetailPanel; // 주문 상세 내역을 표시하는 패널
    private JButton prevButton, nextButton; // 페이지 이동 버튼
    private int currentPage = 0; // 현재 페이지 번호
    private List<OrderDTO> orders; // 사용자 주문 리스트
    private OrderDetailDAO_lsh1208 orderDetailDAO; // 데이터베이스와 연결하는 DAO
    private List<OrderDetailDTO_lsh1208> currentOrderDetails; // 현재 표시 중인 주문 상세 정보
    private UserDTO user; // 사용자 정보
    private JLabel totalAmountLabel; // 총 금액을 표시하는 레이블

    // 생성자: 초기 UI 설정 및 데이터 로드
    public OrderDetail_lsh1208(UserDTO user) {
        this.user = user; // 사용자 정보 저장
        setTitle("Order Detail"); // 창 제목 설정
        setSize(500, 500); // 창 크기 설정
        setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        setLayout(new BorderLayout()); // BorderLayout을 사용하여 레이아웃 배치
        setResizable(false); // 창 크기 변경 불가능 설정

        // DAO 객체 초기화
        orderDetailDAO = new OrderDetailDAO_lsh1208();
        orderComboBox = new JComboBox<>(); // 주문 내역 콤보 박스 초기화
        orderDetailPanel = new JPanel(); // 주문 상세 내역 패널 초기화
        orderDetailPanel.setLayout(new GridLayout(5, 1)); // 최대 5개 항목 표시

        prevButton = new JButton("<"); // 이전 페이지 버튼
        nextButton = new JButton(">"); // 다음 페이지 버튼

        totalAmountLabel = new JLabel("총금액: 0원"); // 총 금액 레이블 초기화
        totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT); // 오른쪽 정렬

        // 상단 패널 (GridBagLayout 사용)
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 상단 패널에 여백 추가
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // "주문 내역: " 라벨 추가
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("주문 내역: "), gbc);

        // 콤보 박스 배치
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1; // 콤보 박스에 남은 공간 채우기
        topPanel.add(orderComboBox, gbc);

        // 총 금액 레이블 배치
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(totalAmountLabel, gbc);

        add(topPanel, BorderLayout.NORTH); // 상단 패널 추가

        // 하단 패널 (페이지 버튼 추가)
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        add(orderDetailPanel, BorderLayout.CENTER); // 상세 내역 패널 추가

        // 사용자 주문 내역 로드 및 콤보 박스 갱신
        orders = orderDetailDAO.getOrdersByUserId(user.getUserId());
        updateOrderComboBox();

        // 콤보 박스 선택 시 액션
        orderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseAndDisplaySelectedOrder(); // 선택된 주문 데이터 처리
            }
        });

        // 이전 버튼 클릭 시 액션
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) { // 첫 페이지가 아닐 경우
                    currentPage--;
                    displayOrderDetails();
                }
            }
        });

        // 다음 버튼 클릭 시 액션
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * 5 < currentOrderDetails.size()) { // 다음 페이지가 있을 경우
                    currentPage++;
                    displayOrderDetails();
                }
            }
        });

        setVisible(true); // 창 표시
    }

    // 콤보 박스에 사용자 주문 내역 업데이트
    private void updateOrderComboBox() {
        orderComboBox.removeAllItems(); // 기존 항목 제거

        // 각 주문을 콤보 박스에 추가
        for (OrderDTO order : orders) {
            String orderItem = String.format("주문번호: %d | 총금액: %d | %s",
                                              order.getOrderId(),
                                              order.getTotalAmount(),
                                              order.getOrderDate());
            orderComboBox.addItem(orderItem);
        }

        // 첫 번째 주문 선택
        if (orders.size() > 0) {
            orderComboBox.setSelectedIndex(0);
            parseAndDisplaySelectedOrder();
        }
    }

    // 선택된 주문의 상세 정보 로드 및 표시
    private void parseAndDisplaySelectedOrder() {
        String selectedOrder = (String) orderComboBox.getSelectedItem();

        if (selectedOrder != null) {
            // 정규식을 사용하여 주문 번호, 총 금액, 날짜 추출
            Pattern pattern = Pattern.compile("주문번호: (\\d+) \\| 총금액: ([\\d.]+) \\| (.+)");
            Matcher matcher = pattern.matcher(selectedOrder);

            if (matcher.matches()) {
                try {
                    Integer orderId = Integer.parseInt(matcher.group(1));
                    String orderDate = matcher.group(3).trim();

                    currentOrderDetails = orderDetailDAO.getOrderDetails(orderId, orderDate); // 주문 상세 데이터 로드
                    updateTotalAmount(orderId, orderDate); // 총 금액 업데이트
                    displayOrderDetails(); // 상세 정보 표시
                } catch (NumberFormatException ex) {
                    System.err.println("Error parsing order data in ComboBox: " + selectedOrder);
                }
            } else {
                System.err.println("Invalid format for selected order: " + selectedOrder);
            }
        }
    }

    // 선택된 주문의 총 금액 표시
    private void updateTotalAmount(Integer orderId, String orderDate) {
        OrderDTO selectedOrder = orders.stream()
                .filter(order -> order.getOrderId().equals(orderId) && order.getOrderDate().toString().startsWith(orderDate))
                .findFirst().orElse(null);
        if (selectedOrder != null) {
            totalAmountLabel.setText("총금액: " + selectedOrder.getTotalAmount() + "원");
        }
    }

    // 현재 페이지의 주문 상세 정보 표시
    private void displayOrderDetails() {
        orderDetailPanel.removeAll(); // 기존 패널 초기화
        int startIndex = currentPage * 5;
        int endIndex = Math.min(startIndex + 5, currentOrderDetails.size());

        for (int i = startIndex; i < endIndex; i++) {
            OrderDetailDTO_lsh1208 detail = currentOrderDetails.get(i);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 설정

            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(1, 3)); // 그리드 레이아웃 (3열)

            JLabel nameLabel = new JLabel("제품명: " + detail.getName());
            JLabel quantityLabel = new JLabel("개수: " + detail.getQuantity());
            JLabel priceLabel = new JLabel("금액: " + detail.getPrice());

            // 중앙 정렬
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gridPanel.add(nameLabel);
            gridPanel.add(quantityLabel);
            gridPanel.add(priceLabel);

            gridPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK)); // 좌측 구분선

            JButton reviewButton = new JButton("리뷰작성");

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.add(reviewButton, BorderLayout.CENTER);

            panel.add(gridPanel, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.EAST);

            orderDetailPanel.add(panel);

            // 리뷰 작성 버튼 클릭 시 동작
            reviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReviewWritePage_lsh1208(detail.getProductId(), detail.getName(), user);
                }
            });
        }

        orderDetailPanel.revalidate(); // 패널 갱신
        orderDetailPanel.repaint();
    }
}
