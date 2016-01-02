package domain;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.JSONObject;

public class Writer {
	public Writer() {
		
	}
	
	public void write(String fileName, ArrayList<JSONObject> playersInfosAsJSON) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			int size = playersInfosAsJSON.size();
			
			for (int i = 0; i < size; i++) {
				writer.write(playersInfosAsJSON.get(i).toString());
				writer.newLine();
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeGameData(String fileName, JSONObject gameAsJSON) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(gameAsJSON.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}