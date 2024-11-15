package ui;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Util {
  public static JButton button(String label, ActionListener action) {
    JButton button = new JButton(label);
    button.addActionListener(action);
    return button;
  }
}
