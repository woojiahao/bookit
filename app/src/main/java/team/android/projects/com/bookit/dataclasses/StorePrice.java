package team.android.projects.com.bookit.dataclasses;

public class StorePrice {
    private Store store;
    private double price;
    private Book book;

    public StorePrice(Store store, double price){
        this.store = store;
        this.price = price;
        this.book = book;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

}
