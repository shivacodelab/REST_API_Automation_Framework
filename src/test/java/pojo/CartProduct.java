package pojo;

public class CartProduct {

	private int productId;
    private int quantity;

    // Default Constructor
    public CartProduct() {
    }

    // Parameterized Constructor
    public CartProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}