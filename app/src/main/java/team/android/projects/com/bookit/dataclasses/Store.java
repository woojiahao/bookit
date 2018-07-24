package team.android.projects.com.bookit.dataclasses;

public class Store{
    private String name;
    private int storeLogoResID;

    public Store(String name, int storeLogoResID){
        this.name = name;
        this.storeLogoResID = storeLogoResID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoreLogoResID() {
        return storeLogoResID;
    }

    public void setStoreLogoResID(int storeLogoResID) {
        this.storeLogoResID = storeLogoResID;
    }
}

