package com.codecool.shop.model;

/**
 * Created by eszti on 2017.04.27..
 */
public interface Orderable {

    boolean checkout();
    boolean pay();
    void add();
    void remove();
}
