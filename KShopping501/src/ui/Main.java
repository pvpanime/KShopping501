package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> 
		{
			new LoginFrame_pkh0827().setVisible(true);
    });
	}
}
