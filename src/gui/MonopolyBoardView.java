package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Bank;
import domain.BankObserver;
import domain.BuyableSquare;
import domain.ColorSquare;
import domain.GameController;
import domain.GameOptions;
import domain.MonopolyBoard;
import domain.Player;
import domain.RailRoadSquare;
import domain.Reader;
import domain.Square;
import domain.SquareObserver;


public class MonopolyBoardView extends JPanel {
	private static final String[] LOCATIONS = {BorderLayout.SOUTH, BorderLayout.WEST, BorderLayout.NORTH, BorderLayout.EAST};
	private static final int ROW_NUM = 15;
	private static final int COLUMN_NUM = 15;
	private PoolView poolView;
	private ArrayList<SquareView> outerSquareViews;
	private ArrayList<SquareView> middleSquareViews;
	private ArrayList<SquareView> innerSquareViews;
	private GridBagConstraints constraints;
	
	public MonopolyBoardView(ArrayList<Player> players, MonopolyBoard monopolyBoard) {
		super();
		setLayout(new GridBagLayout());
		setConstraints(composeConstraints());
		initializeChildren(monopolyBoard.getOuterSquares(), monopolyBoard.getMiddleSquares(), monopolyBoard.getInnerSquares(), monopolyBoard.getBank());
		addChildren();
		initializeAndAddPieceViews(players);
	}
	
	private void initializeAndAddPieceViews(ArrayList<Player> players) {
		MonopolyBoard monopolyBoard = GameController.getInstance().getMonopolyBoard();
		Reader reader = new Reader();
		GameOptions options = GameOptions.fromJSON(reader.read("options.txt").get(0));
		
		if (options.isDebugging()) {
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				PieceView pieceView = new PieceView(player.getPiece());
				player.getPiece().notifyPieceObservers();
			}
		} else {
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				PieceView pieceView = new PieceView(player.getPiece());
				player.moveImmediate(monopolyBoard.getSquare("Go"));
			}
		}
	}
	
	private GridBagConstraints composeConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		return constraints;
	}
	
	private void addChildren() {
		ArrayList<SquareView> outerSquareViews = getOuterSquareViews();
		ArrayList<SquareView> middleSquareViews = getMiddleSquareViews();
		ArrayList<SquareView> innerSquareViews = getInnerSquareViews();
		
		int outerY = outerSquareViews.size() / 4;
		int outerX = outerY;
		int outerIndex = 0;
		int outerSeperator = outerY;
		
		int middleY = outerY - 2;
		int middleX = middleY;
		int middleIndex = 0;
		int middleSeperator = 10;
		
		int innerY = middleY - 2;
		int innerX = innerY;
		int innerIndex = 0;
		int innerSeperator = 6;
		
		for (int i = ROW_NUM - 1; i >= 0; i--) {
			for (int j = COLUMN_NUM - 1; j >= 0; j--) {
				SquareView squareView;
				GridBagConstraints constraints = new GridBagConstraints();
				
				if (i == 0 || i == ROW_NUM - 1 || j == 0 || j == COLUMN_NUM - 1) {
					squareView = outerSquareViews.get(outerIndex);
					constraints.gridx = outerX;
					constraints.gridy = outerY;
					if (0 <= outerIndex && outerIndex < outerSeperator) {
						outerX--;
					} else if (outerSeperator <= outerIndex && outerIndex < outerSeperator * 2) {
						outerY--;
					} else if (outerSeperator * 2 <= outerIndex && outerIndex < outerSeperator * 3) {
						outerX++;
					} else if (outerSeperator * 3 <= outerIndex && outerIndex < outerSeperator * 4) {
						outerY++;
					}
					
					outerIndex++;
					add(squareView, constraints);
				} else if (i == 1 || i == ROW_NUM - 2 || j == 1 || j == COLUMN_NUM - 2) {
					JPanel emptyPanel = new JPanel();
					emptyPanel.setBackground(Color.GRAY);
					emptyPanel.setPreferredSize(new Dimension(100, 50));
					constraints.gridx = j;
					constraints.gridy = i;
					add(emptyPanel, constraints);
				} else if (i == 2 || i == ROW_NUM - 3 || j == 2 || j == COLUMN_NUM - 3) {
					squareView = middleSquareViews.get(middleIndex);
					constraints.gridx = middleX;
					constraints.gridy = middleY;
					if (0 <= middleIndex && middleIndex < middleSeperator) {
						middleX--;
					} else if (middleSeperator <= middleIndex && middleIndex < middleSeperator * 2) {
						middleY--;
					} else if (middleSeperator * 2 <= middleIndex && middleIndex < middleSeperator * 3) {
						middleX++;
					} else if (middleSeperator * 3 <= middleIndex && middleIndex < middleSeperator * 4) {
						middleY++;
					}
					
					middleIndex++;
					add(squareView, constraints);
				} else if (i == 3 || i == ROW_NUM - 4 || j == 3 || j == COLUMN_NUM - 4) {
					JPanel emptyPanel = new JPanel();
					emptyPanel.setBackground(Color.GRAY);
					emptyPanel.setPreferredSize(new Dimension(100, 50));
					constraints.gridx = j;
					constraints.gridy = i;
					add(emptyPanel, constraints);
				} else if (i == 4 || i == ROW_NUM - 5 || j == 4 || j == COLUMN_NUM - 5) {
					squareView = innerSquareViews.get(innerIndex);
					constraints.gridx = innerX;
					constraints.gridy = innerY;
					
					if (0 <= innerIndex && innerIndex < innerSeperator) {
						innerX--;
					} else if (innerSeperator <= innerIndex && innerIndex < innerSeperator * 2) {
						innerY--;
					} else if (innerSeperator * 2 <= innerIndex && innerIndex < innerSeperator * 3) {
						innerX++;
					} else if (innerSeperator * 3 <= innerIndex && innerIndex < innerSeperator * 4) {
						innerY++;
					}
					
					innerIndex++;
					add(squareView, constraints);
				} else {
					JPanel emptyPanel = new JPanel();
					emptyPanel.setBackground(Color.GRAY);
					emptyPanel.setPreferredSize(new Dimension(100, 50));
					constraints.gridx = j;
					constraints.gridy = i;
					
					if (i == 7 && j == 7) {
						emptyPanel.add(getPoolView());
					}
					
					add(emptyPanel, constraints);
				}	
			}
		}
		
		
	}
	
	private void initializeChildren(ArrayList<Square> outerSquares, ArrayList<Square> middleSquares, ArrayList<Square> innerSquares, Bank bank) {
		setOuterSquareViews(new ArrayList<SquareView>());
		setMiddleSquareViews(new ArrayList<SquareView>());
		setInnerSquareViews(new ArrayList<SquareView>());
		
		initialize(getOuterSquareViews(), outerSquares);
		initialize(getMiddleSquareViews(), middleSquares);
		initialize(getInnerSquareViews(), innerSquares);
		
		setPoolView(new PoolView(bank));
	}
	
	private void initialize(ArrayList<SquareView> squareViews, ArrayList<Square> squares) {
		int size = squares.size();
		int seperator = size / 4;
		
		for (int i = 0; i < size; i++) {
			Square square = squares.get(i);
			String location = null;
			
			if (0 <= i && i < seperator) {
				location = LOCATIONS[0];
			} else if (seperator <= i && i < seperator * 2) {
				location = LOCATIONS[1];
			} else if (seperator * 2 <= i && i < seperator * 3) {
				location = LOCATIONS[2];
			} else if (seperator * 3 <= i && i < seperator * 4) {
				location = LOCATIONS[3];
			}
			
			SquareView squareView = new SquareView(square, location);
			squareViews.add(squareView);
		}
	}
	
	public ArrayList<SquareView> getOuterSquareViews() {
		return outerSquareViews;
	}

	public void setOuterSquareViews(ArrayList<SquareView> outerSquareViews) {
		this.outerSquareViews = outerSquareViews;
	}

	public ArrayList<SquareView> getMiddleSquareViews() {
		return middleSquareViews;
	}

	public void setMiddleSquareViews(ArrayList<SquareView> middleSquareViews) {
		this.middleSquareViews = middleSquareViews;
	}

	public ArrayList<SquareView> getInnerSquareViews() {
		return innerSquareViews;
	}

	public void setInnerSquareViews(ArrayList<SquareView> innerSquareViews) {
		this.innerSquareViews = innerSquareViews;
	}

	public static String[] getLocations() {
		return LOCATIONS;
	}

	public static int getRowNum() {
		return ROW_NUM;
	}

	public static int getColumnNum() {
		return COLUMN_NUM;
	}
	
	public class PiecePanel extends JPanel {
		public PiecePanel() {
			super();
			setLayout(new GridLayout(2, 4));
		}
		
		public void addPieceView(PieceView pieceView) {
			add(pieceView);
		}
		
		public void removePieceView(PieceView pieceView) {
			remove(pieceView);
		}
	}
	
	public class SquareView extends JPanel implements SquareObserver {
		private static final int WIDTH = 100;
		private static final int HEIGHT = 50;
		private JLabel namePriceLabel;
		private JLabel priceLabel;
		private JLabel colorLabel;
		private PiecePanel piecePanel;
		
		public SquareView(Square square, String location) {
			super();
			setName(square.getName());
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setLayout(new BorderLayout());
			
			square.addSquareObserver(this);
			initializeChildren(square, location);
			addChildren(location);
			
			square.notifySquareObservers();
		}
		
		private void addChildren(String location) {
			if (location.equals(BorderLayout.WEST) && getNamePriceLabel().getText().contains("$")) {
				JPanel nestedContainer = new JPanel();
				nestedContainer.setLayout(new GridLayout(1, 2));
				String[] namePrice = mySplit(getNamePriceLabel().getText());
				getNamePriceLabel().setText(namePrice[0]);
				getPriceLabel().setText("$" + namePrice[1]);
				nestedContainer.add(getNamePriceLabel());
				nestedContainer.add(getPriceLabel());
				add(nestedContainer, location);
			} else if (location.equals(BorderLayout.EAST) && getNamePriceLabel().getText().contains("$")) {
				JPanel nestedContainer = new JPanel();
				nestedContainer.setLayout(new GridLayout(1, 2));
				String[] namePrice = mySplit(getNamePriceLabel().getText());
				getNamePriceLabel().setText(namePrice[0]);
				getPriceLabel().setText("$" + namePrice[1]);
				nestedContainer.add(getPriceLabel());
				nestedContainer.add(getNamePriceLabel());
				add(nestedContainer, location);
			} else {
				add(getNamePriceLabel(), location);
			}
			
			add(getColorLabel(), getOppositeLocation(LOCATIONS, location));
			add(getPiecePanel(), BorderLayout.CENTER);
		}
		
		private String getOppositeLocation(String[] locations, String location) {
			String oppositeLocation = null;
			
			for (int i = 0; i < locations.length; i++) {
				if (locations[i].equals(location)) {
					oppositeLocation = locations[(i + 2) % locations.length];
					break;
				}
			}
			
			return oppositeLocation;
		}
		
		private String[] mySplit(String text) {
			String[] splitArray = new String[2];
			String splitChar = "$";
			String temp = "";
			int index = 0;
			
			for (int i = 0; i < text.length(); i++) {
				String charAtI = text.charAt(i) + "";
				if (charAtI.equals(splitChar)) {
					splitArray[index] = temp;
					temp = "";
					index++;
				} else {
					temp += charAtI;
				}
			}
			
			splitArray[index] = temp;
			return splitArray;
		}
		
		private void initializeChildren(Square square, String location) {
			Font font = new Font("Sans serif", Font.BOLD, 10);
			setNamePriceLabel(new JLabel(removeVowels(square.getName())));
			getNamePriceLabel().setFont(font);
			getNamePriceLabel().setHorizontalAlignment(JLabel.CENTER);
			
			setColorLabel(new JLabel(" "));
			getColorLabel().setOpaque(true);
			getColorLabel().setFont(font);
			getColorLabel().setHorizontalAlignment(JLabel.CENTER);
			
			setPriceLabel(new JLabel());
			getPriceLabel().setFont(font);
			getPriceLabel().setHorizontalAlignment(JLabel.CENTER);
			
			if (square instanceof ColorSquare) {
				String colorAsString = ((ColorSquare) square).getColor();
				getColorLabel().setBackground(Color.decode(colorAsString));
				
				if (colorAsString.equals("#651010") || colorAsString.equals("#581047") || colorAsString.equals("#000000")) {
					getColorLabel().setForeground(Color.WHITE);
				}
			}
			
			if (square instanceof BuyableSquare) {
				getNamePriceLabel().setText(getNamePriceLabel().getText() + " $" + ((BuyableSquare) square).getPrice());
			}
			
			setPiecePanel(new PiecePanel());
			
			if (location.equals(LOCATIONS[1])) {
				getNamePriceLabel().setUI(new VerticalLabelUI(true));
				getPriceLabel().setUI(new VerticalLabelUI(true));
				getColorLabel().setUI(new VerticalLabelUI(true));
			} else if (location.equals(LOCATIONS[3])) {
				getNamePriceLabel().setUI(new VerticalLabelUI(false));
				getPriceLabel().setUI(new VerticalLabelUI(false));
				getColorLabel().setUI(new VerticalLabelUI(false));
			}
		}
		
		private String removeVowels(String squareName) {
			String controller = "AEIOU";
			String vowelsRemoved = "";
			
			for (int i = 0; i < squareName.length(); i++) {
				char charAtI = squareName.charAt(i);
				
				if (!controller.contains("" + charAtI)) {
					vowelsRemoved += charAtI;
				}
			}
			
			return vowelsRemoved;
		}
		
		public PiecePanel getPiecePanel() {
			return piecePanel;
		}

		public void setPiecePanel(PiecePanel piecePanel) {
			this.piecePanel = piecePanel;
		}
		
		public JLabel getNamePriceLabel() {
			return namePriceLabel;
		}

		public void setNamePriceLabel(JLabel namePriceLabel) {
			this.namePriceLabel = namePriceLabel;
		}

		public JLabel getColorLabel() {
			return colorLabel;
		}

		public void setColorLabel(JLabel colorLabel) {
			this.colorLabel = colorLabel;
		}
		
		public JLabel getPriceLabel() {
			return priceLabel;
		}

		public void setPriceLabel(JLabel priceLabel) {
			this.priceLabel = priceLabel;
		}

		public void addPieceView(PieceView pieceView) {
			getPiecePanel().addPieceView(pieceView);
			getPiecePanel().revalidate();
			getPiecePanel().repaint();
		}
		
		public void removePieceView(PieceView pieceView) {
			getPiecePanel().removePieceView(pieceView);
			getPiecePanel().revalidate();
			getPiecePanel().repaint();
		}

		@Override
		public void update(Square square) {
			// TODO Auto-generated method stub
			if (square instanceof ColorSquare) {
				ColorSquare colorSquare = (ColorSquare) square;
				int buildingNum = colorSquare.getBuildingNum();
				
				if (0 <= buildingNum && buildingNum <= 4) {
					getColorLabel().setText(buildingNum + " h");
				} else if (buildingNum == 5){
					getColorLabel().setText((buildingNum - 4) + " H");
				} else {
					getColorLabel().setText((buildingNum - 5) + " S");
				}
			} else if (square instanceof RailRoadSquare) {
				RailRoadSquare railRoadSquare = (RailRoadSquare) square;
				boolean isTrainDepotBuilt = railRoadSquare.isTrainDepotBuilt();
				
				if (isTrainDepotBuilt) {
					getColorLabel().setText("1 TD");
				} else {
					getColorLabel().setText("0 TD");
				}
			}
		}
	}

	public GridBagConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(GridBagConstraints constraints) {
		this.constraints = constraints;
	}
	
	public void setPoolView(PoolView poolView) {
		this.poolView = poolView;
	}
	
	public PoolView getPoolView() {
		return poolView;
	}
	
	public class PoolView extends JButton implements BankObserver {
		public PoolView(Bank bank) {
			super();
			setText("0");
			setEnabled(false);
			setHorizontalAlignment(CENTER);
			bank.addBankObserver(this);
		}
		
		public void update(Bank bank) {
			setText("$" + bank.getPoolMoney());
		}
	}
}