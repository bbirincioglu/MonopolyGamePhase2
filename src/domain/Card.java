package domain;
public abstract class Card {
	private String content;
	
	public Card(String content) {
		this.content = content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
