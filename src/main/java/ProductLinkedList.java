/*
 * ProductLinkedList.java
 *
 * This class defines a linked list structure to store ProductNode objects.
 * It provides an efficient way to manage and manipulate product data in the inventory,
 * allowing easy additions, deletions, and updates.
 */

class ProductLinkedList {
    ProductNode head;

    public void addProduct(ProductNode newProduct) {
        if (head == null) {
            // if the list is empty, make newProduct the head
            head = newProduct;
        } else {
            // traverse then add newProduct at the end of the list
            ProductNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newProduct;
        }
    }
    
    public ProductNode getProductByName(String name) {
        // get the name of the product node object
        ProductNode current = head;
        while (current != null) {
            if (current.getName().equals(name)) {
                return current; 
            }
            current = current.next;
        }
        // if no product is found
        return null;  
    }


  
    public void removeProduct(ProductNode productNode) {
        if (head == null) return; // empty list

        if (head.equals(productNode)) {
            head = head.next;
            return;
        }

        ProductNode current = head;
        while (current != null && current.next != null) {
            if (current.next.equals(productNode)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }


    public boolean updateProduct(String name, String newCategory, double newCostPrice, double newSellingPrice, int newQuantity) {
        ProductNode current = head;

        while (current != null) {
            if (current.getName().equals(name)) {
                current.setCategory(newCategory);
                current.setCost(newCostPrice);
                current.setPrice(newSellingPrice);
                current.setQuantity(newQuantity);
                return true; // Product updated
            }
            current = current.next;
        }
        return false; // Product not found
    }

    // Display all products
    public void displayProducts() {
        ProductNode current = head;

        if (current == null) {
            System.out.println("No products in the inventory.");
            return;
        }

        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }
}
