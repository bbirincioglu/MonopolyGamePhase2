package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.BuyableSquare;
import domain.CabSquare;
import domain.ColorSquare;
import domain.ControllerObserver;
import domain.Cup;
import domain.DialogBuilder;
import domain.Die;
import domain.GameController;
import domain.Player;
import domain.PlayerObserver;
import domain.Square;
import domain.Stock;
import domain.UtilitySquare;

public class DownPanel extends JPanel {
	private GridBagConstraints constraints;
	private PlayersPanel playersPanel;
	private CPPanel cpPanel;
	private DicePanel diePanel;
	
	public DownPanel(ArrayList<Player> players) {
		super();
		setConstraints(composeConstraints());
		setLayout(new BorderLayout());
		add(new CPPanel(), BorderLayout.WEST);
		add(new PlayersPanel(players), BorderLayout.CENTER);
		add(new DicePanel(), BorderLayout.EAST);
	}
	
	private GridBagConstraints composeConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		return constraints;
	}

	public GridBagConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(GridBagConstraints constraints) {
		this.constraints = constraints;
	}
	
	public class CPPanel extends JPanel implements ControllerObserver {
		private JLabel cpLabel;
		
		public CPPanel() {
			super();
			setLayout(new GridLayout(2, 1));
			GameController gameController = GameController.getInstance();
			gameController.addObserver(this);
			JLabel constantLabel = new JLabel("Current Player: ");
			constantLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(constantLabel);
			
			setCpLabel(new JLabel(gameController.getPlayers().get(0).getName()));
			add(getCpLabel());
			getCpLabel().setHorizontalAlignment(SwingConstants.CENTER);
			gameController.notifyObservers();
		}

		@Override
		public void update(GameController gameController) {
			// TODO Auto-generated method stub
			String currentPlayerName = gameController.getPlayers().get(gameController.getCurrentPlayerIndex()).getName();
			getCpLabel().setText(currentPlayerName);
		}

		public JLabel getCpLabel() {
			return cpLabel;
		}

		public void setCpLabel(JLabel cpLabel) {
			this.cpLabel = cpLabel;
		}
	}
	
	public class PlayersPanel extends JPanel {
		private static final int ROW_NUM = 2;
		private static final int COLUMN_NUM = 4;
		
		public PlayersPanel(ArrayList<Player> players) {
			super();
			setLayout(new GridLayout(ROW_NUM, COLUMN_NUM));
			
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				PlayerView playerView = new PlayerView(player);
				add(playerView);
			}
		}
	}
	
	public class PlayerView extends JPanel implements PlayerObserver {
		private static final int COMBO_BOX_WIDTH = 20;
		private static final int COMBO_BOX_HEIGHT = 20;
		private JLabel nameLabel;
		private JLabel moneyLabel;
		private ComboBoxPanel comboBoxPanel;
		
		public PlayerView(Player player) {
			super();
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setLayout(new BorderLayout());
			player.addPlayerObserver(this);
			initializeChildren(player.getName(), player.getMoney());
			addChildren();
			
			player.notifyPlayerObservers();
		}
		
		private void initializeChildren(String name, int money) {
			setNameLabel(composeDefaultLabel(name));
			setMoneyLabel(composeDefaultLabel("$" + money));
			setComboBoxPanel(new ComboBoxPanel());
		}
		
		private JLabel composeDefaultLabel(String text) {
			JLabel label = new JLabel();
			label.setText(text);
			label.setFont(new Font("Sans serif", Font.BOLD, 10));
			label.setHorizontalAlignment(JLabel.CENTER);
			return label;
		}
		
		private void addChildren() {
			add(getNameLabel(), BorderLayout.SOUTH);
			add(getMoneyLabel(), BorderLayout.NORTH);
			add(getComboBoxPanel(), BorderLayout.CENTER);
		}

		public JLabel getNameLabel() {
			return nameLabel;
		}

		public void setNameLabel(JLabel nameLabel) {
			this.nameLabel = nameLabel;
		}

		public JLabel getMoneyLabel() {
			return moneyLabel;
		}

		public void setMoneyLabel(JLabel moneyLabel) {
			this.moneyLabel = moneyLabel;
		}
		
		public ComboBoxPanel getComboBoxPanel() {
			return comboBoxPanel;
		}

		public void setComboBoxPanel(ComboBoxPanel comboBoxPanel) {
			this.comboBoxPanel = comboBoxPanel;
		}
		
		private class ComboBoxPanel extends JPanel {
			private static final int ROW_NUM = 2;
			private static final int COLUMN_NUM = 5;
			public final String[] LABELS = {"COLORS", "UTILITIES", "CABS", "RAILROADS", "STOCKS"};
			private ArrayList<SteppedComboBox> comboBoxes;
			
			public ComboBoxPanel() {
				super();
				setLayout(new GridLayout(2, 4));
				setComboBoxes(new ArrayList<SteppedComboBox>());
				
				for (int i = 0; i < ROW_NUM; i++) {
					for (int j = 0; j < COLUMN_NUM; j++) {
						if (i == 0) {
							JLabel label = composeDefaultLabel(LABELS[j]);
							add(label);
						} else {
							SteppedComboBox comboBox = new SteppedComboBox(new String[]{});
							comboBox.addMouseListener(new ComboBoxListener());
							comboBox.setName(LABELS[j]);
							comboBox.setPreferredSize(new Dimension(50, 10));
							comboBox.setPopupWidth(200);
							getComboBoxes().add(comboBox);
							add(comboBox);
						}
					}
				}
			}
			
			public class ComboBoxListener implements MouseListener {
				@Override
				public void mousePressed(MouseEvent event) {
					// TODO Auto-generated method stub
					GameController gameController = GameController.getInstance();
					SteppedComboBox comboBox = (SteppedComboBox) event.getSource();
					String comboBoxName = comboBox.getName();
					Object selectedItem = comboBox.getSelectedItem();
					
					if (selectedItem != null) {
						String name = comboBox.getSelectedItem().toString();
						String squareName;
						String stockName;
						
						if (comboBoxName.equals(getComboBoxPanel().getLABELS()[4])) {
							stockName = name;
							String result = DialogBuilder.stockDialog(stockName);
							DialogBuilder.informativeDialog(result);
							
							if (result.equals("Apply Mortgage")) {
								gameController.doApplyMortgage(stockName, 0);
							} else if (result.equals("Remove Mortgage")) {
								gameController.doRemoveMortgage(stockName, 0);
							} else if (result.equals("Sell")) {
								gameController.doSellStock(stockName);
							}
						} else {
							squareName = name;
							String result = DialogBuilder.squareDialog(comboBoxName);
							
							if (result.equals("Apply Mortgage")) {
								gameController.doApplyMortgage(squareName);
							} else if (result.equals("Remove Mortgage")) {
								gameController.doRemoveMortgage(squareName);
							} else if (result.equals("Buy Building")) {
								gameController.doBuyBuilding(squareName);
							} else if (result.equals("Sell Building")) {
								gameController.doSellBuilding(squareName);
							} else if (result.equals("Buy Train Depot")) {
								gameController.doBuyTrainDepot(squareName);
							} else if (result.equals("Sell Train Depot")) {
								gameController.doSellTrainDepot(squareName);
							} else if (result.equals("Sell")) {
								//gameController.doSellSquare(squareName);
								System.out.println("you have clicked sell.");
							}
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}	
			}
			
			public String[] getLABELS() {
				return LABELS;
			}
			
			public ArrayList<SteppedComboBox> getComboBoxes() {
				return comboBoxes;
			}

			public void setComboBoxes(ArrayList<SteppedComboBox> comboBoxes) {
				this.comboBoxes = comboBoxes;
			}
			
			public SteppedComboBox findComboBoxByName(String name) {
				SteppedComboBox comboBox = null;
				
				for (int i = 0; i < getComboBoxes().size(); i++) {
					if (getComboBoxes().get(i).getName().equals(name)) {
						comboBox = getComboBoxes().get(i);
						break;
					}
				}
				
				return comboBox;
			}
		}

		@Override
		public void update(Player player) {
			// TODO Auto-generated method stub
			int money = player.getMoney();
			ArrayList<BuyableSquare> squares = player.getSquares();
			ArrayList<Stock> stocks = player.getStocks();
			
			JLabel moneyLabel = getMoneyLabel();
			moneyLabel.setText("$" + money);
			
			ComboBoxPanel comboBoxPanel = getComboBoxPanel();
			emptyComboBoxes(comboBoxPanel.getComboBoxes());
			
			for (int i = 0; i < squares.size(); i++) {
				Square square = squares.get(i);
				String squareName = square.getName();
				String comboBoxName;
				
				if (square instanceof ColorSquare) {
					comboBoxName = comboBoxPanel.getLABELS()[0];
				} else if (square instanceof UtilitySquare) {
					comboBoxName = comboBoxPanel.getLABELS()[1];
				} else if (square instanceof CabSquare) {
					comboBoxName = comboBoxPanel.getLABELS()[2];
				} else {
					comboBoxName = comboBoxPanel.getLABELS()[3];
				}
				
				SteppedComboBox comboBox = comboBoxPanel.findComboBoxByName(comboBoxName);
				comboBox.addItem(squareName);
			}
			
			SteppedComboBox comboBox = comboBoxPanel.findComboBoxByName(comboBoxPanel.getLABELS()[4]);
			int size = stocks.size();
			
			for (int i = 0; i < size; i++) {
				Stock stock = stocks.get(i);
				String name = stock.getName();
				comboBox.addItem(name);
			}
		}
		
		private void emptyComboBoxes(ArrayList<SteppedComboBox> comboBoxes) {
			int size = comboBoxes.size();
			
			for (int i = 0; i < size; i++) {
				comboBoxes.get(i).removeAllItems();
			}
		}
	}
	
	private class DicePanel extends JPanel {
		public DicePanel() {
			super();
			setLayout(new GridLayout(2, 2));
			GameController gameController = GameController.getInstance();
			Cup cup = gameController.getCup();
			Die die1 = cup.getDie1();
			Die die2 = cup.getDie2();
			Die speedDie = cup.getSpeedDie();
			
			RDieView rDieView1 = new RDieView(die1);
			RDieView rDieView2 = new RDieView(die2);
			SDieView sDieView = new SDieView(speedDie);
			RollButton rollButton = new RollButton();
			
			add(rDieView1);
			add(rDieView2);
			add(sDieView);
			add(rollButton);
		}
	}
}