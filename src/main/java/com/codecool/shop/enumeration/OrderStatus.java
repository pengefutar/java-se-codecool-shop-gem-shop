package com.codecool.shop.enumeration;

/**
 * Created by keli on 2017.05.03..
 */
public enum OrderStatus {
    NEW("new"), HOLD("hold"), SHIPPED("shipped"), DELIVERED("delivered"), CLOSED("closed");

    private String value;

    private OrderStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
