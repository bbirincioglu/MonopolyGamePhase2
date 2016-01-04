package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import domain.Reader;
import domain.Writer;


public class Options extends JPanel {
	private static Options reference;
	private MonopolyGame mainFrame;
	private MainOptions mainOptions;
	private JButton saveAndReturnButton;
	
	public Options(MonopolyGame mainFrame) {
		super();
		setMainFrame(mainFrame);
		setLayout(new BorderLayout());
		
		setMainOptions(new MainOptions());
		add(getMainOptions(), BorderLayout.NORTH);
		setSaveAndReturnButton(new JButton("Save And Return To Main Menu"));
		getSaveAndReturnButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Writer writer = new Writer();
				MainOptions mainOptions = getMainOptions();	
				
				if (mainOptions.getDebugCheckBox().isSelected()) {
					JPanel playersPanelsContainer = getPlayersPanelsContainer();
					ArrayList<JSONObject> playersInfosAsJSON = new ArrayList<JSONObject>();
					
					for (int i = 0; i < playersPanelsContainer.getComponentCount(); i++) {
						playersInfosAsJSON.add(((Options.PlayersPanel.PlayerPanel) playersPanelsContainer.getComponent(i)).toJSON());
						
					}
					
					writer.write("debug.txt", playersInfosAsJSON);
				}
				
				ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
				temp.add(getMainOptions().toJSON());
				writer.write("options.txt", temp);
				
				MonopolyGame mainFrame = getMainFrame();
				mainFrame.setContentPane(mainFrame.getMainMenu());
				mainFrame.pack();
				mainFrame.revalidate();
				mainFrame.repaint();
			}
		});
		
		add(getSaveAndReturnButton(), BorderLayout.SOUTH);
		reference = this;
	}
	
	public JPanel getPlayersPanelsContainer() {
		JPanel container = null;
		
		for (int i = 0; i < getComponentCount(); i++) {
			Component child = getComponent(i);
			
			if (child instanceof PlayersPanel) {
				PlayersPanel playersPanel = (PlayersPanel) child;
				Options.PlayersPanel.PlayerPanel selectedPlayerPanel = playersPanel.getSelectedPlayerPanel();
				container = (JPanel) selectedPlayerPanel.getParent();
				break;
			}
		}
		
		return container;
	}
	
	public MainOptions getMainOptions() {
		return mainOptions;
	}
	
	public void setMainOptions(MainOptions mainOptions) {
		this.mainOptions = mainOptions;
	}
	
	public JButton getSaveAndReturnButton() {
		return saveAndReturnButton;
	}
	
	public void setSaveAndReturnButton(JButton saveAndReturnButton) {
		this.saveAndReturnButton = saveAndReturnButton;
	}
	
	private class MainOptions extends JPanel {
		private final String[] FIELDS_NAMES = new String[]{"playerNumber", "playerMoney", "isDebugging", "currentPlayerIndex"};
		private JLabel pnLabel;
		private JScrollBar playerNumScrollBar;
		private JLabel playerNumLabel;
		
		private JLabel mnyLabel;
		private JLabel moneyLabel;
		private JButton increaseBy100;
		private JButton decreaseBy100;
		
		private JCheckBox debugCheckBox;
		private JTextField currentPlayerIndexTextField;
		
		public MainOptions() {
			super();
			setLayout(new FlowLayout());
			setPnLabel(ComponentBuilder.composeDefaultLabel("Player Number:"));
			setPlayerNumScrollBar(ComponentBuilder.composeScrollBar(JScrollBar.HORIZONTAL,
					200, 20, 90, 20, 10, new ScrollBarListener()));
			setPlayerNumLabel(ComponentBuilder.composeDefaultLabel("2"));
			
			setMnyLabel(ComponentBuilder.composeDefaultLabel("Money: "));
			setMoneyLabel(ComponentBuilder.composeDefaultLabel("$0"));
			getMoneyLabel().setPreferredSize(new Dimension(50, 20));
			setIncreaseBy100(ComponentBuilder.composeDefaultButton("+100", 0, 0, new ButtonListener(), true));
			setDecreaseBy100(ComponentBuilder.composeDefaultButton("-100", 0, 0, new ButtonListener(), true));
			
			setDebugCheckBox(ComponentBuilder.composeDefaultCheckBox("Debug", new CheckBoxListener()));
			setCurrentPlayerIndexTextField(ComponentBuilder.composeDefaultTextField(20, 20, "0", null));
			
			add(getPnLabel());
			add(getPlayerNumScrollBar());
			add(getPlayerNumLabel());
			add(getMnyLabel());
			add(getMoneyLabel());
			add(getIncreaseBy100());
			add(getDecreaseBy100());
			add(getDebugCheckBox());
			add(getCurrentPlayerIndexTextField());
		}
	
		public JTextField getCurrentPlayerIndexTextField() {
			return currentPlayerIndexTextField;
		}

		public void setCurrentPlayerIndexTextField(
				JTextField currentPlayerIndexTextField) {
			this.currentPlayerIndexTextField = currentPlayerIndexTextField;
		}

		public String[] getFIELDS_NAMES() {
			return FIELDS_NAMES;
		}

		public JSONObject toJSON() {
			JSONObject mainOptionsAsJSON = new JSONObject();
			String playerNumber = getPlayerNumLabel().getText();
			String playerMoney = getMoneyLabel().getText().substring(1, getMoneyLabel().getText().length());
			String isDebugging = String.valueOf(getDebugCheckBox().isSelected());
			String currentPlayerIndex = getCurrentPlayerIndexTextField().getText();
			
			try {
				mainOptionsAsJSON.put(FIELDS_NAMES[0], playerNumber);
				mainOptionsAsJSON.put(FIELDS_NAMES[1], playerMoney);
				mainOptionsAsJSON.put(FIELDS_NAMES[2], isDebugging);
				mainOptionsAsJSON.put(FIELDS_NAMES[3], currentPlayerIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mainOptionsAsJSON;
		}
		
		public MainOptions fromJSON(JSONObject mainOptionsAsJSON) {
			/*try {
				String playerNumber = mainOptionsAsJSON.getString(FIELDS_NAMES[0]);
				String playerMoney = mainOptionsAsJSON.getString(FIELDS_NAMES[1]);
				String isDebugging = mainOptionsAsJSON.getString(FIELDS_NAMES[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new MainOptions();*/
			return null;
		}
	
		private class CheckBoxListener implements ActionListener {
			private PlayersPanel playersPanel;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method student
				JCheckBox debugCheckBox = (JCheckBox) e.getSource();
				Options optionsReference = Options.reference;
				JFrame mainFrame = getMainFrame();
				
				if (debugCheckBox.isSelected()) {
					int playerNum = Integer.valueOf(getPlayerNumLabel().getText());
					int playerMoney = Integer.valueOf(getMoneyLabel().getText().substring(1, getMoneyLabel().getText().length()));
					PlayersPanel playersPanel = new PlayersPanel(playerNum, playerMoney);
					this.playersPanel = playersPanel;
					optionsReference.add(playersPanel, BorderLayout.CENTER);
				} else {
					optionsReference.remove(this.playersPanel);
				}
				
				mainFrame.pack();
				mainFrame.revalidate();
				mainFrame.repaint();
			}
			
			public JFrame getMainFrame() {
				Container mainFrame = getParent();
				
				while (!(mainFrame instanceof JFrame)) {
					mainFrame = mainFrame.getParent();
				}
				
				return (JFrame) mainFrame;
			}
		}
		
		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				JLabel moneyLabel = getMoneyLabel();
				String moneyAsText = moneyLabel.getText();
				moneyAsText = moneyAsText.substring(1, moneyAsText.length());
				int money = Integer.valueOf(moneyAsText);
				
				if (button.equals(getIncreaseBy100())) {
					money = money + 100;
				} else {
					if (money != 0) {
						money = money - 100;
					}
				}
				
				moneyLabel.setText("$" + money);
			}
		}
		
		public JLabel getPnLabel() {
			return pnLabel;
		}

		public void setPnLabel(JLabel pnLabel) {
			this.pnLabel = pnLabel;
		}

		public JScrollBar getPlayerNumScrollBar() {
			return playerNumScrollBar;
		}

		public void setPlayerNumScrollBar(JScrollBar playerNumScrollBar) {
			this.playerNumScrollBar = playerNumScrollBar;
		}

		public JLabel getPlayerNumLabel() {
			return playerNumLabel;
		}

		public void setPlayerNumLabel(JLabel playerNumLabel) {
			this.playerNumLabel = playerNumLabel;
		}
		
		public JLabel getMnyLabel() {
			return mnyLabel;
		}

		public void setMnyLabel(JLabel mnyLabel) {
			this.mnyLabel = mnyLabel;
		}

		public JLabel getMoneyLabel() {
			return moneyLabel;
		}

		public void setMoneyLabel(JLabel moneyLabel) {
			this.moneyLabel = moneyLabel;
		}

		public JButton getIncreaseBy100() {
			return increaseBy100;
		}

		public void setIncreaseBy100(JButton increaseBy100) {
			this.increaseBy100 = increaseBy100;
		}

		public JButton getDecreaseBy100() {
			return decreaseBy100;
		}

		public void setDecreaseBy100(JButton decreaseBy100) {
			this.decreaseBy100 = decreaseBy100;
		}

		public JCheckBox getDebugCheckBox() {
			return debugCheckBox;
		}

		public void setDebugCheckBox(JCheckBox debugCheckBox) {
			this.debugCheckBox = debugCheckBox;
		}
		
		private class ScrollBarListener implements AdjustmentListener {
			public ScrollBarListener() {
				
			}
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				int value = e.getValue();
				JScrollBar playerNumScrollBar = getPlayerNumScrollBar();
				JLabel playerNumLabel = getPlayerNumLabel();
				
				if (value == playerNumScrollBar.getMaximum()) {
					value = value - 10;
				}
				
				playerNumLabel.setText(String.valueOf(value / 10));
			}
		}
	}
	
	private class PlayersPanel extends JPanel {
		private PlayerPanel selectedPlayerPanel;
		private SelectablesPanel selectablesPanel;
		private ButtonsPanel buttonsPanel;
		
		public PlayersPanel(int playerNum, int playerMoney) {
			super();
			setLayout(new BorderLayout());
			setSelectablesPanel(new SelectablesPanel());
			setButtonsPanel(new ButtonsPanel());
			
			PlayerPanel[] playerPanels = new PlayerPanel[playerNum];
			
			for (int i = 0; i < playerNum; i++) {
				playerPanels[i] = new PlayerPanel("Player" + i, playerMoney);
			}
			
			Container playerPanelsContainer = ComponentBuilder.composeDummyContainer(playerPanels, new GridLayout(8, 1), null);
			setSelectedPlayerPanel((PlayerPanel) playerPanelsContainer.getComponent(0));
			add(playerPanelsContainer, BorderLayout.CENTER);
			add(getButtonsPanel(), BorderLayout.EAST);
			add(getSelectablesPanel(), BorderLayout.WEST);
		}
		
		public void setButtonsPanel(ButtonsPanel buttonsPanel) {
			this.buttonsPanel = buttonsPanel;
		}
		
		public ButtonsPanel getButtonsPanel() {
			return buttonsPanel;
		}
		
		public class ButtonsPanel extends JPanel {
			private String[] buttonsTexts = new String[]{"Add Square", "Remove Square", "Add Card", "Remove Card", "Add Stock", "Remove Stock", "Build", "Demolish"};
			
			public ButtonsPanel() {
				super();
				setLayout(new GridLayout(3, 2));
				ButtonListener buttonListener = new ButtonListener();
				String[] buttonsTexts = getButtonsTexts();
				int size = buttonsTexts.length;
				
				for (int i = 0; i < size; i++) {
					add(ComponentBuilder.composeDefaultButton(buttonsTexts[i], 0, 0, buttonListener, true));
				}
			}
			
			private class ButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					JButton button = (JButton) e.getSource();
					String buttonText = button.getText();
					PlayerPanel selectedPlayerPanel = getSelectedPlayerPanel();
					SelectablesPanel selectablesPanel = getSelectablesPanel();
					
					if (buttonText.equals("Add Square")) {
						String square = selectablesPanel.removeSelectedSquare();
						
						if (square != null && !square.equals("")) {
							selectedPlayerPanel.addSquare(square);
						}
					} else if (buttonText.equals("Remove Square")) {
						String square = selectedPlayerPanel.removeSelectedSquare();
						
						if (square != null && !square.equals("")) {
							selectablesPanel.addSquare(square);	
						}
					} else if (buttonText.equals("Add Card")) {
						String card = selectablesPanel.removeSelectedCard();
						
						if (card != null && !card.equals("")) {
							selectedPlayerPanel.addCard(card);
						}
					} else if (buttonText.equals("Remove Card")) {
						String card = selectedPlayerPanel.removeSelectedCard();
						
						if (card != null && !card.equals("")) {
							selectablesPanel.addCard(card);
						}
					} else if (buttonText.equals("Add Stock")) {
						if (selectedPlayerPanel.getStocks().getItemCount() != 6) {
							String stock = selectablesPanel.removeSelectedStock();
							
							if (stock != null && !stock.equals("")) {
								selectedPlayerPanel.addStock(stock);
							}
						}
					} else if (buttonText.equals("Remove Stock")) {
						String stock = selectedPlayerPanel.removeSelectedStock();
						
						if (stock != null && !stock.equals("")) {
							selectablesPanel.addStock(stock);
						}
					} else if (buttonText.equals("Build")) {
						selectedPlayerPanel.buildOnSelectedSquare();
					} else if (buttonText.equals("Demolish")) {
						selectedPlayerPanel.demolishOnSelectedSquare();
					} else if (buttonText.equals("ToString")) {
						selectedPlayerPanel.toJSON();
					}
				}
			}
			
			public void setButtonsTexts(String[] buttonsTexts) {
				this.buttonsTexts = buttonsTexts;
			}
			
			public String[] getButtonsTexts() {
				return buttonsTexts;
			}
		}
		
		public SelectablesPanel getSelectablesPanel() {
			return selectablesPanel;
		}

		public void setSelectablesPanel(SelectablesPanel selectablesPanel) {
			this.selectablesPanel = selectablesPanel;
		}
		
		private class SelectablesPanel extends JPanel {
			private SteppedComboBox squaresComboBox;
			private SteppedComboBox cardsComboBox;
			private SteppedComboBox stocksComboBox;
			
			public SelectablesPanel() {
				super();
				setLayout(new GridLayout(3, 1));
				
				String[] buyableSquaresNames = findBuyableSquaresNames();
				setSquaresComboBox(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 
						200, buyableSquaresNames, null, null));
				add(getSquaresComboBox());
				
				
				setCardsComboBox(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 500, findPickables(), null, null));
				add(getCardsComboBox());
				
				setStocksComboBox(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 200, findOptions("stocks.txt", "name"), null, null));
				add(getStocksComboBox());
			}
			
			private String[] findPickables() {
				String[] pickables;
				ArrayList<String> pickableList = new ArrayList<String>();
				
				String[] chanceCards = findOptions("chance.txt", "content");
				String[] communityCards = findOptions("community.txt", "content");
				int length = chanceCards.length;
				
				for (int i = 0; i < length; i++) {
					if (chanceCards[i].contains(".nim")) {
						pickableList.add(chanceCards[i]);
					}
				}
				
				length = communityCards.length;
				
				for (int i = 0; i < length; i++) {
					if (communityCards[i].contains(".nim")) {
						pickableList.add(communityCards[i]);
					}
				}
				
				length = pickableList.size();
				pickables = new String[length];
				
				for (int i = 0; i < length; i++) {
					pickables[i] = pickableList.get(i);
				}
				
				return pickables;
			}
			
			private String[] findOptions(String fileName, String searchKey) {
				String[] options;
				
				ArrayList<JSONObject> elementsAsJSON = Reader.read(fileName);
				options = new String[elementsAsJSON.size()];
				int i = 0;
				
				try {
					for (JSONObject elementAsJSON : elementsAsJSON) {
						options[i] = elementAsJSON.getString(searchKey);
						i++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return options;
			}
			
			public void setStocksComboBox(SteppedComboBox stocksComboBox) {
				this.stocksComboBox = stocksComboBox;
			}
			
			public SteppedComboBox getStocksComboBox() {
				return stocksComboBox;
			}
			
			public String removeSelectedSquare() {
				String removal = (String) getSquaresComboBox().getSelectedItem();
				getSquaresComboBox().removeItem(removal);
				return removal;
			}
			
			public String removeSelectedCard() {
				String removal = (String) getCardsComboBox().getSelectedItem();
				getCardsComboBox().removeItem(removal);
				return removal;
			}
			
			public String removeSelectedStock() {
				String removal = (String) getStocksComboBox().getSelectedItem();
				getStocksComboBox().removeItem(removal);
				return removal;
			}
			
			public void addSquare(String square) {
				getSquaresComboBox().addItem(square);
			}
			
			public void addCard(String card) {
				getCardsComboBox().addItem(card);
			}
			
			public void addStock(String stock) {
				getStocksComboBox().addItem(stock);
			}
			
			public void setSquaresComboBox(SteppedComboBox squaresComboBox) {
				this.squaresComboBox = squaresComboBox;
			}
			
			public JComboBox getSquaresComboBox() {
				return squaresComboBox;
			}
			
			public void setCardsComboBox(SteppedComboBox cardsComboBox) {
				this.cardsComboBox = cardsComboBox;
			}
			
			public JComboBox getCardsComboBox() {
				return cardsComboBox;
			}
			
			public String[] findBuyableSquaresNames() {
				String[] buyableSquaresNames = null;
				Reader reader = new Reader();
				
				try {
					ArrayList<JSONObject> outerSquares = reader.read("outerSquares.txt");
					ArrayList<JSONObject> middleSquares = reader.read("middleSquares.txt");
					ArrayList<JSONObject> innerSquares = reader.read("innerSquares.txt");
					
					ArrayList<String> temp = new ArrayList<String>();
					extractBuyableSquares(outerSquares, temp);
					extractBuyableSquares(middleSquares, temp);
					extractBuyableSquares(innerSquares, temp);
					
					buyableSquaresNames = new String[temp.size()];
					
					for (int i = 0; i < temp.size(); i++) {
						buyableSquaresNames[i] = temp.get(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return buyableSquaresNames;
			}
			
			public void extractBuyableSquares(ArrayList<JSONObject> squaresAsJSON, ArrayList<String> temp) throws JSONException {
				for (int i = 0; i < squaresAsJSON.size(); i++) {
					JSONObject squareAsJSON = squaresAsJSON.get(i);
					String name = squareAsJSON.getString("name");
					String type = squareAsJSON.getString("type");
					
					if (type.equals("ColorSquare") || type.equals("RailRoadSquare")
							|| type.equals("CabSquare") || type.equals("UtilitySquare")) {
						temp.add(name);
					}
				}
			}
		}
		
		public class PlayerPanel extends JPanel implements MouseListener {
			private final String[] FIELDS_NAMES = new String[]{"name", "money", "currentLocation", "squares", "cards", "stocks", "die1Value", "die2Value", "speedDieValue"};
			public final Color COLOR_SELECTED = Color.GREEN;
			public final Color COLOR_UNSELECTED = getBackground();
			private JLabel nameLabel;
			private JTextField money;
			private JTextField currentLocation;
			private SteppedComboBox squares;
			private SteppedComboBox cards;
			private SteppedComboBox stocks;
			private JComboBox die1Value;
			private JComboBox die2Value;
			private JComboBox speedDieValue;
			
			public PlayerPanel(String name, int money) {
				super();
				if (name.contains("0")) {
					setBackground(COLOR_SELECTED);
				} else {
					setBackground(COLOR_UNSELECTED);
				}
				
				addMouseListener(this);
				setBorder(BorderFactory.createLineBorder(Color.black));
				initializeChildren(name, String.valueOf(money), "Go", new String[]{}, new String[]{}, new String[]{}, "1", "1", "1");
				addChildren();
			}
			
			public PlayerPanel(String name, String money, String currentLocation, String[] squares, String[] cards, String[] stocks, String die1Value, String die2Value, String speedDieValue) {
				super();
				if (name.contains("0")) {
					setBackground(COLOR_SELECTED);
				} else {
					setBackground(COLOR_UNSELECTED);
				}
				
				addMouseListener(this);
				setBorder(BorderFactory.createLineBorder(Color.black));
				initializeChildren(name, money, currentLocation, squares, cards, stocks, die1Value, die2Value, speedDieValue);
				addChildren();
			}
			
			private void initializeChildren(String name, String money, String currentLocation, String[] squares, String[] cards, String[] stocks, String die1Value, String die2Value, String speedDieValue) {
				setNameLabel(ComponentBuilder.composeDefaultLabel(name));
				setMoney(ComponentBuilder.composeDefaultTextField(50, 20, String.valueOf(money), null));
				setCurrentLocation(ComponentBuilder.composeDefaultTextField(200, 20, currentLocation, null));
				setSquares(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 200, squares, null, null));
				setCards(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 400, cards, null, null));
				setStocks(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 200, stocks, null, null));
				setDie1Value(ComponentBuilder.composeDefaultComboBox(50, 20, new String[]{"1", "2", "3", "4", "5", "6"}, die1Value, null));
				setDie2Value(ComponentBuilder.composeDefaultComboBox(50, 20, new String[]{"1", "2", "3", "4", "5", "6"}, die2Value, null));
				setSpeedDieValue(ComponentBuilder.composeDefaultSteppedComboBox(50, 20, 75, new String[]{"1", "2", "3", "Bus", "Bus", "Mr.Monopoly"}, speedDieValue, null));
			}
			
			private void addChildren() {
				JComponent[] theOnesInSouth = new JComponent[]{getNameLabel(), getMoney(), getCurrentLocation()};
				JComponent[] theOnesInCenter = new JComponent[]{getSquares(), getCards(), getStocks()};
				JComponent[] theOnesInNorth = new JComponent[]{getDie1Value(), getDie2Value(), getSpeedDieValue()};
				add(ComponentBuilder.composeDummyContainer(theOnesInSouth, null, null), BorderLayout.SOUTH);
				add(ComponentBuilder.composeDummyContainer(theOnesInCenter, null, null), BorderLayout.CENTER);
				add(ComponentBuilder.composeDummyContainer(theOnesInNorth, null, null), BorderLayout.NORTH);
			}
			
			public void addSquare(String square) {
				getSquares().addItem(square);
			}
			
			public void addCard(String card) {
				getCards().addItem(card);
			}
			
			public void addStock(String stock) {
				getStocks().addItem(stock);
			}
			
			public String removeSelectedSquare() {
				String removal = null;
				SteppedComboBox squares = getSquares();
				
				if (squares.getItemCount() != 0) {
					removal = (String) squares.getSelectedItem();
					squares.removeItem(removal);
					
					if (removal.contains(",")) {
						removal = removal.substring(0, removal.length() - 2);
					}
				}
				
				return removal;
			}
			
			public String removeSelectedCard() {
				String removal = null;
				SteppedComboBox cards = getCards();
				
				if (cards.getItemCount() != 0) {
					removal = (String) cards.getSelectedItem();
					cards.removeItem(removal);
				}
				
				return removal;
			}
			
			public String removeSelectedStock() {
				String removal = null;
				SteppedComboBox stocks = getStocks();
				
				if (stocks.getItemCount() != 0) {
					removal = (String) stocks.getSelectedItem();
					stocks.removeItem(removal);
				}
				
				return removal;
			}
			
			public void buildOnSelectedSquare() {
				String selectedSquare = getSquares().getSelectedItem().toString();
				getSquares().removeItem(selectedSquare);
				
				if (selectedSquare.contains("RAILROAD") || selectedSquare.contains("SHORT LINE")) {
					if (!selectedSquare.contains(",")) {
						selectedSquare += ",1";
					}
				} else if (selectedSquare.contains(" CAB")) {
					
				} else if (selectedSquare.contains("COMPANY")) {
					if (selectedSquare.contains(",")) {
						int length = selectedSquare.length();
						String baseName = selectedSquare.substring(0, length - 2);
						int stockNum = Integer.valueOf("" + selectedSquare.charAt(length - 1));
						
						if (stockNum != 5) {
							stockNum = stockNum + 1;
						}
						
						selectedSquare = baseName + "," + stockNum;
					} else {
						selectedSquare += ",1";
					}
				} else {
					if (!selectedSquare.contains(",")) {
						selectedSquare += ",1";
					} else {
						String baseName = selectedSquare.substring(0, selectedSquare.length() - 2);
						int buildingNum = Integer.valueOf("" + selectedSquare.charAt(selectedSquare.length() - 1));
						
						if (buildingNum != 6) {
							buildingNum = buildingNum + 1;
						}
						
						selectedSquare = baseName + "," + buildingNum;
					}
				}
				
				getSquares().addItem(selectedSquare);
			}
			
			public void demolishOnSelectedSquare() {
				String selectedSquare = getSquares().getSelectedItem().toString();
				getSquares().removeItem(selectedSquare);
				
				if (selectedSquare.contains("RAILROAD") || selectedSquare.contains("SHORT LINE")) {
					if (selectedSquare.contains(",")) {
						selectedSquare = selectedSquare.substring(0, selectedSquare.length() - 2);
					}
				} else if (selectedSquare.contains(" CAB")) {
					
				} else if (selectedSquare.contains("COMPANY")){
					if (selectedSquare.contains(",")) {
						int length = selectedSquare.length();
						String baseName = selectedSquare.substring(0, length - 2);
						int stockNum = Integer.valueOf("" + selectedSquare.charAt(length - 1));
						
						if (stockNum == 1) {
							selectedSquare = baseName;
						} else {
							stockNum = stockNum - 1;
							selectedSquare = baseName + "," + stockNum;
						}
					}
				} else {
					if (selectedSquare.contains(",")) {
						String baseName = selectedSquare.substring(0, selectedSquare.length() - 2);
						int buildingNum = Integer.valueOf("" + selectedSquare.charAt(selectedSquare.length() - 1));
						
						if (buildingNum == 1) {
							selectedSquare = baseName;
						} else {
							buildingNum = buildingNum - 1;
							selectedSquare = baseName + "," + buildingNum;
						}
					}
				}
				
				getSquares().addItem(selectedSquare);
			}
			
			public void setCurrentLocation(JTextField currentLocation) {
				this.currentLocation = currentLocation;
			}
			
			public JTextField getCurrentLocation() {
				return currentLocation;
			}

			public JLabel getNameLabel() {
				return nameLabel;
			}

			public void setNameLabel(JLabel nameLabel) {
				this.nameLabel = nameLabel;
			}

			public JTextField getMoney() {
				return money;
			}
			
			public void setStocks(SteppedComboBox stocks) {
				this.stocks = stocks;
			}
			
			public SteppedComboBox getStocks() {
				return stocks;
			}

			public void setMoney(JTextField money) {
				this.money = money;
			}

			public SteppedComboBox getSquares() {
				return squares;
			}

			public void setSquares(SteppedComboBox squares) {
				this.squares = squares;
			}

			public JComboBox getDie1Value() {
				return die1Value;
			}

			public void setDie1Value(JComboBox die1Value) {
				this.die1Value = die1Value;
			}

			public JComboBox getDie2Value() {
				return die2Value;
			}

			public void setDie2Value(JComboBox die2Value) {
				this.die2Value = die2Value;
			}

			public JComboBox getSpeedDieValue() {
				return speedDieValue;
			}

			public void setSpeedDieValue(JComboBox speedDieValue) {
				this.speedDieValue = speedDieValue;
			}

			public SteppedComboBox getCards() {
				return cards;
			}

			public void setCards(SteppedComboBox cards) {
				this.cards = cards;
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			public PlayersPanel getPlayersPanel() {
				Container parent = getParent();
				
				while (!(parent instanceof PlayersPanel)) {
					parent = parent.getParent();
				}
				
				return (PlayersPanel) parent;
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				PlayersPanel playersPanel = getPlayersPanel();
				playersPanel.setSelectedPlayerPanel(this);
				JPanel playerPanelsContainer = (JPanel) playersPanel.getComponent(0);
				int playerPanelsNum = playerPanelsContainer.getComponentCount();
				
				for (int i = 0; i < playerPanelsNum; i++) {
					playerPanelsContainer.getComponent(i).setBackground(COLOR_UNSELECTED);
				}
				
				playersPanel.getSelectedPlayerPanel().setBackground(COLOR_SELECTED);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			/*public PlayerPanel fromJSON(JSONObject playerInfoAsJSON) {
				PlayerPanel playerPanel = null;
				class Helper {
					public String[] decomposeAppendedString(String appendedString) {
						ArrayList<String> squares = new ArrayList<String>();
						String temp = "";
						int length = appendedString.length();
						
						for (int i = 0; i < length; i++) {
							char charAtI = appendedString.charAt(i);
							
							if (charAtI == ':') {
								squares.add(temp);
								temp = "";
							} else {
								temp += charAtI;
							}
						}
						
						squares.add(temp);
						return (String[]) squares.toArray();
					}
				}
				
				try {
					Helper helper = new Helper();
					String name = playerInfoAsJSON.getString(FIELDS_NAMES[0]);
					String money = playerInfoAsJSON.getString(FIELDS_NAMES[1]);
					String currentLocation = playerInfoAsJSON.getString(FIELDS_NAMES[2]);
					String[] squares = helper.decomposeAppendedString(playerInfoAsJSON.getString(FIELDS_NAMES[3]));
					String[] cards = helper.decomposeAppendedString(playerInfoAsJSON.getString(FIELDS_NAMES[4]));
					String die1Value = playerInfoAsJSON.getString(FIELDS_NAMES[5]);
					String die2Value = playerInfoAsJSON.getString(FIELDS_NAMES[6]);
					String speedDieValue = playerInfoAsJSON.getString(FIELDS_NAMES[7]);
					playerPanel = new PlayerPanel(name, money, currentLocation,
							squares, cards, die1Value, die2Value, speedDieValue);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
				return playerPanel;
			}*/
			
			public JSONObject toJSON() {
				class Helper {
					public String composeAppendedString(JComboBox comboBox, String appendWith) {
						String squaresAppended = "";
						int size = comboBox.getItemCount();
						
						for (int i = 0; i < size; i++) {
							squaresAppended += comboBox.getItemAt(i) + appendWith;
						}
						
						if (!squaresAppended.equals("")) {
							squaresAppended = squaresAppended.substring(0, squaresAppended.length() - appendWith.length());
						}
						
						return squaresAppended;
					}
				}
				
				Helper helper = new Helper();
				JSONObject playerInfoAsJSON = new JSONObject();
				
				String name = getNameLabel().getText();
				String money = getMoney().getText();
				
				if (money.contains("$")) {
					money = money.substring(1, money.length());
				}
				
				String currentLocation = getCurrentLocation().getText();
				String squaresAppended = helper.composeAppendedString(getSquares(), ":");
				String cardsAppended = helper.composeAppendedString(getCards(), ":");
				String stocksAppended = helper.composeAppendedString(getStocks(), ":");
				String die1Value = getDie1Value().getSelectedItem().toString();
				String die2Value = getDie2Value().getSelectedItem().toString();
				String speedDieValue = getSpeedDieValue().getSelectedItem().toString();
				
				if (speedDieValue.equals("Mr.Monopoly")) {
					speedDieValue = "6";
				} else if (speedDieValue.equals("Bus")) {
					speedDieValue = "5";
				}
				
				try {
					playerInfoAsJSON.put(FIELDS_NAMES[0], name);
					playerInfoAsJSON.put(FIELDS_NAMES[1], money);
					playerInfoAsJSON.put(FIELDS_NAMES[2], currentLocation);
					playerInfoAsJSON.put(FIELDS_NAMES[3], squaresAppended);
					playerInfoAsJSON.put(FIELDS_NAMES[4], cardsAppended);
					playerInfoAsJSON.put(FIELDS_NAMES[5], stocksAppended);
					playerInfoAsJSON.put(FIELDS_NAMES[6], die1Value);
					playerInfoAsJSON.put(FIELDS_NAMES[7], die2Value);
					playerInfoAsJSON.put(FIELDS_NAMES[8], speedDieValue);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(playerInfoAsJSON.toString());
				return playerInfoAsJSON;
			}
		}
		
		public void setSelectedPlayerPanel(PlayerPanel selectedPlayerPanel) {
			this.selectedPlayerPanel = selectedPlayerPanel;
		}
		
		public PlayerPanel getSelectedPlayerPanel() {
			return selectedPlayerPanel;
		}
	}
	
	public void setMainFrame(MonopolyGame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public MonopolyGame getMainFrame() {
		return mainFrame;
	}
}