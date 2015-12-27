package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import domain.GameController;


public class RollButton extends JButton {
	public RollButton() {
		super();
		setText("Roll");
		addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GameController.getInstance().doRoll();
		}
	}
}