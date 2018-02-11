package github_provider;

public class Issue {
	private String title;
	private String text;
	private boolean closed;
	private String createdAt;
	
	public Issue(){}
	public Issue(String title, String text, boolean closed, String createdAt){
		this.setTitle(title);
		this.text = text;
		this.closed = closed;
		this.setCreatedAt(createdAt);
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
