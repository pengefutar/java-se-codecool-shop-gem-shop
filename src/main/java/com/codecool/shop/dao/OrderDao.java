package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

/**
 * Created by eszti on 2017.05.03..
 */
public interface OrderDao {


    void add(Order order);

    Order find(int id);

    void remove(int id);

    List<Order> getAll();
}
