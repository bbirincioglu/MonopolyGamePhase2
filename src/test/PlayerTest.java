package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domain.Bank;
import domain.ColorSquare;
import domain.GameController;
import domain.MonopolyBoard;
import domain.Player;
import domain.RailRoadSquare;
import domain.Square;
import domain.Stock;

public class PlayerTest {
	@Test
	public void testGetWealth() {
		GameController gameController = GameController.getNewInstanceEachTime();
		Player testerPlayer = gameController.getCurrentPlayer();
		int wealth=testerPlayer.getWealth();
		testerPlayer.setMoney(testerPlayer.getMoney()-50);
		assertEquals(testerPlayer.getWealth()+50,wealth );
		assertTrue("should preserve representation invariant", testerPlayer.repOK());
		
	}
	@Test
	public void testMoveStep(){
		GameController gameController = GameController.getNewInstanceEachTime();
		Player testerPlayer = gameController.getCurrentPlayer();
		Square currentLocation=testerPlayer.getCurrentLocation();
		Square expectedSquare=currentLocation.getNext().getNext();
		testerPlayer.move(2);
		assertEquals(testerPlayer.getCurrentLocation(), expectedSquare);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testMoveSquare(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		testerPlayer.move(square);
		assertEquals(testerPlayer.getCurrentLocation(), square);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testMoveImmediate(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare square = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		testerPlayer.moveImmediate(square);
		assertEquals(testerPlayer.getCurrentLocation(), square);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testSelectChanceCard(){
	GameController gameController = GameController.getNewInstanceEachTime();
	Player testerPlayer = gameController.getCurrentPlayer();
	testerPlayer.selectChanceCard(monopolyBoard);
	assertTrue(testerPlayer.repOK());
	}
	
	@Test
	public void testSelectCommunityCard(){
		GameController gameController = GameController.getNewInstanceEachTime();
		Player testerPlayer = gameController.getCurrentPlayer();
	testerPlayer.selectCommunityCard(monopolyBoard);
	assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testMakePaymentToPlayer(){
		GameController gameController = GameController.getNewInstanceEachTime();
		Player testerPlayer = gameController.getCurrentPlayer();
		int payment=50;
		int currentMoney=testerPlayer.getMoney();
		Player player=gameController.getPlayers().get(1);
		int currentMoney2=player.getMoney();
		testerPlayer.makePayment(player, payment);
		assertEquals(currentMoney-50, testerPlayer.getMoney());
		assertEquals(currentMoney2+50, player.getMoney());
		assertTrue(testerPlayer.repOK());	
	}
	@Test
	public void testMakePaymentToBank(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank = monopolyBoard.getBank();
		int payment=50;
		int currentMoney=testerPlayer.getMoney();
		testerPlayer.makePayment(bank, payment);
		assertEquals(currentMoney-50, testerPlayer.getMoney());
		assertTrue(testerPlayer.repOK());	
	}
	
	@Test
	public void testReceivePayment(){
		GameController gameController = GameController.getNewInstanceEachTime();
		Player testerPlayer = gameController.getCurrentPlayer();
		int payment=50;
		int currentMoney=testerPlayer.getMoney();
		testerPlayer.receivePayment(player, payment);
		assertEquals(currentMoney+50, testerPlayer.getMoney());
		assertTrue(testerPlayer.repOK());	
	}
	@Test
	public void testBuySquareFromBank() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank = monopolyBoard.getBank();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int payment=testerSquare.getPrice();
		testerPlayer.buySquare(bank, testerSquare, payment);
		ArrayList<Square> squares=testerPlayer.getSquares();
		assertTrue(squares.contains(testerSquare));
		assertTrue(testerPlayer.repOK());
	}
	
	@Test
	public void testBuySquareFromPlayer(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Player player=gameController.getPlayers().get(1);
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int payment=testerSquare.getPrice();
		int currentMoney=testerPlayer.getMoney();
		testerPlayer.buySquare( player,  testerSquare,  payment);
		//how do we test it to see if the transaction is completed without fault
		assertTrue(testerPlayer.getSquares().contains(testerSquare));
		assertFalse(player.getSquares().contains(testerSquare));
		assertTrue(testerPlayer.repOK());
		assertEquals(testerPlayer.getMoney(), currentMoney-payment);
	}
	@Test
	public void testSellSquareToPlayer(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Player player=gameController.getPlayers().get(1);
		Bank bank=monopolyBoard.getBank();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.sellSquare(player, testerSquare, 50);
		assertFalse(testerPlayer.getSquares().contains(testerSquare));
		assertTrue(player.getSquares().contains(testerSquare));
		assertTrue(testerPlayer.repOK());
		assertEquals(testerPlayer.getMoney(), currentMoney+50);
		
	}
	@Test
	public void testBuyHouse(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int payment=testerSquare.getHouseCost();
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.buyHouse(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 1);
		assertEquals(testerPlayer.getMoney(),currentMoney-payment);
		assertTrue(testerPlayer.repOK());
	}
	
	@Test
	public void testBuyHotel(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int payment=testerSquare.getHotelCost();
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.buyHotel(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 5);
		assertEquals(testerPlayer.getMoney(),currentMoney-payment);
		assertTrue(testerPlayer.repOK());
	}
	
	@Test
	public void testBuySkyscraper(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int payment=testerSquare.getSkyscraperCost();
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.buySkyscraper(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 6);
		assertEquals(testerPlayer.getMoney(),currentMoney-payment);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testSellHouse(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		int payment = testerSquare.getHouseCost()/2;
		testerSquare.setBuildingNum(1);
		testerPlayer.sellHouse(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 0);
		assertEquals(testerPlayer.getMoney(),currentMoney+payment);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testSellHotel(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		int payment = testerSquare.getHotelCost()/2;
		testerSquare.setBuildingNum(5);
		testerPlayer.sellHotel(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 4);
		assertEquals(testerPlayer.getMoney(),currentMoney+payment);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testSellSkyscraper(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		int payment = testerSquare.getSkyscraperCost()/2;
		testerSquare.setBuildingNum(6);
		testerPlayer.sellSkyscraper(testerSquare);
		assertEquals(testerSquare.getBuildingNum(), 5);
		assertEquals(testerPlayer.getMoney(),currentMoney+payment);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testBuyTrainDepot() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		RailRoadSquare testerSquare = (RailRoadSquare) monopolyBoard.getSquare("B&0 RAILROAD");
		int payment=100;
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.buyTrainDepot(testerSquare);
		assertTrue(testerSquare.isTrainDepotBuilt());
		assertEquals(testerPlayer.getMoney(),currentMoney-payment);
		assertTrue(testerPlayer.repOK());		
	}
	
	@Test
	public void testSellTrainDepot() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		RailRoadSquare testerSquare = (RailRoadSquare) monopolyBoard.getSquare("B&0 RAILROAD");
		int payment=100;
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.setTrainDepotBuilt(true);
		testerPlayer.sellTrainDepot(testerSquare);
		assertFalse(testerSquare.isTrainDepotBuilt());
		assertEquals(testerPlayer.getMoney(),currentMoney-payment);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testApplyMortgageToSquare(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		int payment=testerSquare.getMortgageValue();
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.applyMortgage(testerSquare);
		assertEquals(testerPlayer.getMoney(), currentMoney+payment );
		assertTrue(testerSquare.isMortgaged());
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testRemoveMortgageFromSquare(){
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		ColorSquare testerSquare = (ColorSquare) monopolyBoard.getSquare("ORIENTAL AVENUE");
		int currentMoney=testerPlayer.getMoney();
		int payment=(int) (1.1 * testerSquare.getMortgageValue() * -1);
		testerPlayer.getSquares().add(testerSquare);
		testerPlayer.setMortgaged(true);
		testerPlayer.removeMortgageFrom(testerSquare);
		assertEquals(testerPlayer.getMoney(), currentMoney-payment );
		assertFalse(testerSquare.isMortgaged());
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testApplyMortgageToStock() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		int loanValue = stock.getLoanValue();
		testerPlayer.getStocks().add(stock);
		testerPlayer.applyMortgageTo(stock);
		assertEquals(testerPlayer.getMoney(), currentMoney+loanValue );
		assertTrue(testerSquare.isMortgaged());
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testRemoveMortgageFromStock() {
	
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		int loanValue = stock.getLoanValue();
		loanValue = ((int) (loanValue * 1.1));
		testerPlayer.getStocks().add(stock);
		testerPlayer.applyMortgageTo(stock);
		assertEquals(testerPlayer.getMoney(), currentMoney-loanValue );
		assertFalse(testerSquare.isMortgaged());
		assertTrue(testerPlayer.repOK());
		
	}
	@Test
	public void testCollectDivident0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		int firstDivident=stock.getFirstDivident();
		ArrayList<Stock> stocks = testerPlayer.getStocks();
		testerPlayer.collectDivident("Motion Pictures");
		assertEquals(testerPlayer.getMoney(), currentMoney);
		assertTrue(testerPlayer.repOK());
		
	}
	@Test
	public void testCollectDivident1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		int firstDivident=stock.getFirstDivident();
		testerPlayer.getStocks().add(stock);
		ArrayList<Stock> stocks = testerPlayer.getStocks();
		testerPlayer.collectDivident("Motion Pictures");
		assertEquals(testerPlayer.getMoney(), currentMoney+firstDivident);
		assertTrue(testerPlayer.repOK());
		
	}
	@Test
	public void testCollectDivident2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		Stock stock1=bank.getStock("Motion Pictures1");
		int firstDivident=stock.getFirstDivident();
		testerPlayer.getStocks().add(stock);
		testerPlayer.getStocks().add(stock1);
		ArrayList<Stock> stocks = testerPlayer.getStocks();
		testerPlayer.collectDivident("Motion Pictures");
		assertEquals(testerPlayer.getMoney(), currentMoney+4*firstDivident);
		assertTrue(testerPlayer.repOK());		
	}
	@Test
	public void testCollectAllDividents0() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		int currentMoney=testerPlayer.getMoney();
		testerPlayer.collectAllDividents();
		assertEquals(testerPlayer.getMoney(), currentMoney);
		assertTrue(testerPlayer.repOK());	
	}
	@Test
	public void testCollectAllDividents1() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		testerPlayer.getStocks().add(stock);
		int firstDivident=stock.getFirstDivident();
		testerPlayer.collectAllDividents();
		assertEquals(testerPlayer.getMoney(), currentMoney);
		assertTrue(testerPlayer.repOK());	
	}
	@Test
	public void testCollectAllDividents2() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		Stock stock1=bank.getStock("Motion Pictures1");
		int firstDivident=stock.getFirstDivident();
		testerPlayer.getStocks().add(stock);
		testerPlayer.getStocks().add(stock1);
		ArrayList<Stock> stocks = testerPlayer.getStocks();
		testerPlayer.collectDivident("Motion Pictures");
		assertEquals(testerPlayer.getMoney(), currentMoney+4*firstDivident);
		assertTrue(testerPlayer.repOK());
	}
	@Test
	public void testBuyStock() {
		GameController gameController = GameController.getNewInstanceEachTime();
		MonopolyBoard monopolyBoard = gameController.getMonopolyBoard();
		Player testerPlayer = gameController.getCurrentPlayer();
		Bank bank=monopolyBoard.getBank();
		int currentMoney=testerPlayer.getMoney();
		Stock stock=bank.getStock("Motion Pictures0");
		int payment=stock.getParValue();
		testerPlayer.buyStock(bank, stock, payment );
		assertEquals(testerPlayer.getMoney(), currentMoney-payment);
		assertTrue(testerPlayer.getStocks().contains(stock));
		assertTrue(testerPlayer.repOK());

	}
	
}

