package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domain.Bank;
import domain.Checker;
import domain.ColorSquare;
import domain.GameController;
import domain.MonopolyBoard;
import domain.Player;
import domain.RailRoadSquare;
import domain.Stock;

public class CheckerTest {

	@Test
	public void testChecker() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckBuySquare0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		System.out.println(monopolyBoard.toString());
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(0);
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		
		String result = checker.checkBuySquare(buyer, bank, square, square.getPrice());
		System.out.println(result);
		assertEquals("true", result);
	}
	
	@Test
	public void testCheckBuySquare1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(0);
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		
		buyer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuySquare(buyer, bank, square, square.getPrice()));
	}
	
	@Test
	public void testCheckBuySquare2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(0);
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		
		assertEquals(Checker.ALREADY_OWNED_ERROR, checker.checkBuySquare(buyer, seller, square, square.getPrice()));
	}
	
	@Test
	public void testCheckBuySquare3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(1);
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		
		buyer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuySquare(buyer, seller, square, square.getPrice()));
	}
	
	@Test
	public void testCheckBuySquare4() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(1);
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		square.setBuildingNum(3);
		
		assertEquals(Checker.SQUARE_HAS_BUILDING_ERROR, checker.checkBuySquare(buyer, seller, square, square.getPrice()));
	}
	
	@Test
	public void testCheckBuySquare5() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Checker checker = gameController.getChecker();
		Player buyer = gameController.getCurrentPlayer();
		Player seller = gameController.getPlayers().get(1);
		RailRoadSquare square = (RailRoadSquare) monopolyBoard.getSquare("READING RAILROAD");
		square.setTrainDepotBuilt(true);
		
		assertEquals(Checker.SQUARE_HAS_BUILDING_ERROR, checker.checkBuySquare(buyer, seller, square, square.getPrice()));
	}

	@Test
	public void testCheckSellSquare() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCheckApplyMortgageBuyableSquare0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, square.getPrice());
		assertEquals("true", checker.checkApplyMortgage(square));
	}

	@Test
	public void testCheckApplyMortgageBuyableSquare1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkApplyMortgage(square));
	}
	
	@Test
	public void testCheckApplyMortgageBuyableSquare2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, square.getPrice());
		square.setMortgaged(true);
		assertEquals(Checker.ALREADY_MORTGAGED_ERROR, checker.checkApplyMortgage(square));
	}
	
	@Test
	public void testCheckRemoveMortgageBuyableSquare0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, square.getPrice());
		currentPlayer.applyMortgageTo(square);
		assertEquals("true", checker.checkRemoveMortgage(square));
	}
	
	@Test
	public void testCheckRemoveMortgageBuyableSquare1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		//currentPlayer.buySquare(bank, square, square.getPrice());
		//currentPlayer.applyMortgageTo(square);
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkRemoveMortgage(square));
	}
	
	@Test
	public void testCheckRemoveMortgageBuyableSquare2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, square.getPrice());
		//currentPlayer.applyMortgageTo(square);
		assertEquals(Checker.ALREADY_NOT_MORTGAGED_ERROR, checker.checkRemoveMortgage(square));
	}
	
	@Test
	public void testCheckRemoveMortgageBuyableSquare3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, square.getPrice());
		currentPlayer.applyMortgageTo(square);
		currentPlayer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkRemoveMortgage(square));
	}

	@Test
	public void testCheckBuyBuilding0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		square.setMortgaged(true);
		assertEquals(Checker.MORTGAGED_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		square.setBuildingNum(6);
		assertEquals(Checker.CANT_BUY_ANYMORE_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		assertEquals(Checker.MAJORITY_OWNERSHIP_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding4() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		square.setBuildingNum(1);
		assertEquals(Checker.TOO_MUCH_IMPROVEMENT_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding5() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		currentPlayer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding6() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		assertEquals(Checker.RESULT_HOUSE, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding7() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		square.setBuildingNum(4);
		square1.setBuildingNum(4);
		currentPlayer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding8() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		square.setBuildingNum(4);
		square1.setBuildingNum(4);
		assertEquals(Checker.RESULT_HOTEL, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding9() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		square.setBuildingNum(5);
		square1.setBuildingNum(5);
		assertEquals(Checker.MONOPOLY_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding10() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		ColorSquare square2 = (ColorSquare) monopolyBoard.getSquare("CONNECTICUT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		currentPlayer.buySquare(bank, square2, 0);
		square.setBuildingNum(5);
		square1.setBuildingNum(5);
		square2.setBuildingNum(5);
		currentPlayer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding11() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		ColorSquare square2 = (ColorSquare) monopolyBoard.getSquare("CONNECTICUT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		currentPlayer.buySquare(bank, square2, 0);
		square.setBuildingNum(5);
		square1.setBuildingNum(5);
		square2.setBuildingNum(5);
		assertEquals(Checker.RESULT_SKYSCRAPER, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckBuyBuilding12() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player currentPlayer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		ColorSquare square1 = (ColorSquare) monopolyBoard.getSquare("VERMONT AVENUE");
		ColorSquare square2 = (ColorSquare) monopolyBoard.getSquare("CONNECTICUT AVENUE");
		currentPlayer.buySquare(bank, square, 0);
		currentPlayer.buySquare(bank, square1, 0);
		currentPlayer.buySquare(bank, square2, 0);
		square.setBuildingNum(5);
		square1.setBuildingNum(4);
		square2.setBuildingNum(5);
		assertEquals(Checker.TOO_MUCH_IMPROVEMENT_ERROR, checker.checkBuyBuilding(square));
	}
	
	@Test
	public void testCheckSellBuilding() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckBuyTrainDepot0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		assertEquals("true", checker.checkBuyTrainDepot(square));
	}
	
	@Test
	public void testCheckBuyTrainDepot1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		//buyer.buySquare(bank, square, square.getPrice());
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkBuyTrainDepot(square));
	}
	
	@Test
	public void testCheckBuyTrainDepot2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		buyer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuyTrainDepot(square));
	}
	
	@Test
	public void testCheckBuyTrainDepot3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		square.setTrainDepotBuilt(true);
		assertEquals(Checker.ALREADY_TRAIN_DEPOT_BUILT_ERROR, checker.checkBuyTrainDepot(square));
	}
	
	@Test
	public void testCheckBuyTrainDepot4() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		buyer.applyMortgageTo(square);
		assertEquals(Checker.MORTGAGED_ERROR, checker.checkBuyTrainDepot(square));
	}

	@Test
	public void testCheckSellTrainDepot0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		buyer.buyTrainDepot(square);
		assertEquals("true", checker.checkSellTrainDepot(square));
	}
	
	@Test
	public void testCheckSellTrainDepot1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		//buyer.buySquare(bank, square, square.getPrice());
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkSellTrainDepot(square));
	}
	
	@Test
	public void testCheckSellTrainDepot2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		buyer.buyTrainDepot(square);
		buyer.applyMortgageTo(square);
		assertEquals(Checker.MORTGAGED_ERROR, checker.checkSellTrainDepot(square));
	}
	
	@Test
	public void testCheckSellTrainDepot3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		RailRoadSquare square = (RailRoadSquare) bank.getBuyableSquare("READING RAILROAD");
		buyer.buySquare(bank, square, square.getPrice());
		//buyer.buyTrainDepot(square);
		//buyer.applyMortgageTo(square);
		assertEquals(Checker.ALREADY_NO_TRAIN_DEPOT_BUILT_ERROR, checker.checkSellTrainDepot(square));
	}


	@Test
	public void testCheckBuyStock0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		assertEquals("true", checker.checkBuyStock(buyer, bank, stock, stock.getParValue()));
	}
	
	@Test
	public void testCheckBuyStock1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkBuyStock(buyer, bank, stock, stock.getParValue()));
	}
	
	@Test
	public void testCheckApplyMortgageStock0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkApplyMortgage(stock));
	}
	
	@Test
	public void testCheckApplyMortgageStock1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.buyStock(bank, stock, stock.getParValue());
		stock.setMortgaged(true);
		assertEquals(Checker.ALREADY_MORTGAGED_ERROR, checker.checkApplyMortgage(stock));
	}
	
	@Test
	public void testCheckApplyMortgageStock2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.buyStock(bank, stock, stock.getParValue());
		assertEquals("true", checker.checkApplyMortgage(stock));
	}

	@Test
	public void testCheckRemoveMortgageStock0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.buyStock(bank, stock, stock.getParValue());
		buyer.applyMortgageTo(stock);
		assertEquals("true", checker.checkRemoveMortgage(stock));
	}
	
	@Test
	public void testCheckRemoveMortgageStock1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.buyStock(bank, stock, stock.getParValue());
		//buyer.applyMortgageTo(stock);
		assertEquals(Checker.ALREADY_NOT_MORTGAGED_ERROR, checker.checkRemoveMortgage(stock));
	}
	
	@Test
	public void testCheckRemoveMortgageStock2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		buyer.buyStock(bank, stock, stock.getParValue());
		buyer.applyMortgageTo(stock);
		buyer.setMoney(0);
		assertEquals(Checker.NOT_ENOUGH_MONEY_ERROR, checker.checkRemoveMortgage(stock));
	}
	
	@Test
	public void testCheckRemoveMortgageStock3() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player buyer = gameController.getCurrentPlayer();
		Checker checker = gameController.getChecker();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		Stock stock = bank.getStock("Acme Motors0");
		//buyer.buyStock(bank, stock, stock.getParValue());
		buyer.applyMortgageTo(stock);
		assertEquals(Checker.NOT_OWNED_ERROR, checker.checkRemoveMortgage(stock));
	}

}
