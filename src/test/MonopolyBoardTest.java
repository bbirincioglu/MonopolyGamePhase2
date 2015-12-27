package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.ChanceCard;
import domain.CommunityCard;
import domain.GameController;
import domain.MonopolyBoard;
import domain.Square;

public class MonopolyBoardTest {
	@Test
	public void testGetSquare0() {
		String squareName = "ORIENTAL AVENUE";
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Square square = monopolyBoard.getSquare(squareName);
		assertEquals(squareName, square.getName());
	}
	
	@Test
	public void testGetSquare1() {
		String squareName = "";
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Square square = monopolyBoard.getSquare(squareName);
		assertEquals(null, square);
	}

	@Test
	public void testGetChanceCard0() {
		String content;
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		int currentChanceCardIndex = monopolyBoard.getCurrentChanceCardIndex();
		content = monopolyBoard.getChanceCards().get(currentChanceCardIndex).getContent();
		ChanceCard chanceCard = monopolyBoard.getChanceCard();
		assertEquals(content, chanceCard.getContent());
	}
	
	@Test
	public void testGetChanceCard1() {
		String content;
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		
		for (int i = 0; i < monopolyBoard.getChanceCards().size() - 1; i++) {
			monopolyBoard.getChanceCard();
		}
		
		int currentChanceCardIndex = monopolyBoard.getCurrentChanceCardIndex();
		content = monopolyBoard.getChanceCards().get(currentChanceCardIndex).getContent();
		ChanceCard chanceCard = monopolyBoard.getChanceCard();
		assertEquals(content, chanceCard.getContent());
	}

	@Test
	public void testGetCommunityCard0() {
		String content;
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		int currentCommunityCardIndex = monopolyBoard.getCurrentCommunityCardIndex();
		content = monopolyBoard.getCommunityCards().get(currentCommunityCardIndex).getContent();
		CommunityCard communityCard = monopolyBoard.getCommunityCard();
		assertEquals(content, communityCard.getContent());
	}
	
	@Test
	public void testGetCommunityCard1() {
		String content;
		GameController gameController = GameController.getInstance();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		
		for (int i = 0; i < monopolyBoard.getCommunityCards().size() - 1; i++) {
			monopolyBoard.getCommunityCard();
		}
		
		int currentCommunityCardIndex = monopolyBoard.getCurrentCommunityCardIndex();
		content = monopolyBoard.getCommunityCards().get(currentCommunityCardIndex).getContent();
		CommunityCard communityCard = monopolyBoard.getCommunityCard();
		assertEquals(content, communityCard.getContent());
	}
}