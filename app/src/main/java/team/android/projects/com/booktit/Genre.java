package team.android.projects.com.booktit;

public class Genre {
	private String mGenreTitle;
	private int mGenreIcon;
	private int mGenreImage;
	
	public Genre (String genreTitle, int genreIcon, int genreImage) {
		mGenreTitle = genreTitle;
		mGenreIcon = genreIcon;
		mGenreImage = genreImage;
	}
	
	public String getGenreTitle () {
		return mGenreTitle;
	}
	
	public void setGenreTitle (String mGenreTitle) {
		this.mGenreTitle = mGenreTitle;
	}
	
	public int getGenreIcon () {
		return mGenreIcon;
	}
	
	public void setGenreIcon (int mGenreIcon) {
		this.mGenreIcon = mGenreIcon;
	}
	
	public int getGenreImage () {
		return mGenreImage;
	}
	
	public void setGenreImage (int mGenreImage) {
		this.mGenreImage = mGenreImage;
	}
}
