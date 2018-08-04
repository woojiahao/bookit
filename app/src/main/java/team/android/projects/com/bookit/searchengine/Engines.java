package team.android.projects.com.bookit.searchengine;

public enum Engines {
	GoogleBooks("Google Books", "google-books-api"), NewYorkTimes("New York Times", "new-york-times-api");
	
	public String mapKey;
	public String jsonKey;
	Engines(String mapKey, String jsonKey) {
		this.mapKey = mapKey;
		this.jsonKey = jsonKey;
	}
}
