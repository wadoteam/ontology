package github_provider;

import java.util.List;

public class Repository {
	private String name;
	private String url;
	private String description;
	private String language;
	private int stars;
	private List<String> topics;
	private List<Issue> issues;

	public Repository() {
	}

	public Repository(String name, String url, String description, String language, int stars, List<String> topics, List<Issue> issues) {
		this.name = name;
		this.url = url;
		this.description = description;
		this.language = language;
		this.stars = stars;
		this.topics = topics;
		this.issues = issues;
	}

	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
}
