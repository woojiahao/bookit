package team.android.projects.com.bookit.dataclasses;

public class StoreLocation {
	private String mStoreName;
	private String mStoreURL;
	private double mPrice;
	
	public StoreLocation(String storeName, String storeURL, double price) {
		this.mPrice = price;
		this.mStoreURL = storeURL;
		this.mStoreName = storeName;
	}
	
	public String getStoreName() {
		return mStoreName;
	}
	
	public String getStoreURL() {
		return mStoreURL;
	}
	
	public double getPrice() {
		return mPrice;
	}
}
