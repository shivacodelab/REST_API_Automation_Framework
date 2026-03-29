package pojo;

import java.util.List;

public class Cart {

	//variables
	private int userId;
    private String date;
    private List<CartProduct> products;//the data is stored in list

    // Default Constructor
    public Cart() {
    }

    // Parameterized Constructor
    public Cart(int userId, String date, List<CartProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }
}
