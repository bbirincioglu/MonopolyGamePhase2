package gui;
import javax.swing.ImageIcon;

import domain.Die;


public class SDieView extends DieView {
	private static final String[] FILE_NAMES = new String[] {"one.png", "two.png", "three.png", "bus.png", "bus.png", "mrMonopoly.png"};
	
	public SDieView(Die die) {
		super(FILE_NAMES, die);
	}
	
	@Override
	public void update(int faceValue) {
		// TODO Auto-generated method stub
		setIcon(new ImageIcon(getImages()[faceValue - 1]));
	}
}