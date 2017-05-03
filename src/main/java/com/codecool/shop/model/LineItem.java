package com.codecool.shop.model;

/**
 * Created by eszti on 2017.05.03..
 */
public class LineItem {
    private Integer quantity;
    private float price;

    public LineItem(Product product) {
        quantity = 1;
        price = quantity * product.getDefaultPrice();
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
}
