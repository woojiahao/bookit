package team.android.projects.com.bookit.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreLocation implements Parcelable{
	private String mStoreName;
	private String mStoreURL;
	private double mPrice;
	
	public StoreLocation(String storeName, String storeURL, double price) {
		this.mPrice = price;
		this.mStoreURL = storeURL;
		this.mStoreName = storeName;
	}
	
	protected StoreLocation(Parcel in) {
		mStoreName = in.readString();
		mStoreURL = in.readString();
		mPrice = in.readDouble();
	}
	
	public static final Creator<StoreLocation> CREATOR = new Creator<StoreLocation>() {
		@Override
		public StoreLocation createFromParcel(Parcel in) {
			return new StoreLocation(in);
		}
		
		@Override
		public StoreLocation[] newArray(int size) {
			return new StoreLocation[size];
		}
	};
	
	public String getStoreName() {
		return mStoreName;
	}
	
	public String getStoreURL() {
		return mStoreURL;
	}
	
	public double getPrice() {
		return mPrice;
	}
	
	public void setPrice(double price) {
		mPrice = price;
	}
	
	@Override public String toString() {
		return String.format("Store Name: %s/Store URL: %s/Price: %s", mStoreName, mStoreURL, mPrice);
	}
	
	@Override public int describeContents() {
		return 0;
	}
	
	@Override public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(mStoreName);
		dest.writeString(mStoreURL);
		dest.writeDouble(mPrice);
	}
}
