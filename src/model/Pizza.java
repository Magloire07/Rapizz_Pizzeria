package model;

public class Pizza {
    private int id;
    private String name;
    private double price;
    private String size;
    private String iconPath; // Path to the pizza image

    public Pizza(int id, String name, double price, String size, String iconPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.iconPath = iconPath;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getImagePath() {
        // Assuming there is a field `imagePath` in the Pizza class
        return this.iconPath;
    }
}
