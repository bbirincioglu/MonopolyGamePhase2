package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.MonopolyBoard;
import domain.Piece;
import domain.Player;

public class PieceTest {
	
	@Test(expected=NullPointerException.class)
	public void testRepOk1(){
		Piece p = null;
		p.repOk();
	}
	@Test
	public void testRepOk2(){
		Piece p = new Piece(new Player("p1", 0));
		p.repOk();
	}
	@Test
	public void testMove1(){
		Piece p = new Piece(new Player("p1", 0));
		MonopolyBoard mp = new MonopolyBoard();
		p.moveImmediate(mp.getSquare("Subway"));
		p.move(3);
		assertEquals("NICOLLET AVENUE",p.getCurrentLocation().getName());
	}
	@Test
	public void testMove2(){
		Piece p = new Piece(new Player("p1", 0));
		MonopolyBoard mp = new MonopolyBoard();
		p.moveImmediate(mp.getSquare("Subway"));
		p.move(mp.getOuterSquares().get(3));
		assertEquals("NICOLLET AVENUE",p.getCurrentLocation().getName());
	}
	@Test
	public void testMove3(){
		Piece p = new Piece(new Player("p1", 0));
		MonopolyBoard mp = new MonopolyBoard();
		p.moveImmediate(mp.getOuterSquares().get(3));
		assertEquals("NICOLLET AVENUE",p.getCurrentLocation().getName());
	}
}