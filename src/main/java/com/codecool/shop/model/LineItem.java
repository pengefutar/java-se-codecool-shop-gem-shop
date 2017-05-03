package com.codecool.shop.model;

/**
 * Created by eszti on 2017.05.03..
 */
public class LineItem {
    private int id;
    private int counter;
    private Integer quantity;
    private float price;
    private Product product;

    public LineItem(Product product) {
        counter ++;
        id = counter;
        quantity = 1;
        this.product = product;
        price = quantity * this.product.getDefaultPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        quantity = quantity;
    }
    
    public float getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }
}
