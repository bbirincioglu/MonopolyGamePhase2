package domain;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import gui.ComponentBuilder;
import gui.MonopolyGame;
import gui.SteppedComboBox;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogBuilder {
	private static MonopolyGame mainFrame;
	
	public DialogBuilder() {
	
	}
	
	public static void forcePlayerToSellPropertyToGetOutOfJailDialog(Player currentPlayer) {
		//new SellPropertyToGetOutOfJailDialog(getMainFrame(), currentPlayer);
	}
	
	public static void informativeDialog(String information) {
		JOptionPane.showMessageDialog(getMainFrame(), information);
	}
	
	public static Square busTicketDialog(Player currentPlayer, MonopolyBoard monopolyBoard) {
		Square currentLocation = currentPlayer.getCurrentLocation();
		ArrayList<Square> squaresAtSameLevel = monopolyBoard.getSquaresAtSameLevel(currentLocation);
		String[] options = new String[squaresAtSameLevel.size() - 1];
		int index = 0;
		
		for (Square squareAtSameLevel : squaresAtSameLevel) {
			if (!currentLocation.equals(squareAtSameLevel)) {
				options[index] = squareAtSameLevel.getName();
				index++;
			}
		}
		
		JLabel informationLabel = ComponentBuilder.composeDefaultLabel("PLEASE PICK A SQUARE FROM THE CURRENT LEVEL.");
		JComboBox squareNameComboBox = ComponentBuilder.composeDefaultComboBox(200, 40, options, options[0], null);
		JPanel container = ComponentBuilder.composeDummyContainer(new JComponent[]{informationLabel, squareNameComboBox}, new GridLayout(2, 1), null);
				
		JOptionPane.showMessageDialog(getMainFrame(), container);
		Square selectedSquare = monopolyBoard.getSquare(squareNameComboBox.getSelectedItem().toString());
		return selectedSquare;
	}
	
	public static int busDialog(int die1Value, int die2Value) {
		Integer[] options = new Integer[3];
		options[0] = die1Value;
		options[1] = die2Value;
		options[2] = die1Value + die2Value;
		
		int choice = JOptionPane.showOptionDialog(getMainFrame(), "Select A Value", "Bus Dialog", 0, 0, null, options, 0);
		return options[choice];
	}
	
	public static String buyOrAuctionDialog(Player currentPlayer, BuyableSquare square) {
		String choice;
		String squareName = square.getName();
		String squarePrice = "$" + square.getPrice();
		String playerName = currentPlayer.getName();
		String question = playerName.toUpperCase() + ", WHAT DO YOU WANT TO DO?";
		
		JButton squareNameButton = ComponentBuilder.composeDefaultButton(squareName, 200, 50, null, false);
		JButton squarePriceButton = ComponentBuilder.composeDefaultButton(squarePrice, 200, 50, null, false);
		JLabel questionLabel = ComponentBuilder.composeDefaultLabel(question);
		JPanel dialogPanel = ComponentBuilder.composeDummyContainer(new JComponent[]{squareNameButton, squarePriceButton, questionLabel}, new GridLayout(3, 1), null);
		
		String[] optionsAsString = new String[]{"Buy", "Auction"};
		int result = JOptionPane.showOptionDialog(getMainFrame(), dialogPanel, "Buy Or Start Auction", JOptionPane.YES_NO_OPTION, 0, null, optionsAsString, null);
		
		if (result == 0) {
			choice = "Buy";
		} else {
			choice = "Auction";
		}
		
		return choice;
	}
	
	public static int[] auctionDialog(ArrayList<Player> players, Object object) {
		int[] bids = new int[players.size()];
		
		class ButtonListener implements ActionListener {
			private int currentPlayerIndex;
			private ArrayList<JPanel> playerPanels;
			
			public ButtonListener() {
				this.currentPlayerIndex = 0;
				this.playerPanels = playerPanels;
			}
			
			public void setCurrentPlayerIndex(int currentPlayerIndex) {
				this.currentPlayerIndex = currentPlayerIndex;
			}
			
			public int getCurrentPlayerIndex() {
				return currentPlayerIndex;
			}
			
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				JPanel playerPanel = (JPanel) button.getParent();
				JTextField moneyTextField = (JTextField) playerPanel.getComponent(0);
				int bid = Integer.valueOf(moneyTextField.getText());
				bids[getCurrentPlayerIndex()] = bid;
				setCurrentPlayerIndex(getCurrentPlayerIndex() + 1);
				
				if (getCurrentPlayerIndex() == getPlayerPanels().size()) {
					disablePanel(getPlayerPanels().get(getCurrentPlayerIndex() - 1));
				} else {
					disableAllPanels();
					enablePanel(getPlayerPanels().get(getCurrentPlayerIndex()));
				}
			}
			
			public void disableAllPanels() {
				ArrayList<JPanel> playerPanels = getPlayerPanels();
				
				for (int i = 0; i < playerPanels.size(); i++) {
					disablePanel(playerPanels.get(i));
				}
			}
			
			public void enablePanel(JPanel panel) {
				int size = panel.getComponentCount();
				Component[] children = panel.getComponents();
				
				for (int i = 0; i < size; i++) {
					Component child = children[i];
					
					if (child instanceof JComponent) {
						((JComponent) child).setEnabled(true);
					}
				}
			}
			
			public void disablePanel(JPanel panel) {
				int size = panel.getComponentCount();
				Component[] children = panel.getComponents();
				
				for (int i = 0; i < size; i++) {
					Component child = children[i];
					
					if (child instanceof JComponent) {
						((JComponent) child).setEnabled(false);
					}
				}
			}
			
			public void setPlayerPanels(ArrayList<JPanel> playerPanels) {
				this.playerPanels = playerPanels;
				disableAllPanels();
				enablePanel(playerPanels.get(getCurrentPlayerIndex()));
			}
			
			public ArrayList<JPanel> getPlayerPanels() {
				return playerPanels;
			}
		}
		
		String name = null;
		String price = null;
		
		if (object instanceof BuyableSquare) {
			BuyableSquare square = (BuyableSquare) object;
			name = square.getName().toUpperCase();
			price = "$" + square.getPrice();
		} else if (object instanceof Stock){
			Stock stock = (Stock) object;
			name = stock.getName().substring(0, stock.getName().length() - 1).toUpperCase();
			price = "$" + stock.getParValue();
		}
		
		class BidListener implements KeyListener {
			private Player player;
			
			public BidListener(Player player) {
				this.player = player;
			}
			
			public void setPlayer(Player player) {
				this.player = player;
			}
			
			public Player getPlayer() {
				return player;
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
				Player player = getPlayer();
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				
				if (text.equals("") || hasInvalidChars(text)) {
					textField.setText("0");
				} else {
					int bid = Integer.valueOf(textField.getText());	
					
					if (bid > player.getMoney()) {
						textField.setText(String.valueOf(player.getMoney()));
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				Player player = getPlayer();
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				
				if (text.equals("") || hasInvalidChars(text)) {
					textField.setText("0");
				} else {
					int bid = Integer.valueOf(textField.getText());	
					
					if (bid > player.getMoney()) {
						textField.setText(String.valueOf(player.getMoney()));
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			private boolean hasInvalidChars(String text) {
				boolean result = false;
				
				try {
					int length = text.length();
					
					for (int i = 0; i < length; i++) {
						int dummy = Integer.valueOf(text.charAt(i) + "");
					}
				} catch (Exception e) {
					result = true;
				}
				
				return result;
			}
		}
		
		JButton nameButton = ComponentBuilder.composeDefaultButton(name, 0, 0, null, false);
		JButton priceButton = ComponentBuilder.composeDefaultButton(price, 0, 0, null, false);
		JPanel nestedContainer = ComponentBuilder.composeDummyContainer(new JComponent[]{nameButton, priceButton}, new GridLayout(2, 1), null);
		ArrayList playerPanels = new ArrayList<JPanel>();
		
		ButtonListener buttonListener = new ButtonListener();
		
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			String playerName = player.getName();
			String playerMoney = "$" + player.getMoney();
			
			JLabel playerNameLabel = ComponentBuilder.composeDefaultLabel(playerName);
			JLabel playerMoneyLabel = ComponentBuilder.composeDefaultLabel(playerMoney);
			JButton bidButton = ComponentBuilder.composeDefaultButton("Make Bid", 0, 0, buttonListener, true);
			JTextField bidTextField = ComponentBuilder.composeDefaultTextField(40, 20, "0", null);
			bidTextField.addKeyListener(new BidListener(player));
			playerPanels.add(ComponentBuilder.composeDummyContainer(new JComponent[]{bidTextField, bidButton, playerMoneyLabel, playerNameLabel}, new GridLayout(4, 1), null));
		}
		
		buttonListener.setPlayerPanels(playerPanels);
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());
		mainContainer.add(nestedContainer, BorderLayout.NORTH);
		
		nestedContainer = ComponentBuilder.composeDummyContainer(playerPanels, null, null);
		mainContainer.add(nestedContainer, BorderLayout.CENTER);
		JOptionPane.showMessageDialog(mainContainer, mainContainer);
		JDialog dialog = ComponentBuilder.composeDefaultDialog(mainContainer);
		return bids;
	}
	
	public static String pickAnUnownedSquareDialog(Bank bank) {
		String squareName = null;
		ArrayList<BuyableSquare> squares = bank.getBuyableSquares();
		String[] squareNames = new String[squares.size()];
		int size = squares.size();
		
		for (int i = 0; i < size; i++) {
			squareNames[i] = squares.get(i).getName();
		}
		
		SteppedComboBox comboBox = ComponentBuilder.composeDefaultSteppedComboBox(100, 20, 200, squareNames, squareNames[0], null);
		JLabel informativeLabel = ComponentBuilder.composeDefaultLabel("PLEASE PICK A SQUARE FOR AUCTION.");
		JPanel container = ComponentBuilder.composeDummyContainer(new JComponent[]{comboBox, informativeLabel}, new GridLayout(2, 1), null);
		JOptionPane.showMessageDialog(getMainFrame(), container);
		squareName = comboBox.getSelectedItem().toString();
		System.out.println(squareName);
		return squareName;
	}
	
	public static String pickAnUnownedStockDialog(Bank bank) {
		String stockName;
		ArrayList<Stock> stocks = bank.getStocks();
		String[] stockNames = new String[stocks.size()];
		int size = stocks.size();
		
		for (int i = 0; i < size; i++) {
			stockNames[i] = stocks.get(i).getName();
		}
		
		SteppedComboBox comboBox = ComponentBuilder.composeDefaultSteppedComboBox(100, 20, 200, stockNames, stockNames[0], null);
		JLabel informativeLabel = ComponentBuilder.composeDefaultLabel("PLEASE PICK A STOCK.");
		JPanel container = ComponentBuilder.composeDummyContainer(new JComponent[]{comboBox, informativeLabel}, new GridLayout(2, 1), null);
		JOptionPane.showMessageDialog(getMainFrame(), container);
		stockName = comboBox.getSelectedItem().toString();
		System.out.println(stockName);
		return stockName;
	}
	
	public static String squareDialog(String comboBoxName) {
		String response;
		String[] options = null;
		
		if (comboBoxName.equals("COLORS")) {
			 options = new String[]{"Apply Mortgage", "Remove Mortgage", "Buy Building", "Sell Building", "Sell"};
		} else if (comboBoxName.equals("UTILITIES")) {
			 options = new String[]{"Apply Mortgage", "Remove Mortgage", "Sell"};
		} else if (comboBoxName.equals("CABS")) {
			 options = new String[]{"Apply Mortgage", "Remove Mortgage", "Sell"};
		} else if (comboBoxName.equals("RAILROADS")) {
			 options = new String[]{"Apply Mortgage", "Remove Mortgage", "Buy Train Depot", "Sell Train Depot", "Sell"};
		}
		
		JButton informativeButton = ComponentBuilder.composeDefaultButton("WHAT WOULD YOU LIKE TO DO?", 0, 0, null, false);
		int selected = JOptionPane.showOptionDialog(getMainFrame(), informativeButton, "Square Options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if (selected == -1) {
			response = "quit";
		} else {
			response = options[selected];
		}
		
		return response;
	}
	
	public static boolean yesNoDialog(String message) {
		boolean result;
		String[] yesNo = new String[]{"Yes", "No"};
		int resultAsInt = JOptionPane.showOptionDialog(getMainFrame(), message, "Yes No Dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, yesNo, yesNo[0]);
		
		if (resultAsInt == 0) {
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	public static String stockDialog(String stockName) {
		String response;
		String[] options = new String[]{"Apply Mortgage", "Remove Mortgage", "Sell"};
		JButton informativeButton = ComponentBuilder.composeDefaultButton("WHAT WOULD YOU LIKE TO DO?", 0, 0, null, false);
		int selected = JOptionPane.showOptionDialog(getMainFrame(), informativeButton, "Stock Options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if (selected == -1) {
			response = "quit";
		} else {
			response = options[selected];
		}
		
		return response;
	}
	
	public static void setMainFrame(MonopolyGame mainFrame) {
		DialogBuilder.mainFrame = mainFrame;
	}
	
	public static MonopolyGame getMainFrame() {
		return mainFrame;
	}
}