package ui;

import dao.CartDAOShw1013;
import dao.CartDAO_Wjh0324;
import dao.CategoryHierarchDAO_Wjh0324;
import dao.ProductDAO_Wjh0324;
import dao.ReviewDAO_Wjh0324;
import dto.CartDTO;
import dto.ProductDTO;
import dto.ReviewDTO_Wjh0324;
import dto.UserDTO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;


class LabeledPanel extends JPanel {
	
	public LabeledPanel(String label, JLabel view) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel(label));
		this.add(view);
		view.setFont(getFont().deriveFont(18f));
	}
	
	public LabeledPanel(String label, JLabel view, String postfix) {
		this(label, view);
		this.add(new JLabel(postfix));
	}
	
}

class ImmutableTextArea extends JTextArea {

	public ImmutableTextArea(String text) {
		super(text);
				this.setEditable(false);
				this.setLineWrap(true);
				this.setWrapStyleWord(true);
				this.setFont(UIManager.getFont("Label.font"));
				this.setOpaque(false);
				this.getCaret().setVisible(false);
				this.setCursor(java.awt.Cursor.getDefaultCursor());
	}
	
	public ImmutableTextArea() {
		this("");
	}
	
}


public class ProductDetail_Wjh0324 extends JFrame {
	
	private UserDTO currentUser;
	private final ProductDTO currentProduct;
	private CartDTO foundInCart;
	
	private final JLabel nameView = new JLabel();
	private final JLabel priceView = new JLabel();
	private final JLabel categoryView = new JLabel();
	private final JLabel stockView = new JLabel();
	private final JLabel timestampView = new JLabel();
	private final JTextArea descriptionView = new ImmutableTextArea();

	private final JLabel foundInCartView = new JLabel();

	private JButton addCartButton = new JButton();
	
	private final JSpinner quantitySp;
	private final JPanel reviewGroup;
	
	
	private List<ReviewDTO_Wjh0324> reviewModel;
	
	public static final String toText(Object obj) {
		if (obj == null) return "";
		return obj.toString();
	}

	public ProductDetail_Wjh0324(int productID, UserDTO currentUser) {
		this(new ProductDAO_Wjh0324().getProductById(productID), currentUser);
	}
	
	public ProductDetail_Wjh0324(ProductDTO product, UserDTO currentUser) {
		
		this.currentUser = currentUser;
		this.currentProduct = product;
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JPanel detailArea = new JPanel();
		detailArea.setLayout(new BoxLayout(detailArea, BoxLayout.PAGE_AXIS));
		detailArea.add(new LabeledPanel("상품명", nameView));
		detailArea.add(new LabeledPanel("가격", priceView, "원"));
		detailArea.add(new LabeledPanel("카테고리", categoryView));
		detailArea.add(new LabeledPanel("등록일", timestampView));
		detailArea.add(new LabeledPanel("재고", stockView, "개"));
		detailArea.add(new JLabel("상품설명"));
		detailArea.add(descriptionView);
		
		this.setTitle("상품 상세정보");
		this.setSize(900, 600);
		
		reviewGroup = new JPanel();
		reviewGroup.setLayout(new BoxLayout(reviewGroup, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(detailArea);
		mainPanel.add(reviewGroup);
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel navLabel = new JLabel("수량");
		nav.add(navLabel);
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 9999, 1);
		quantitySp = new JSpinner(spinnerModel);
		
		nav.add(quantitySp);
		addCartButton.addActionListener(_l -> {
			addToCart();
		});
		nav.add(addCartButton);
		nav.add(foundInCartView);

		JPanel root = new JPanel(new BorderLayout());
		root.add(scrollPane, BorderLayout.CENTER);
		root.add(nav, BorderLayout.NORTH);
		
		this.add(root);

		this.loadProductInfo();
		this.loadCartInfo();
		this.loadReviewModel();

		this.setVisible(true);
		
	}

	public void setCartInfo(CartDTO cart) {
		this.foundInCart = cart;
		if (foundInCart == null) {
			quantitySp.setValue(1);
			addCartButton.setText("장바구니 담기");
			foundInCartView.setText("");
		} else {
			int quantity = foundInCart.getQuantity();
			quantitySp.setValue(quantity);
			addCartButton.setText("수량 변경");
			foundInCartView.setText("장바구니에 " + quantity + "개 담겨 있습니다.");
		}
	}

	public void loadCartInfo() {
		this.setCartInfo(new CartDAO_Wjh0324().getCartOf(currentUser.getUserId(), currentProduct.getProductId()));
	}
	
	public void setProductInfo(String name, Integer price, String categoryHirearch, Integer stock, Timestamp ts, String description) {
		nameView.setText(name);
		priceView.setText(toText(price));
		categoryView.setText(categoryHirearch);
		stockView.setText(toText(stock));
		timestampView.setText(ts.toString());
		descriptionView.setText(description);
		quantitySp.setModel(new SpinnerNumberModel(1, 1, stock.intValue(), 1));
		
		this.revalidate();
	}
	
	public void loadProductInfo() {
		if (currentProduct == null) return;
		CategoryHierarchDAO_Wjh0324 dao = new CategoryHierarchDAO_Wjh0324();
		this.setProductInfo(
			currentProduct.getName(),
			currentProduct.getPrice(),
			dao.getCategory(currentProduct.getCategoryId()).getFlattenCategory(),
			currentProduct.getStock(),
			currentProduct.getCreatedAt(),
			currentProduct.getDescription()
		);
	}
	
	public void loadReviewModel() {
		if (currentProduct == null) return;
		ReviewDAO_Wjh0324 dao = new ReviewDAO_Wjh0324();
		reviewModel = dao.getReviewsFor(currentProduct.getProductId());
		reviewGroup.removeAll();
		for (ReviewDTO_Wjh0324 review : reviewModel) {
			reviewGroup.add(new UserReviewCompPanel_Wjh0324(review.userName(), review.rating(), review.comment(), review.createdAt()));
		}
		this.revalidate();
	}

	public void addToCart() {
		boolean success;
		CartDAO_Wjh0324 dao = new CartDAO_Wjh0324();
		if (foundInCart == null) {
			success = dao.addCart(this.currentUser.getUserId(), this.currentProduct.getProductId(), (Integer) quantitySp.getValue());
		} else {
			CartDAOShw1013 dao2 = new CartDAOShw1013();
			dao2.updateCartItem(foundInCart.getCartId().intValue(), ((Integer) quantitySp.getValue()).intValue());
			success = true;
		}
		if (success) {
			JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.");
			loadCartInfo();
		} else {
			JOptionPane.showMessageDialog(this, "앗! 장바구니 추가에 뭔가 문제가 생겼습니다!");
		}
	}
}
