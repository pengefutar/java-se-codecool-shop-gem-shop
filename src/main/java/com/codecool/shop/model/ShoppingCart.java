package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.03..
 */
public class ShoppingCart {

    private List<LineItem> shoppingList;

    public ShoppingCart(List<LineItem> shoppingList) {
        this.shoppingList = new ArrayList<>();
    }

    public List<LineItem> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<LineItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void add(LineItem lineItem){
        shoppingList.add(lineItem);
    }

    public void remove(LineItem lineItem){
        shoppingList.remove(lineItem);
    }
}