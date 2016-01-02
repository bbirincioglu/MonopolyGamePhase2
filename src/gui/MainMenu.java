package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.GameOptions;
import domain.SaveLoadUtil;

public class MainMenu extends JPanel {
	private MonopolyGame mainFrame;
	private JButton startButton;
	private JButton loadButton;
	private JButton optionsButton;
	private JButton exitButton;
	
	public MainMenu(MonopolyGame mainFrame) {
		super();
		setLayout(new GridLayout(4, 1));
		setMainFrame(mainFrame);
		initializeChildren();
		addChildren();
	}
	
	private void initializeChildren() {
		Font font = new Font("Sans Serif", Font.BOLD, 20);
		Dimension dimension = new Dimension(400, 100);
		ButtonListener buttonListener = new ButtonListener();
		setStartButton(ComponentBuilder.composeDefaultButton("START", 0, 0, buttonListener, true));
		getStartButton().setFont(font);
		getStartButton().setPreferredSize(dimension);
		setLoadButton(ComponentBuilder.composeDefaultButton("LOAD GAME", 0, 0, buttonListener, true));
		getLoadButton().setFont(font);
		getLoadButton().setPreferredSize(dimension);
		setOptionsButton(ComponentBuilder.composeDefaultButton("OPTIONS", 0, 0, buttonListener, true));
		getOptionsButton().setFont(font);
		getOptionsButton().setPreferredSize(dimension);
		setExitButton(ComponentBuilder.composeDefaultButton("EXIT", 0, 0, buttonListener, true));
		getExitButton().setFont(font);
		getOptionsButton().setPreferredSize(dimension);
	}
	
	private void addChildren() {
		add(getStartButton());
		add(getLoadButton());
		add(getOptionsButton());
		add(getExitButton());
		
		File file = new File(SaveLoadUtil.getSaveLoadFileName());
		
		if(!file.exists()) {
			getLoadButton().setEnabled(false);
		}
	}
	
	public void setMainFrame(MonopolyGame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public MonopolyGame getMainFrame() {
		return mainFrame;
	}
	
	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JButton getOptionsButton() {
		return optionsButton;
	}

	public void setOptionsButton(JButton optionsButton) {
		this.optionsButton = optionsButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}

	public void setExitButton(JButton exitButton) {
		this.exitButton = exitButton;
	}
	
	public void setLoadButton(JButton loadButton) {
		this.loadButton = loadButton;
	}
	
	public JButton getLoadButton() {
		return loadButton;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			MonopolyGame mainFrame = getMainFrame();
			
			if (button.equals(getStartButton())) {
				mainFrame.setGameBoard(new GameBoard(false));
				mainFrame.setContentPane(mainFrame.getGameBoard());
				mainFrame.revalidate();
				mainFrame.repaint();
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
			} else if (button.equals(getOptionsButton())) {
				mainFrame.setOptions(new Options(mainFrame));
				mainFrame.setContentPane(mainFrame.getOptions());
				mainFrame.revalidate();
				mainFrame.repaint();
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
			} else if (button.equals(getExitButton())) {
				mainFrame.dispose();
				System.exit(0);
			} else if (button.equals(getLoadButton())) {
				mainFrame.setGameBoard(new GameBoard(true));
				mainFrame.setContentPane(mainFrame.getGameBoard());
				mainFrame.revalidate();
				mainFrame.repaint();
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
			}
		}
	}
}