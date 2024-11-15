package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dto.ProductDTOKjh_0313;
import dao.DAOKjh_0313;
import dto.UserDTO; // UserDTO 임포트 추가

public class UiKjh_0313 extends JFrame {
	private DAOKjh_0313 dao;
	private JPanel topPanel, listPanel, infoPanel;
	private JLabel productNameLabel, productPriceLabel, productDescriptionLabel;
	private UserDTO loggedInUser; // 로그인된 사용자 정보를 저장할 변수

	public UiKjh_0313(UserDTO user) {
		this.loggedInUser = user; // 로그인된 사용자 정보 받아오기
		dao = new DAOKjh_0313();
		setTitle("상품 목록");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTopPanel();
		createListPanel();
		createInfoPanel();

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.WEST);
		add(infoPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	private void createTopPanel() {
		topPanel = new JPanel(new FlowLayout());

		JLabel titleLabel = new JLabel("상품 목록");
		topPanel.add(titleLabel);

		// 아이콘 예시 추가 (이곳에 추가적인 JFrame을 호출하는 이벤트를 설정할 수 있습니다)
		JButton dummyButton1 = new JButton("장바구니");
		dummyButton1.addActionListener(e -> {
			// 새로운 JFrame 실행 위치
		});

		JButton dummyButton2 = new JButton("회원정보");
		dummyButton2.addActionListener(e -> {
			// 회원정보 버튼 클릭 시 MyProfileFrame 열기
			new MyProfileFrame(loggedInUser).setVisible(true); // 현재 창 닫기
		});

		JButton dummyButton3 = new JButton("주문내역");
		dummyButton3.addActionListener(e -> {
			// 새로운 JFrame 실행 위치
		});

		topPanel.add(dummyButton1);
		topPanel.add(dummyButton2);
		topPanel.add(dummyButton3);
	}

	private boolean isAdminUser() {
		// 로그인된 사용자가 관리자일 때 true를 반환하는 함수
		return true; // 임시로 true 설정
	}

	private JPanel categoryPanel;

	private void createListPanel() {
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		// 상단 검색 필드와 버튼을 패널에 추가
		JPanel searchPanel = new JPanel();
		JTextField searchField = new JTextField(10);
		JButton searchButton = new JButton("검색");
		searchButton.addActionListener(e -> updateProductList(dao.searchProducts(searchField.getText())));
		searchPanel.add(searchField);
		searchPanel.add(searchButton);

		JButton viewAllButton = new JButton("모두 보기");
		viewAllButton.addActionListener(e -> updateCategoryList());
		searchPanel.add(viewAllButton);

		listPanel.add(searchPanel); // 검색 패널을 listPanel에 추가

		// 카테고리 목록을 표시할 별도의 패널 추가
		categoryPanel = new JPanel();
		categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
		listPanel.add(categoryPanel);
		// 카테고리 목록 업데이트
		updateCategoryList();
	}

	private void updateProductList(List<String> products) {
		// categoryPanel에서 카테고리 선택시 해당 카테고리에 속한 상품 목록을 표시
		categoryPanel.removeAll();
		for (String product : products) {
			JButton productButton = new JButton(product);
			productButton.addActionListener(e -> showProductInfo(product));
			categoryPanel.add(productButton);
		}

		categoryPanel.revalidate();
		categoryPanel.repaint();
	}

	private void updateCategoryList() {
		// categoryPanel에서 기존 카테고리 표시
		categoryPanel.removeAll();

		List<String> categories = dao.getCategories();
		for (String category : categories) {
			JButton categoryButton = new JButton(category);
			categoryButton.addActionListener(e -> updateProductList(dao.getProductsByCategory(category)));
			categoryPanel.add(categoryButton);
		}

		categoryPanel.revalidate();
		categoryPanel.repaint();
	}

	private void createInfoPanel() {
		infoPanel = new JPanel(new GridLayout(4, 1));
		productNameLabel = new JLabel();
		productPriceLabel = new JLabel();
		productDescriptionLabel = new JLabel();

		JButton addToCartButton = new JButton("장바구니에 추가");
		addToCartButton.addActionListener(e -> {
			ProductDTOKjh_0313 product = dao.getProductInfo(productNameLabel.getText());
			dao.addToCart(getUserId(), product.getProductId());
		});

		infoPanel.add(productNameLabel);
		infoPanel.add(productPriceLabel);
		infoPanel.add(productDescriptionLabel);
		infoPanel.add(addToCartButton);
	}

	private void showProductInfo(String productName) {
		ProductDTOKjh_0313 product = dao.getProductInfo(productName);
		if (product != null) {
			productNameLabel.setText("상품 이름: " + product.getName());
			productPriceLabel.setText("가격: " + product.getPrice());
			productDescriptionLabel.setText("설명: " + product.getDescription());
		}
	}

	private int getUserId() {
		// 현재 로그인된 사용자 ID를 반환 (임시로 1로 설정)
		return 1;
	}

	public static void main(String[] args) {
		// 예시로 로그인된 사용자 정보 (실제 로그인 후 전달되어야 함)
		UserDTO loggedInUser = new UserDTO(1, "홍길동", "hong@domain.com", "1234", null, false);
		new UiKjh_0313(loggedInUser); // 사용자 정보 전달하여 UiKjh_0313 실행
	}
}
