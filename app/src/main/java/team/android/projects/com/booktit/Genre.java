package team.android.projects.com.booktit;

public class Genre {
	public enum ColorScheme {
		BLACK, WHITE
	}
	
	private String mGenreTitle;
	private int mGenreIcon;
	private int mGenreImage;
	private boolean mIsSelected;
	
	private ColorScheme mScheme;
	
	public Genre (String genreTitle, int genreIcon, int genreImage) {
		mGenreTitle = genreTitle;
		mGenreIcon = genreIcon;
		mGenreImage = genreImage;
		
		mIsSelected = false;
		
		mScheme = ColorScheme.BLACK;
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
	
	public boolean getIsSelected () {
		return mIsSelected;
	}
	
	public void setIsSelected (boolean isSelected) {
		mIsSelected = isSelected;
	}
	
	public ColorScheme getScheme () {
		return mScheme;
	}
	
	public void setScheme(ColorScheme scheme) {
		mScheme = scheme;
	}
}
