package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

/**
 * Adds, finds, removes, and lists all the orders from the storage.
 */
public interface OrderDao {


    /**
     * Adds an order to the storage.
     * @param order - the order to be added
     */
    void add(Order order);

    /**
     * Finds an order from the storage.
     * @param id - id of the order to find
     * @return
     */
    Order find(int id);

    /**
     * Removes an order from the storage.
     * @param id - id of the order to remove
     */
    void remove(int id);

    /**
     * Returns a list of all the orders in the storage.
     * @return
     */
    List<Order> getAll();
}
