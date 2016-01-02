package gui;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import domain.GameController;
import domain.MonopolyBoard;
import domain.Player;
import domain.Square;

public class GameBoard extends JPanel {
	private MonopolyBoardView monopolyBoardView;
	private DownPanel downPanel;
	
	public GameBoard(boolean isLoad) {
		super();
		setLayout(new GridBagLayout());
		
		GameController.setLoad(isLoad);
		GameController gameController = GameController.getInstance();
		
		if(isLoad) {
			gameController.doLoadGame();
		}
		
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		ArrayList<Player> players = gameController.getPlayers();
		
		setMonopolyBoardView(new MonopolyBoardView(players, monopolyBoard));
		setDownPanel(new DownPanel(players));
		
		add(getMonopolyBoardView(), getMonopolyBoardView().getConstraints());
		add(getDownPanel(), getDownPanel().getConstraints());
	}

	public MonopolyBoardView getMonopolyBoardView() {
		return monopolyBoardView;
	}

	public void setMonopolyBoardView(MonopolyBoardView monopolyBoardView) {
		this.monopolyBoardView = monopolyBoardView;
	}

	public DownPanel getDownPanel() {
		return downPanel;
	}

	public void setDownPanel(DownPanel downPanel) {
		this.downPanel = downPanel;
	}
}