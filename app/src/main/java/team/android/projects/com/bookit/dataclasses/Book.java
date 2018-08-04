package team.android.projects.com.bookit.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class Book implements Parcelable {
	private double rating;
	
	private String thumbnail;
	private String summary;
	private String title;
	
	private String[] authors;
	private String[] genres;
	
	private Map<String, String> ISBN;
	private Map<String, Double> prices;
	
	public static class Builder {
		private double rating;
		
		private String thumbnail;
		private String summary;
		private String title;
		
		private String[] authors;
		private String[] genres;
		
		private Map<String, String> ISBN;
		private Map<String, Double> prices;
		
		public Builder() {
			rating = 0.0;
			
			thumbnail = null;
			ISBN = null;
			summary = null;
			title = null;
			
			authors = null;
			genres = null;
			prices = null;
		}
		
		public Builder setRating(double rating) {
			this.rating = rating;
			return this;
		}
		
		public Builder setThumbnail(String thumnail) {
			this.thumbnail = thumnail;
			return this;
		}
		
		public Builder setISBN(Map<String, String> ISBN) {
			this.ISBN = ISBN;
			return this;
		}
		
		public Builder setSummary(String summary) {
			this.summary = summary;
			return this;
		}
		
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Builder setAuthors(String[] authors) {
			this.authors = authors;
			return this;
		}
		
		public Builder setGenres(String[] genres) {
			this.genres = genres;
			return this;
		}
		
		public Builder setPrices(Map<String, Double> prices) {
			this.prices = prices;
			return this;
		}
		
		public Book build() {
			return new Book(rating, thumbnail, summary, title, authors, genres, ISBN, prices);
		}
	}
	
	private Book(double rating, String thumbnail,
				 String summary, String title, String[] authors, String[] genres,
				 Map<String, String> ISBN,
				 Map<String, Double> prices) {
		this.rating = rating;
		this.thumbnail = thumbnail;
		this.ISBN = ISBN;
		this.summary = summary;
		this.title = title;
		this.authors = authors;
		this.genres = genres;
		this.prices = prices;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthors() {
		return TextUtils.join(", ", authors);
	}
	
	public String getGenres() {
		return TextUtils.join(", ", genres);
	}
	
	public Map<String, String> getISBN() {
		return ISBN;
	}
	
	public Map<String, Double> getPrices() {
		return prices;
	}
	
	protected Book(Parcel in) {
		rating = in.readDouble();
		thumbnail = in.readString();
		summary = in.readString();
		title = in.readString();
		authors = in.createStringArray();
		genres = in.createStringArray();
		ISBN = new HashMap<String, String>();
		prices = new HashMap<String, Double>();
		in.readMap(ISBN, String.class.getClassLoader());
		in.readMap(prices, Double.class.getClassLoader());
	}
	
	
	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}
		
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};
	
	@Override public int describeContents() {
		return 0;
	}
	
	@Override public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeDouble(rating);
		dest.writeString(thumbnail);
		dest.writeString(summary);
		dest.writeString(title);
		dest.writeStringArray(authors);
		dest.writeStringArray(genres);
		dest.writeMap(ISBN);
		dest.writeMap(prices);
	}
}
