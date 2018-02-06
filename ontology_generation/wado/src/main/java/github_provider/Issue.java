package github_provider;

public class Issue {
	private String text;
	private boolean closed;
	
	public Issue(){}
	public Issue(String text, boolean closed){
		this.text = text;
		this.closed = closed;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
