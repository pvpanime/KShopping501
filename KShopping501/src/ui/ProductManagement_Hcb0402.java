package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.CatDAO_Hcb0402;
import dao.ProductDAO_Hcb0402;
import dto.CategoryDTO;
import dto.ProductDTO;

// 상품관리 -> table을 불러와서 textfield에 넣을것, 수정버튼 만들것
// textfield에 수정을 가하고 수정 버튼을 누르면 수정을 확인 후 update
// 왼쪽에 상품 ID, name 리스트, 오른쪽에 name~created_at 정보 호출

public class ProductManagement_Hcb0402 {
	// 불러온 dto의 세부정보를 표시할 컴포넌트
	JPanel rightPanel = new JPanel();
	JTextField name;
	JTextField description;
	JTextField price;
	JTextField stock;
	JComboBox<Integer> categoryId;
	List<Integer> categoryIdArray = new ArrayList<Integer>();
	JTextField categoryName;
	JTextField createdAt;
	// 불러온 productdto의 list를 표시할 컴포넌트
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> idJList = new JList<String>(listModel);
	JScrollPane scrollPane = new JScrollPane(idJList);
	List<ProductDTO> dtoList;
	// 불러온 catdto의 list를 표시할 컴포넌트
	DefaultListModel<String> catListModel = new DefaultListModel<String>();
	JList<String> catJList = new JList<String>(catListModel);
	JScrollPane catScrollPane = new JScrollPane(catJList);
	List<CategoryDTO> catDTOList;
	// product list panel
	JPanel leftPanel = new JPanel();
	// list의 selectedindex를 저장할 변수
	int catIndex;
	int productIndex;
	JFrame frame = new JFrame();

// mainUI
	public ProductManagement_Hcb0402() {
		// dao를 사용해서 dto를 불러와 id,name만 따로 listmodel에 넣음
		CatDAO_Hcb0402 dao2 = new CatDAO_Hcb0402();
		catDTOList = dao2.loadCatDB();
		for (CategoryDTO catDTO : catDTOList) {
			catListModel.addElement(String.valueOf(catDTO.getCategoryId()) + "." + catDTO.getName());
			categoryIdArray.add(catDTO.getCategoryId());
		}

		// frame property
		frame.setLayout(new FlowLayout());
		frame.setSize(1000, 400);
		// 임시
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// leftpanel의 inner 생성, property
		idJList.setLayoutOrientation(JList.VERTICAL);
		idJList.addListSelectionListener(e -> {
			productIndex = idJList.getSelectedIndex();
			if (productIndex != -1) {
				loadProduct(idJList.getSelectedIndex());
			}
		});
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(200, 100));
		JButton insert = new JButton("insert");
		JButton delete = new JButton("delete");
		JButton update = new JButton("update");
		update.addActionListener(e -> updateProduct(idJList.getSelectedIndex()));
		delete.addActionListener(e -> deleteProduct(idJList.getSelectedIndex()));
		insert.addActionListener(e -> insertProduct());
		scrollPane.setPreferredSize(new Dimension(150, 220));
		// leftpanel의 inner 생성, property

		// categorypanel의 inner 생성, property
		catJList.setLayoutOrientation(JList.VERTICAL);
		catJList.addListSelectionListener(e -> {
			catIndex = catJList.getSelectedIndex();
			if (catIndex != -1) {
				loadCategory(catIndex);
			}

		});
		JPanel catButtons = new JPanel();
		catButtons.setPreferredSize(new Dimension(200, 100));
		JButton catInsert = new JButton("insert");
		JButton catDelete = new JButton("delete");
		JButton catUpdate = new JButton("update");
		catUpdate.addActionListener(e -> updateCat());
		catInsert.addActionListener(e -> insertCat());
		catDelete.addActionListener(e -> deleteCat());
		catScrollPane.setPreferredSize(new Dimension(150, 220));
		// categorypanel의 inner 생성, property

		// categorypanel 생성, property
		JPanel categoryPanel = new JPanel();
		categoryPanel.setPreferredSize(new Dimension(200, 300));

		// leftpanel 생성, property
		leftPanel.setPreferredSize(new Dimension(200, 300));

		// rightpanelinner 생성, property
		rightPanel.setLayout(new GridLayout(6, 2, 5, 5));
		rightPanel.setPreferredSize(new Dimension(500, 250));

		// catpanel에 inner추가
		categoryPanel.add(catScrollPane);
		categoryPanel.add(catButtons);
		catButtons.add(catInsert);
		catButtons.add(catUpdate);
		catButtons.add(catDelete);

		// leftpanel에 inner 추가
		leftPanel.add(scrollPane);
		leftPanel.add(buttons);
		buttons.add(insert);
		buttons.add(update);
		buttons.add(delete);

		// rightpanel에 inner 추가

		// frame에 panel추가
		frame.add(categoryPanel);
		frame.add(leftPanel);
		frame.add(rightPanel);

		// 끝
		frame.setVisible(true);
	}

// category 선택하여 product 리스트 불러오기
	public void loadCategory(int selectedIndex) {
		listModel.clear();
		rightPanel.removeAll();
		CategoryDTO categoryDTO = catDTOList.get(selectedIndex);
		dtoList = new ProductDAO_Hcb0402().loadProductDB(categoryDTO.getCategoryId());
		for (ProductDTO productDTO : dtoList) {
			listModel.addElement(String.valueOf(productDTO.getProductId()) + "." + productDTO.getName());
		}
		categoryId = new JComboBox<Integer>(categoryIdArray.toArray(new Integer[0]));
		categoryName = new JTextField(categoryDTO.getName());
		rightPanel.add(new JLabel("categoryId"));
		rightPanel.add(categoryId);
		categoryId.setSelectedItem(categoryDTO.getCategoryId());
		categoryId.setEnabled(false);
		rightPanel.add(new JLabel("categoryName"));
		rightPanel.add(categoryName);
		idJList.setModel(listModel);
		scrollPane.setViewportView(idJList);
		scrollPane.revalidate();
		scrollPane.repaint();
		rightPanel.revalidate();
		rightPanel.repaint();
	}

// product 선택하여 정보 불러오기
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
		categoryId = new JComboBox<Integer>(categoryIdArray.toArray(new Integer[0]));
		categoryId.setSelectedItem(productDTO.getCategoryId());
		categoryId.setEnabled(true);
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
		rightPanel.revalidate();
		rightPanel.repaint();
	}

// category update
	public void updateCat() {
		CategoryDTO categoryDTO = new CategoryDTO(Integer.parseInt(categoryId.getSelectedItem().toString()), categoryName.getText(),
				null);
		new CatDAO_Hcb0402().updateCatDB(categoryDTO);
		frame.dispose();
		new ProductManagement_Hcb0402();
	}

// category insert
	public void insertCat() {
		CategoryDTO categoryDTO = new CategoryDTO(null, categoryName.getText(), null);
		new CatDAO_Hcb0402().insertCatDB(categoryDTO);
		frame.dispose();
		new ProductManagement_Hcb0402();
	}

// category delete
	public void deleteCat() {
		CategoryDTO categoryDTO = new CategoryDTO(Integer.parseInt(categoryId.getSelectedItem().toString()), categoryName.getText(), null);
		new CatDAO_Hcb0402().deleteCatDB(categoryDTO);
		frame.dispose();
		new ProductManagement_Hcb0402();
	}

// product update
	public void updateProduct(int selectedIndex) {
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(currentTimeMillis);
		ProductDTO productDTO = new ProductDTO(dtoList.get(selectedIndex).getProductId(), name.getText(),
				description.getText(), Integer.parseInt(price.getText()), Integer.parseInt(stock.getText()),
				Integer.parseInt(categoryId.getSelectedItem().toString()), timestamp);
		new ProductDAO_Hcb0402().updateDB(productDTO);
		loadCategory(catIndex);
		leftPanel.revalidate();
		leftPanel.repaint();
		rightPanel.revalidate();
		rightPanel.repaint();
	}
// product insert
	public void insertProduct() {
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(currentTimeMillis);
		ProductDTO productDTO = new ProductDTO(
				null, 
				name.getText(),
				description.getText(), 
				Integer.parseInt(price.getText()), 
				Integer.parseInt(stock.getText()),
				Integer.parseInt(categoryId.getSelectedItem().toString()), 
				timestamp
				);
		new ProductDAO_Hcb0402().insertDB(productDTO);
		loadCategory(catIndex);
		leftPanel.revalidate();
		leftPanel.repaint();
		rightPanel.revalidate();
		rightPanel.repaint();
	}
// product delete
	public void deleteProduct(int selectedIndex) {
		ProductDTO productDTO = dtoList.get(selectedIndex);
		new ProductDAO_Hcb0402().deleteDB(productDTO.getProductId());
		loadCategory(catIndex);
		leftPanel.revalidate();
		leftPanel.repaint();
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	//
	public static void main(String[] args) {
		new ProductManagement_Hcb0402();
	}
}
