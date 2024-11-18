package ui;

import dao.UserDAO_pkh0827;
import dto.UserDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame_pkh0827 extends JFrame {
	private JTextField nameField, emailField;
	private JPasswordField passwordField;
	private UserDAO_pkh0827 userDAO;
	private JFrame parent;

	public SignupFrame_pkh0827(JFrame parent) {
		this.parent = parent;
		userDAO = new UserDAO_pkh0827();
		setTitle("회원가입");
		setSize(400, 300);
		setLocationRelativeTo(null);

		// UI Components
		nameField = new JTextField();
		emailField = new JTextField();
		passwordField = new JPasswordField();
		JButton signupButton = new JButton("회원가입");

		// Layout setup
		setLayout(new GridLayout(5, 2, 10, 10));
		add(new JLabel("이름:"));
		add(nameField);
		add(new JLabel("이메일:"));
		add(emailField);
		add(new JLabel("비밀번호:"));
		add(passwordField);
		add(signupButton);

		// 이벤트 처리
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = nameField.getText();
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());

				if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(SignupFrame_pkh0827.this, "모든 필드를 입력하세요", "알림", JOptionPane.WARNING_MESSAGE);
				} else {
					UserDTO newUser = new UserDTO();
					newUser.setUsername(username);
					newUser.setEmail(email);
					newUser.setPassword(password); // 평문 비밀번호 저장
					newUser.setIsAdmin(false);

					boolean isSuccess = userDAO.register(newUser);
					if (isSuccess) {
						JOptionPane.showMessageDialog(SignupFrame_pkh0827.this, "회원가입 성공", "알림",
								JOptionPane.INFORMATION_MESSAGE);
						dispose(); // 회원가입 후 로그인 화면으로 돌아가기
						if (!parent.isVisible()) new LoginFrame_pkh0827().setVisible(true);
					} else {
						JOptionPane.showMessageDialog(SignupFrame_pkh0827.this, "회원가입 실패", "알림", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}
