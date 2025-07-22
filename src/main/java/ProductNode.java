/*
 * ProductNode.java
 *
 * This class represents a node in a linked list, storing information about a product, including:
 * - Name, category, cost price, selling price, and quantity.
 * It also holds a reference to the next node, enabling efficient management of product data in the inventory.
 */

class ProductNode {
    private String name;
    private String category;
    private double costPrice;
    private double sellingPrice;
    private int quantity;
    ProductNode next;

    public ProductNode(String name, String category, double costPrice, double sellingPrice, int quantity) {
        this.name = name;
        this.category = category;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.next = null;
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
        if (costPrice >= 0) {
            this.costPrice = costPrice;
        } else {
            throw new IllegalArgumentException("Cost price cannot be negative.");
        }
    }

    public void setPrice(double sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Selling price cannot be negative");
        }
        this.sellingPrice = sellingPrice;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductNode that = (ProductNode) obj;
        return Double.compare(that.costPrice, costPrice) == 0 &&
               Double.compare(that.sellingPrice, sellingPrice) == 0 &&
               quantity == that.quantity &&
               name.equals(that.name) &&
               category.equals(that.category);
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