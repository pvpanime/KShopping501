package ui;

import dao.DAOKjh_0313;
import dto.CategoryDTO;
import dto.ProductDTO;
import dto.ProductDTOKjh_0313;
import dto.UserDTO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField; // UserDTO 임포트 추가

public class UiKjh_0313 extends JFrame {
	private DAOKjh_0313 dao;
	private JPanel topPanel, listPanel, infoPanel;
	private JPanel categoryPanel;
	private JLabel productNameLabel, productPriceLabel, productDescriptionLabel;
	private UserDTO loggedInUser; // 로그인된 사용자 정보를 저장할 변수
	private ProductDTO selectedProduct;

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
		JButton cartButton = new JButton("장바구니");
		cartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CartUIShw1013().setVisible(true);
			}
		});
		JButton productDetailButton = new JButton("제품 상세정보");
		productDetailButton.addActionListener(l -> {
			ProductDetail_Wjh0324 p = new ProductDetail_Wjh0324(selectedProduct.getProductId(), loggedInUser);
			p.setLocationRelativeTo(this);
		});

		JButton userProfileButton = new JButton("회원정보");
		userProfileButton.addActionListener(e -> {
			new MyProfileFrame(loggedInUser).setVisible(true);
		});

		JButton ordersButton = new JButton("주문내역");
		ordersButton.addActionListener(e -> {
			new OrderDetail_lsh1208(loggedInUser);
		});



		topPanel.add(cartButton);
		topPanel.add(productDetailButton);
		topPanel.add(ordersButton);
		topPanel.add(userProfileButton);

		if (loggedInUser.getIsAdmin()) {
			JButton mgButton = new JButton("판매상품 관리");
			mgButton.addActionListener(e -> {
				new ProductManagement_Hcb0402();
			});

			topPanel.add(mgButton);
		}
	}

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

	private void updateProductList(List<ProductDTO> products) {
		// categoryPanel에서 카테고리 선택시 해당 카테고리에 속한 상품 목록을 표시
		categoryPanel.removeAll();
		for (ProductDTO product : products) {
			JButton productButton = new JButton(product.getName());
			productButton.addActionListener(e -> {
				selectedProduct = product;
				showProductInfo(product.getProductId());
			});
			categoryPanel.add(productButton);
		}

		categoryPanel.revalidate();
		categoryPanel.repaint();
	}

	private void updateCategoryList() {
		// categoryPanel에서 기존 카테고리 표시
		categoryPanel.removeAll();

		List<CategoryDTO> categories = dao.getCategories();
		for (CategoryDTO category : categories) {
			JButton categoryButton = new JButton(category.getName());
			categoryButton.addActionListener(e -> updateProductList(dao.getProductsByCategory(category.getCategoryId())));
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
			// ProductDTOKjh_0313 product = dao.getProductInfo(productNameLabel.getText());
			ProductDTOKjh_0313 product = dao.getProductInfo(selectedProduct.getProductId());
			dao.addToCart(loggedInUser.getUserId(), product.getProductId());
			JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.", "장바구니", JOptionPane.INFORMATION_MESSAGE);
		});

		infoPanel.add(productNameLabel);
		infoPanel.add(productPriceLabel);
		infoPanel.add(productDescriptionLabel);
		infoPanel.add(addToCartButton);
	}

	private void showProductInfo(int productId) {
		ProductDTOKjh_0313 product = dao.getProductInfo(productId);
		if (product != null) {
			productNameLabel.setText("상품 이름: " + product.getName());
			productPriceLabel.setText("가격: " + product.getPrice());
			productDescriptionLabel.setText("설명: " + product.getDescription());
		}
	}

	// public static void main(String[] args) {
	// 	// 예시로 로그인된 사용자 정보 (실제 로그인 후 전달되어야 함)
	// 	UserDTO loggedInUser = new UserDTO(1, "홍길동", "hong@domain.com", "1234", null, true);
	// 	new UiKjh_0313(loggedInUser); // 사용자 정보 전달하여 UiKjh_0313 실행
	// }

}
