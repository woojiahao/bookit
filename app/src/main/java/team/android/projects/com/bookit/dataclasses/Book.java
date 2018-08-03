package team.android.projects.com.bookit.dataclasses;

import android.text.TextUtils;

import java.util.Map;

public class Book {
	private double rating;
	
	private String thumbnail;
	private String ISBN;
	private String summary;
	private String title;
	
	private String[] authors;
	private String[] genres;
	
	private Map<String, Double> prices;
	
	public static class Builder {
		private double rating;
		
		private String thumbnail;
		private String ISBN;
		private String summary;
		private String title;
		
		private String[] authors;
		private String[] genres;
		
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
		
		public Builder setISBN(String ISBN) {
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
			return new Book(rating, thumbnail, ISBN, summary, title, authors, genres, prices);
		}
	}
	
	private Book(double rating, String thumbnail,
				 String ISBN, String summary, String title,
				 String[] authors, String[] genres,
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
	
	public String getISBN() {
		return ISBN;
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
	
	public Map<String, Double> getPrices() {
		return prices;
	}
}
