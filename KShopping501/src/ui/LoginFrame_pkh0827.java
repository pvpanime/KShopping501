package ui;

import dao.UserDAO_pkh0827;
import dto.UserDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame_pkh0827 extends JFrame {
	private JTextField emailField;
	private JPasswordField passwordField;
	private UserDAO_pkh0827 userDAO;

	public LoginFrame_pkh0827() {
		userDAO = new UserDAO_pkh0827();
		setTitle("로그인");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// UI Components
		emailField = new JTextField();
		passwordField = new JPasswordField();
		JButton loginButton = new JButton("로그인");
		JButton signupButton = new JButton("회원가입");

		// Layout setup
		setLayout(new GridLayout(4, 2, 10, 10));

		add(new JLabel("이메일:"));
		add(emailField);
		add(new JLabel("비밀번호:"));
		add(passwordField);
		add(loginButton);
		add(signupButton);

		// 로그인 버튼 이벤트 처리
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());

				// 로그인 처리
				if (userDAO.login(email, password)) {
					// 로그인 성공 후 사용자 정보 저장
					UserDTO loggedInUser = userDAO.getUserByEmail(email);
					if (loggedInUser != null) {
						UiKjh_0313 ui = new UiKjh_0313(loggedInUser);
						// 로그인 성공 후 메인 페이지로 이동
						ui.setVisible(true); // 메인 페이지로 이동
						ui.setLocationRelativeTo(null);
						dispose(); // 로그인 창 닫기
					}
				} else {
					// 로그인 실패 알림
					JOptionPane.showMessageDialog(LoginFrame_pkh0827.this, "로그인 실패. 이메일과 비밀번호를 확인하세요.", "알림",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 회원가입 버튼 이벤트 처리
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입 화면 띄우기
				new SignupFrame_pkh0827().setVisible(true);
				dispose(); // 로그인 창 닫기
			}
		});
	}

}
