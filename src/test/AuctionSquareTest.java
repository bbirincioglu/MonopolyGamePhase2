package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.AuctionSquare;
import domain.GameController;
import domain.MonopolyBoard;

public class AuctionSquareTest {

	@Test
	public void test() {
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		AuctionSquare auctionSquare = (AuctionSquare) monopolyBoard.getSquare("AuctionSquare");
		gameController.getCurrentPlayer().moveImmediate(monopolyBoard.getSquare("MAGAZINE STREET"));
		System.out.println(gameController.getCurrentPlayer().getSquares().size());
		gameController.getCurrentPlayer().move(auctionSquare);
		System.out.println(gameController.getCurrentPlayer().getSquares().size());
	}
}
