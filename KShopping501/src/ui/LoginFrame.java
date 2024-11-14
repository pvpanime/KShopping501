package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
	private JTextField emailField;
	private JPasswordField passwordField;
	private UserDAO userDAO;

	public LoginFrame() {
		userDAO = new UserDAO();
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

				if (userDAO.login(email, password)) {
					// 로그인 성공 시 알림
					JOptionPane.showMessageDialog(LoginFrame.this, "로그인 성공! 메인 페이지로 이동합니다.", "알림",
							JOptionPane.INFORMATION_MESSAGE);

					// 로그인 후 메인 페이지로 이동 (실제 메인 페이지 구현 전까지는 생략)
					// 메인 페이지로 이동할 부분은 나중에 추가해주세요
					// 예시: new MainFrame().setVisible(true);

					dispose(); // 현재 로그인 창 닫기
				} else {
					// 로그인 실패 알림
					JOptionPane.showMessageDialog(LoginFrame.this, "로그인 실패. 이메일과 비밀번호를 확인하세요.", "알림",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 회원가입 버튼 이벤트 처리
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입 화면 띄우기
				new SignupFrame().setVisible(true);
				dispose(); // 로그인 창 닫기
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginFrame().setVisible(true);
			}
		});
	}
}
