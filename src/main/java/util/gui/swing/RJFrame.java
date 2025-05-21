package util.gui.swing;

import java.awt.Rectangle;

import javax.swing.JFrame;

public class RJFrame extends JFrame {
	private static final long serialVersionUID = 7957682029190274911L;
	
	public RJFrame(String title) {
		super(title);
	}
	
	public void hideOrPop(Rectangle rect) {
		if (isShowing()) {
			dispose();
		} else {
			setBounds(rect);
			setVisible(true);
		}
	}
}
