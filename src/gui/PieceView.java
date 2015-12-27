package gui;
import gui.MonopolyBoardView.SquareView;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import domain.Piece;
import domain.PieceObserver;
import domain.Square;


public class PieceView extends JLabel implements PieceObserver {
	public PieceView(Piece piece) {
		super();
		piece.addPieceObserver(this);
		setHorizontalAlignment(CENTER);
		setFont(new Font("Sans Serif", Font.BOLD, 10));
		String playerName = piece.getOwner().getName();
		setText(playerName.substring(playerName.length() - 1, playerName.length()));
	}

	@Override
	public void update(Piece piece) {
		// TODO Auto-generated method stub
		SquareView parent = getSquareView();
		
		if (parent != null) {
			parent.removePieceView(this);
		}
		
		Square square = piece.getCurrentLocation();
		SquareView squareView = (SquareView) square.getSquareObservers().get(0);
		squareView.addPieceView(this);
	}
	
	private SquareView getSquareView() {
		Container parent = getParent();
		
		while (!(parent instanceof SquareView) && parent != null) {
			parent = parent.getParent();
		}
		
		return (SquareView) parent;
	}
}
