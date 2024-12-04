package Practice2;

public class Product {
    private int id;
    private String name;
    private double price;
    private Productcategory catogery;
    private int stock;
    private double totalIndividualprice;
    private int quantity;

    public Product(int id, String name, double price, Productcategory catogery, int stock ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.catogery = catogery;
        this.stock = stock;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Productcategory getCatogery() {
        return catogery;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getTotalIndividualprice() {
        return getQuantity()* getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
