package com.codecool.shop.enumeration;

/**
 * Created by keli on 2017.05.03..
 */
public enum UserState {
    NEW("new"), ACTIVE("atcive"), BLOCKED("blocked"), BANNED("banned");
    private String value;

    private UserState(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
