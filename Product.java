



class Product {
    private String name;
    private String category;
    private double costPrice;
    private double sellingPrice;
    private int quantity;

    public Product(String name, String category, double costPrice, double sellingPrice, int quantity) {
        this.name = name;
        this.category = category;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getCost() {
        return costPrice;
    }

    public double getPrice() {
        return sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                '}';
    }
}
