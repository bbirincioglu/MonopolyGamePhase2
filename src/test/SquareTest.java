package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.ColorSquare;
import domain.GameController;
import domain.MonopolyBoard;
import domain.Player;

public class SquareTest {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRemovePiece() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		testerSquare.getPieces().add(testerPlayer.getPiece());
		testerSquare.removePiece(testerPlayer.getPiece());
		assertFalse(testerSquare.getPieces().contains(testerPlayer.getPiece()));
		assertTrue(testerSquare.repOK());
	}
	@Test
	public void testAddPiece(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		testerSquare.addPiece(testerPlayer.getPiece());
		assertTrue(testerSquare.getPieces().contains(testerPlayer.getPiece()));
		assertTrue(testerSquare.repOK());
	}
}
