package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import domain.Bank;
import domain.BuyableSquare;
import domain.MonopolyBoard;
import domain.Stock;

public class BankTest {
	@Test
	public void testBank() {
		//fail("Not yet implemented.");
	}

	@Test
	public void testGetBuyableSquare0() {
		String squareName = "VERMONT AVENUE";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		System.out.println(bank.toString());
		assertEquals(monopolyBoard.getSquare(squareName), bank.getBuyableSquare(squareName));
	}
	
	@Test
	public void testGetBuyableSquare1() {
		String squareName = "";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		assertEquals(null, bank.getBuyableSquare(squareName));
	}

	@Test
	public void testIsUnownedBuyableSquareLeft0() {
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		assertEquals(true, bank.isUnownedBuyableSquareLeft());
	}
	
	@Test
	public void testIsUnownedBuyableSquareLeft1() {
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Iterator<BuyableSquare> iterator = bank.getBuyableSquares().iterator();
		
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		
		assertEquals(false, bank.isUnownedBuyableSquareLeft());
	}

	@Test
	public void testAddBuyableSquare0() {
		String squareName = "VERMONT AVENUE";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		BuyableSquare square = (BuyableSquare) monopolyBoard.getSquare(squareName);
		
		for (int i = 0; i < 5; i++) {
			bank.addBuyableSquare(square);
		}
		
		assertEquals(80, bank.getBuyableSquares().size());
		assertEquals(true, bank.getBuyableSquares().contains(square));
	}
	
	@Test
	public void testAddBuyableSquare1() {
		String squareName = "ORIENTAL AVENUE";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Iterator<BuyableSquare> iterator = bank.getBuyableSquares().iterator();
		
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		
		BuyableSquare square = (BuyableSquare) monopolyBoard.getSquare(squareName);
		bank.addBuyableSquare(square);
		assertEquals(1, bank.getBuyableSquares().size());
		assertEquals(true, bank.getBuyableSquares().contains(square));
	}
	
	@Test
	public void testRemoveBuyableSquare0() {
		String squareName = "ORIENTAL AVENUE";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
	
		BuyableSquare square = (BuyableSquare) monopolyBoard.getSquare(squareName);
		bank.removeBuyableSquare(square);
		assertEquals(79, bank.getBuyableSquares().size());
		assertEquals(false, bank.getBuyableSquares().contains(square));
	}

	@Test
	public void testRemoveBuyableSquare1() {
		String squareName = "VERMONT AVENUE";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
	
		BuyableSquare square = (BuyableSquare) monopolyBoard.getSquare(squareName);
		Iterator<BuyableSquare> iterator = bank.getBuyableSquares().iterator();
		
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		
		bank.removeBuyableSquare(square);
		assertEquals(0, bank.getBuyableSquares().size());
		assertEquals(false, bank.getBuyableSquares().contains(square));
	}
	
	@Test
	public void testIsUnownedStockLeft0() {
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		assertEquals(true, bank.isUnownedStockLeft());
	}
	
	@Test
	public void testIsUnownedStockLeft1() {
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		ArrayList<Stock> stocks = bank.getStocks();
		
		while (!stocks.isEmpty()) {
			stocks.remove(0);
		}
		
		assertEquals(false, bank.isUnownedStockLeft());
	}
	
	@Test
	public void testGetStock0() {
		String stockName = "Acme Motors4";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		
		assertEquals("Acme Motors4", bank.getStock(stockName).getName());
	}
	
	@Test
	public void testGetStock1() {
		String stockName = "Acme Motors4";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		bank.removeStock(bank.getStock(stockName));
		
		assertEquals(null, bank.getStock(stockName));
	}

	@Test
	public void testAddStock0() {
		String stockName = "United Railways0";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Stock stock = bank.getStock(stockName);
		
		for (int i = 0; i < 5; i++) {
			bank.addStock(stock);
		}
		
		assertEquals(30, bank.getStocks().size());
		assertEquals(true, bank.getStocks().contains(stock));
	}
	
	@Test
	public void testAddStock1() {
		String stockName = "United Railways0";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Stock stock = bank.getStock(stockName);
		ArrayList<Stock> stocks = bank.getStocks();
		int initSize = stocks.size();
		
		for (int i = 0; i < initSize; i++) {
			stocks.remove(0);
		}
		
		for (int i = 0; i < 5; i++) {
			bank.addStock(stock);
		}
		
		assertEquals(1, bank.getStocks().size());
		assertEquals(true, bank.getStocks().contains(stock));
	}
	
	@Test
	public void testRemoveStock0() {
		String stockName = "United Railways0";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Stock stock = bank.getStock(stockName);
		bank.removeStock(stock);
		
		assertEquals(29, bank.getStocks().size());
		assertEquals(false, bank.getStocks().contains(stock));
	}

	@Test
	public void testRemoveStock1() {
		String stockName = "United Railways0";
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		Stock stock = bank.getStock(stockName);
		ArrayList<Stock> stocks = bank.getStocks();
		int initSize = stocks.size();
		
		for (int i = 0; i < initSize; i++) {
			stocks.remove(0);
		}
		
		bank.removeStock(stock);
		assertEquals(0, bank.getStocks().size());
		assertEquals(false, bank.getStocks().contains(stock));
	}
	
	@Test
	public void testReceivePayment() {
		int payment = 1000;
		MonopolyBoard monopolyBoard = new MonopolyBoard();
		Bank bank = monopolyBoard.getBank();
		bank.receivePayment(1000);
		
		assertEquals(payment, bank.getPoolMoney());
	}
}
