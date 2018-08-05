package team.android.projects.com.bookit.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable{
	private String mStoreName;
	private String mStoreURL;
	private double mPrice;
	
	public Store(String storeName, String storeURL, double price) {
		this.mPrice = price;
		this.mStoreURL = storeURL;
		this.mStoreName = storeName;
	}
	
	protected Store(Parcel in) {
		mStoreName = in.readString();
		mStoreURL = in.readString();
		mPrice = in.readDouble();
	}
	
	public static final Creator<Store> CREATOR = new Creator<Store>() {
		@Override
		public Store createFromParcel(Parcel in) {
			return new Store(in);
		}
		
		@Override
		public Store[] newArray(int size) {
			return new Store[size];
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
