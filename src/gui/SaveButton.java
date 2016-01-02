package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import domain.GameController;

public class SaveButton extends JButton {
	public SaveButton() {
		super();
		setText("Save");
		addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GameController.getInstance().doSaveGame();
		}
	}
}
