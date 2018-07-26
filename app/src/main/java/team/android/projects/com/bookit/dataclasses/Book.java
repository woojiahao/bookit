package team.android.projects.com.bookit.dataclasses;

public class Book {
	private String mGenre;
	private String mTitle;
	private String mAuthor;
	private String mLocation;
	
	private float mRating;
	private float mPrice;
	private int mThumbnail;
	
	public Book(String name, String genre, String author, String location, double rating, double price, int thumbnail) {
		this(name, genre, author, location, (float) rating, (float) price, thumbnail);
	}
	
	public Book(String name, String genre, String author, String location, float rating, float price, int thumbnail) {
		mGenre = genre;
		mTitle = name;
		mRating = rating;
		mPrice = price;
		mThumbnail = thumbnail;
		mAuthor = author;
		mLocation = location;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String name) {
		mTitle = name;
	}
	
	public float getPrice() {
		return mPrice;
	}
	
	public void setPrice(float price) {
		mPrice = price;
	}
	
	public float getRating() {
		return mRating;
	}
	
	public void setRating(float rating) {
		mRating = rating;
	}
	
	public String getGenre() {
		return mGenre;
	}
	
	public void setGenre(String genre) {
		mGenre = genre;
	}
	
	public int getThumbnail() {
		return mThumbnail;
	}
	
	public void setThumbnail(int thumbnail) {
		mThumbnail = thumbnail;
	}
	
	public String getAuthor() {
		return mAuthor;
	}
	
	public void setAuthor(String author) {
		mAuthor = author;
	}
	
	public String getLocation() {
		return mLocation;
	}
	
	public void setLocation(String location) {
		mLocation = location;
	}
}
