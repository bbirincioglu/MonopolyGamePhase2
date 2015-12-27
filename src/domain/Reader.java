package domain;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;


public class Reader {
	public Reader() {
		
	}
	
	public static ArrayList<JSONObject> read(String fileName) {
		ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			
			while (line != null) {
				jsonObjects.add(new JSONObject(line));
				line = reader.readLine();
			}
			
			reader.close();
		} catch (Exception e) {
			
		}
		
		return jsonObjects;
	}
}
