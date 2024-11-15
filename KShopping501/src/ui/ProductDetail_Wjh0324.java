package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import dao.CategoryHierarchDAO_Wjh0324;
import dto.ReviewDTO;


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
	
	private final JLabel nameView = new JLabel();
	private final JLabel priceView = new JLabel();
	private final JLabel categoryView = new JLabel();
	private final JLabel stockView = new JLabel();
	private final JLabel timestampView = new JLabel();
	private final JTextArea descriptionView = new ImmutableTextArea();
	
	private final JPanel reviewGroup;
	
	private ReviewDTO[] reviewModel;
	
	public static final String toText(Object obj) {
		if (obj == null) return "";
		return obj.toString();
	}
	
	public ProductDetail_Wjh0324(Component parent) {
		
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
		this.setLocationRelativeTo(parent);
		
		reviewGroup = new JPanel();
		reviewGroup.setLayout(new BoxLayout(reviewGroup, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(detailArea);
		mainPanel.add(reviewGroup);
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nav.add(new JButton("장바구니 담기"));
		

		JPanel root = new JPanel(new BorderLayout());
		root.add(scrollPane, BorderLayout.CENTER);
		root.add(nav, BorderLayout.NORTH);
		
		this.add(root);
		this.setVisible(true);
		
	}
	
	public void setProductInfo(String name, Integer price, String categoryHirearch, Integer stock, Timestamp ts, String description) {
		nameView.setText(name);
		priceView.setText(toText(price));
		categoryView.setText(categoryHirearch);
		stockView.setText(toText(stock));
		timestampView.setText(ts.toString());
		descriptionView.setText(description);
		this.revalidate();
	}
	
	public void setReviewModel(ReviewDTO[] reviews) {
		reviewModel = reviews;
		reviewGroup.removeAll();
		for (ReviewDTO review : reviews) {
			reviewGroup.add(new UserReviewCompPanel_Wjh0324(review.getUserId().toString(), review.getRating(), review.getComment()));
		}
		this.revalidate();
	}
	
	public static void main(String[] args) {
		var dao = new CategoryHierarchDAO_Wjh0324();
		ProductDetail_Wjh0324 ui = new ProductDetail_Wjh0324(null);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setProductInfo(
				"iPad Pro",
				2_790_000,
				dao.getCategory(1).getFlattenCategory(),
				99,
				Timestamp.from(Instant.now()), "아이패드 아십니까? 정말 비쌉니다!");
		
		ui.setReviewModel(new ReviewDTO[] {
			new ReviewDTO() {{ setUserId(1); setComment("와 개극혐"); setRating(1); }},
			new ReviewDTO() {{ setUserId(2); setComment("염병 이거 왜삼?"); setRating(1); }},
			new ReviewDTO() {{ setUserId(3); setComment("ㅋㅋㅋㅋㅋ 팀쿸 감 다 죽었네"); setRating(1); }},
			new ReviewDTO() {{ setUserId(4); setComment("이재용 노태문 화이팅"); setRating(1); }},
		});
	}
}
