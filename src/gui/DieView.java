package gui;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import domain.Die;
import domain.DieObserver;


public abstract class DieView extends JLabel implements DieObserver {
	private static final String FOLDER_PATH = "images/";
	private BufferedImage[] images;
	
	public DieView(String[] fileNames, Die die) {
		super();
		setHorizontalAlignment(CENTER);
		setImages(composeImages(FOLDER_PATH, fileNames));
		setIcon(new ImageIcon(getImages()[5]));
		die.addDieObserver(this);
	}
	
	public BufferedImage[] composeImages(String folderPath, String[] fileNames) {
		BufferedImage[] images = new BufferedImage[fileNames.length];
		
		for (int i = 0; i < fileNames.length; i++) {
			String fullPath = folderPath + fileNames[i];
			
			try {
				images[i] = ImageIO.read(new File(fullPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return images;
	}
	
	public void setImages(BufferedImage[] images) {
		this.images = images;
	}
	
	public BufferedImage[] getImages() {
		return images;
	}
}