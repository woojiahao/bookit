package team.android.projects.com.bookit.dataclasses;

public class Genre {
	private String mGenreTitle;
	private int mGenreBackground;
	private int mGenreIcon;
	
	private boolean mIsSelected;
	
	public Genre(String genreTitle, int genreIcon) {
		mGenreTitle = genreTitle;
		mGenreIcon = genreIcon;
		
		mIsSelected = false;
	}
	
	public String getGenreTitle() {
		return mGenreTitle;
	}
	
	public void setGenreTitle(String mGenreTitle) {
		this.mGenreTitle = mGenreTitle;
	}
	
	public int getGenreBackground() {
		return mGenreBackground;
	}
	
	public void setGenreBackground(int genreBackground) {
		mGenreBackground = genreBackground;
	}
	
	public int getGenreIcon() {
		return mGenreIcon;
	}
	
	public void setGenreIcon(int genreIcon) {
		mGenreIcon = genreIcon;
	}
	
	public boolean getIsSelected() {
		return mIsSelected;
	}
	
	public void setIsSelected(boolean isSelected) {
		mIsSelected = isSelected;
	}
}