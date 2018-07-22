package team.android.projects.com.booktit.dataclasses;

public class Genre {
	private String mGenreTitle;
	private boolean mIsSelected;
	
	
	public Genre (String genreTitle) {
		mGenreTitle = genreTitle;
		
		mIsSelected = false;
	}
	
	public String getGenreTitle () {
		return mGenreTitle;
	}
	
	public void setGenreTitle (String mGenreTitle) {
		this.mGenreTitle = mGenreTitle;
	}
	
	public boolean getIsSelected () {
		return mIsSelected;
	}
	
	public void setIsSelected (boolean isSelected) {
		mIsSelected = isSelected;
	}
}
