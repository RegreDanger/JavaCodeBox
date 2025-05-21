package util.gui.swing.actions;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class SecondaryWindowCloserHandler extends WindowAdapter {
	private JFrame secondaryWindow;
	private boolean whenIsFocusedMain;
	
	public SecondaryWindowCloserHandler(JFrame secondaryWindow, boolean whenIsFocusedMain) {
		this.secondaryWindow = secondaryWindow;
		this.whenIsFocusedMain = whenIsFocusedMain;
	}
	
	@Override
	public void windowLostFocus(WindowEvent e) {
		if(secondaryWindow.isShowing() && !whenIsFocusedMain) {
			secondaryWindow.dispose();
		}
	}
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		if(secondaryWindow.isShowing() && whenIsFocusedMain) {
			secondaryWindow.dispose();
		}
	}
}
