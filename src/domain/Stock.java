 package domain;

import org.json.JSONObject;

public class Stock {
	private static final String[] COMPANY_NAMES = new String[] {"Motion Pictures", "Allied Steamships", "National Utilities", "General Radio", "United Railways", "Acme Motors"};
	private static final String[] FIELD_NAMES = new String[] {"name", "parValue", "loanValue", "firstDivident", "isMortgaged"};
			
	private String name;
	private int parValue;
	private int loanValue;
	private int firstDivident;
	private boolean isMortgaged;
	private Player owner;
	
	public Stock() {
		this.name = null;
		this.parValue = 0;
		this.loanValue = 0;
		this.firstDivident = 0;
		this.isMortgaged = false;
		this.owner = null;
	}
	
	public Stock(String name, int parValue, int loanValue, int firstDivident, boolean isMortgaged) {
		this.name = name;
		this.parValue = parValue;
		this.loanValue = loanValue;
		this.firstDivident = firstDivident;
		this.isMortgaged = isMortgaged;
		this.owner = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParValue() {
		return parValue;
	}

	public void setParValue(int parValue) {
		this.parValue = parValue;
	}

	public int getLoanValue() {
		return loanValue;
	}

	public void setLoanValue(int loanValue) {
		this.loanValue = loanValue;
	}

	public int getFirstDivident() {
		return firstDivident;
	}

	public void setFirstDivident(int firstDivident) {
		this.firstDivident = firstDivident;
	}

	public boolean isMortgaged() {
		return isMortgaged;
	}

	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}

	public static String[] getCompanyNames() {
		return COMPANY_NAMES;
	}

	public static String[] getFieldNames() {
		return FIELD_NAMES;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}

	public static Stock fromJSON(JSONObject stockAsJSON) {
		Stock stock = null;
		
		try {
			String name = stockAsJSON.getString(FIELD_NAMES[0]);
			int parValue = stockAsJSON.getInt(FIELD_NAMES[1]);
			int loanValue = stockAsJSON.getInt(FIELD_NAMES[2]);
			int firstDivident = stockAsJSON.getInt(FIELD_NAMES[3]);
			boolean isMortgaged = stockAsJSON.getBoolean(FIELD_NAMES[4]);
			stock = new Stock(name, parValue, loanValue, firstDivident, isMortgaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stock;
	}
	
	/*public String toString() {
		String content = "{";
		content += FIELD_NAMES[0] + ":" + getName() + ",";
		content += FIELD_NAMES[1] + ":" + getParValue() + ",";
		content += FIELD_NAMES[2] + ":" + getLoanValue() + ",";
		content += FIELD_NAMES[3] + ":" + getFirstDivident() + ",";
		content += FIELD_NAMES[4] + ":" + isMortgaged();
		content += "}";
		return content;
	}*/
	
	public String toString() {
		return toJSON().toString();
	}
	
	public JSONObject toJSON() {
		JSONObject stockAsJSON = null;
		
		try {
			stockAsJSON = new JSONObject();
			stockAsJSON.put(FIELD_NAMES[0], getName());
			stockAsJSON.put(FIELD_NAMES[1], getParValue());
			stockAsJSON.put(FIELD_NAMES[2], getLoanValue());
			stockAsJSON.put(FIELD_NAMES[3], getFirstDivident());
			stockAsJSON.put(FIELD_NAMES[4], isMortgaged());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stockAsJSON;
	}
}