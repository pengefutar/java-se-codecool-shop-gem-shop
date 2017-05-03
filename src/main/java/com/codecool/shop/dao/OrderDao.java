package com.codecool.shop.dao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.List;

/**
 * Created by eszti on 2017.05.03..
 */
public interface OrderDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    List<Product> getAll();
}
