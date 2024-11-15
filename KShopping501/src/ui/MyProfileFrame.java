package ui;

import dao.UserDAO_pkh0827;
import dto.UserDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyProfileFrame extends JFrame {
	private JTextField nameField, emailField;
	private JPasswordField passwordField;
	private UserDAO_pkh0827 userDAO;
	private UserDTO user;

	public MyProfileFrame(UserDTO user) {
		this.user = user; // 로그인된 사용자 정보 받아오기
		userDAO = new UserDAO_pkh0827();

		setTitle("내 정보");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// UI Components
		nameField = new JTextField(user.getUsername());
		emailField = new JTextField(user.getEmail());
		passwordField = new JPasswordField(user.getPassword()); // 이미 설정된 비밀번호
		JButton saveButton = new JButton("저장");
		JButton editButton = new JButton("수정");

		// Layout setup
		setLayout(new GridLayout(6, 2, 10, 10));
		add(new JLabel("이름:"));
		add(nameField);
		add(new JLabel("이메일:"));
		add(emailField);
		add(new JLabel("비밀번호:"));
		add(passwordField);
		add(editButton); // 수정 버튼 추가
		add(saveButton); // 저장 버튼 추가

		// 초기 상태에서는 수정 불가 (비활성화)
		nameField.setEditable(false);
		emailField.setEditable(false);
		passwordField.setEditable(false);
		saveButton.setEnabled(false);

		// 수정 버튼 이벤트 처리
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 수정 모드로 변경
				nameField.setEditable(true);
				emailField.setEditable(true);
				passwordField.setEditable(true);
				saveButton.setEnabled(true); // 저장 버튼 활성화
				editButton.setEnabled(false); // 수정 버튼 비활성화
			}
		});

		// 저장 버튼 이벤트 처리
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 수정된 내용 가져오기
				String username = nameField.getText();
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());

				if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(MyProfileFrame.this, "모든 필드를 입력하세요", "알림",
							JOptionPane.WARNING_MESSAGE);
				} else {
					user.setUsername(username);
					user.setEmail(email);
					user.setPassword(password);

					boolean isSuccess = userDAO.updateUser(user);
					if (isSuccess) {
						JOptionPane.showMessageDialog(MyProfileFrame.this, "정보가 수정되었습니다.", "알림",
								JOptionPane.INFORMATION_MESSAGE);
						dispose(); // 정보 수정 후 창 닫기
						new MyProfileFrame(user).setVisible(true); // 수정된 정보로 다시 내 정보 페이지 열기
					} else {
						JOptionPane.showMessageDialog(MyProfileFrame.this, "정보 수정 실패", "알림",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// 예시로 로그인된 사용자 정보 (실제 로그인 후 전달되어야 함)
				UserDTO user = new UserDTO(1, "홍길동", "hong@domain.com", "1234", null, false);
				new MyProfileFrame(user).setVisible(true);
			}
		});
	}
}
