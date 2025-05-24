package model;

public class Ingredient {
    private int id;
    private String name;
    private int quantity;
    private double price;

    // Full constructor
    public Ingredient(int id, String name, int quantity, double price) {
        this.id       = id;
        this.name     = name;
        this.quantity = quantity;
        this.price    = price;
    }

    // Constructor when creating new Ingredient (no id yet)
    public Ingredient(String name, int quantity, double price) {
        this(0, name, quantity, price);
    }

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

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", quantity=" + quantity +
               ", price=" + price +
               '}';
    }
}
