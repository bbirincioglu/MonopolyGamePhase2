package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.BuyableSquare;
import domain.ColorSquare;
import domain.Player;
import domain.PlayerObserver;
import domain.RailRoadSquare;
import domain.Square;
import domain.SquareObserver;

public class SellPropertyToGetOutOfJailDialog extends JDialog {
	private Player currentPlayer;
	
	public SellPropertyToGetOutOfJailDialog(JFrame mainFrame, Player currentPlayer) {
		super(mainFrame, true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setCurrentPlayer(currentPlayer);
		JPanel contentPanel = (JPanel) getContentPane();
		contentPanel.setLayout(new BorderLayout());
		pack();
		setVisible(true);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	private class SquarePanel extends JPanel implements SquareObserver {
		private JLabel buildingsInfoLabel;
		
		public SquarePanel(BuyableSquare square) {
			super();
			setLayout(new GridLayout(5, 1));
			String squareName = square.getName();
			int price = square.getPrice();
			square.addSquareObserver(this);
			JLabel nameLabel = createDefaultLabel(squareName);
			JLabel priceLabel = createDefaultLabel("$" + price);
			add(nameLabel);
			add(priceLabel);
			
			if (square instanceof ColorSquare) {
				ColorSquare colorSquare = (ColorSquare) square;
				nameLabel.setOpaque(true);
				nameLabel.setBackground(Color.decode(colorSquare.getColor()));
				JLabel costsLabel = createDefaultLabel("$" + colorSquare.getHouseCost() + ":$" + colorSquare.getHotelCost() + ":$" + colorSquare.getSkyscraperCost());
				add(costsLabel);
				setBuildingsInfoLabel(createDefaultLabel(colorSquare.getBuildingsInfo()));
				add(getBuildingsInfoLabel());
			} else if (square instanceof RailRoadSquare) {
				RailRoadSquare railRoadSquare = (RailRoadSquare) square;
				JLabel costsLabel = createDefaultLabel("$" + 100);
				add(costsLabel);
				setBuildingsInfoLabel(createDefaultLabel(railRoadSquare.getBuildingsInfo()));
				add(getBuildingsInfoLabel());
			} else {
				
			}
			
			JButton sellButton = new JButton("Sell");
			sellButton.addActionListener(new ButtonListener(square));
			add(sellButton);
		}
		
		private JLabel createDefaultLabel(String text) {
			JLabel defaultLabel = new JLabel(text);
			defaultLabel.setHorizontalAlignment(JLabel.CENTER);
			return defaultLabel;
		}
		
		public JLabel getBuildingsInfoLabel() {
			return buildingsInfoLabel;
		}

		public void setBuildingsInfoLabel(JLabel buildingsInfoLabel) {
			this.buildingsInfoLabel = buildingsInfoLabel;
		}
		
		private class ButtonListener implements ActionListener {
			private BuyableSquare square;
			
			public ButtonListener(BuyableSquare square) {
				this.square = square;
			}
			
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = getCurrentPlayer();
				BuyableSquare square = getSquare();
				
				if (square instanceof ColorSquare) {
					ColorSquare colorSquare = (ColorSquare) square;
					//checker
				} else if (square instanceof RailRoadSquare) {
					
				}
			}
			
			public void setSquare(BuyableSquare square) {
				this.square = square;
			}
			
			public BuyableSquare getSquare() {
				return square;
			}
		}

		@Override
		public void update(Square square) {
			// TODO Auto-generated method stub
			BuyableSquare buyableSquare = (BuyableSquare) square;
			
			if (buyableSquare.getOwner() == null) {
				Container container = getParent();
				container.remove(this);
				container.revalidate();
				container.repaint();
			} else {
				
			}
		}
	}
	
	private class PlayerPanel extends JPanel implements PlayerObserver {
		private JLabel nameLabel;
		private JLabel moneyLabel;
		
		public PlayerPanel() {
			super();
			getCurrentPlayer().addPlayerObserver(this);
			setNameLabel(createDefaultLabel(getCurrentPlayer().getName()));
			setMoneyLabel(createDefaultLabel("$" + getCurrentPlayer().getMoney()));
		}
		
		private JLabel createDefaultLabel(String text) {
			JLabel defaultLabel = new JLabel(text);
			defaultLabel.setHorizontalAlignment(JLabel.CENTER);
			return defaultLabel;
		}
		
		@Override
		public void update(Player player) {
			// TODO Auto-generated method stub
			getMoneyLabel().setText("$" + player.getMoney());
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
	}
}
