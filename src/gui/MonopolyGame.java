package gui;
import java.util.ArrayList;

import javax.swing.JFrame;

import domain.DialogBuilder;
import domain.GameOptions;
import domain.Reader;
import domain.Square;


public class MonopolyGame extends JFrame {
	private MainMenu mainMenu;
	private GameBoard gameBoard;
	private Options options;
	
	public MonopolyGame() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//int playerNum = GameOptions.getPlayerNum();
		
		DialogBuilder.setMainFrame(this);
		setMainMenu(new MainMenu(this));	
		setContentPane(getMainMenu());
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	
	public void setOptions(Options options) {
		this.options = options;
	}
	
	public Options getOptions() {
		return options;
	}
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
}
