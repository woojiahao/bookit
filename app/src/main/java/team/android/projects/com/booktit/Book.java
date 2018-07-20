package team.android.projects.com.booktit;

public class Book {
    private Genre genre;
    private String name;
    private double rating;
    private double price;
    private int resID;

    public Book(String name, double rating, double price, Genre genre, int resID) {
        this.genre = genre;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
