package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.ProductDAO_Hcb0402;
import dto.ProductDTO;

// 상품관리 -> table을 불러와서 textfield에 넣을것, 수정버튼 만들것
// textfield에 수정을 가하고 수정 버튼을 누르면 수정을 확인 후 update
// 왼쪽에 상품 ID, name 리스트, 오른쪽에 name~created_at 정보 호출

public class ProductManagement_Hcb0402 {
	// 불러온 dto를 넣어 표시할 컴포넌트
	JPanel rightPanel = new JPanel();
	JTextField name;
	JTextField description;
	JTextField price;
	JTextField stock;
	JTextField categoryId;
	JTextField createdAt;
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	List<ProductDTO> dtoList;
	JList<String> idJList = new JList<String>(listModel);
	JScrollPane scrollPane = new JScrollPane(idJList);

	public ProductManagement_Hcb0402() {
		// dao를 사용해서 productdto를 전부 불러옴, id와 name만 따로 리스트모델 만들어 놓음->leftpanel에 넣을것
		ProductDAO_Hcb0402 dao = new ProductDAO_Hcb0402();
		dtoList = dao.loadProductDB();
		for (ProductDTO productDTO : dtoList) {
			listModel.addElement(String.valueOf(productDTO.getProductId()) + "." + productDTO.getName());
		}

		// frame 생성, property
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setSize(800, 300);

		// leftpanel의 inner 생성, property
		idJList.setLayoutOrientation(JList.VERTICAL);
		idJList.addListSelectionListener(e -> loadProduct(idJList.getSelectedIndex()));
		JButton create = new JButton("create");
		JButton delete = new JButton("delete");
		JButton update = new JButton("update");
		update.addActionListener(e -> updateDB(idJList.getSelectedIndex()));

		// leftpanel 생성, property
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200, 300));

		// rightpanelinner 생성, property

		// leftpanel에 inner 추가
		leftPanel.add(scrollPane);
		leftPanel.add(create);
		leftPanel.add(update);
		leftPanel.add(delete);

		// rightpanel에 inner 추가
		rightPanel.setLayout(new GridLayout(6, 2, 5, 5));

		// frame에 panel추가
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(rightPanel, BorderLayout.CENTER);

		// 끝
		frame.setVisible(true);
	}

	public void loadProduct(int selectedIndex) {
		rightPanel.removeAll();
		ProductDTO productDTO = dtoList.get(selectedIndex);
		JLabel inner1 = new JLabel("name");
		JLabel inner2 = new JLabel("description");
		JLabel inner3 = new JLabel("price");
		JLabel inner4 = new JLabel("stock");
		JLabel inner5 = new JLabel("category");
		JLabel inner6 = new JLabel("date");
		name = new JTextField(productDTO.getName());
		description = new JTextField(productDTO.getDescription());
		price = new JTextField(String.valueOf(productDTO.getPrice()));
		stock = new JTextField(String.valueOf(productDTO.getStock()));
		categoryId = new JTextField(String.valueOf(productDTO.getCategoryId()));
		createdAt = new JTextField(String.valueOf(productDTO.getCreatedAt()));
		createdAt.setEditable(false);
		rightPanel.add(inner1);
		rightPanel.add(name);
		rightPanel.add(inner2);
		rightPanel.add(description);
		rightPanel.add(inner3);
		rightPanel.add(price);
		rightPanel.add(inner4);
		rightPanel.add(stock);
		rightPanel.add(inner5);
		rightPanel.add(categoryId);
		rightPanel.add(inner6);
		rightPanel.add(createdAt);
		rightPanel.add(createdAt);
		rightPanel.revalidate();
		rightPanel.repaint();
	}

	public void updateDB(int selectedIndex) {
		System.out.println(selectedIndex);
		long currentTimeMillis = System.currentTimeMillis();
		 Timestamp timestamp = new Timestamp(currentTimeMillis);
		ProductDTO productDTO = new ProductDTO(
				dtoList.get(selectedIndex).getProductId(), 
				name.getText(), description.getText(),
				Integer.parseInt(price.getText()), 
				Integer.parseInt(stock.getText()), 
				Integer.parseInt(categoryId.getText()), 
				timestamp);
		new ProductDAO_Hcb0402().updateDB(productDTO);
		loadProduct(selectedIndex);
	}

	public static void main(String[] args) {
		new ProductManagement_Hcb0402();
	}
}
