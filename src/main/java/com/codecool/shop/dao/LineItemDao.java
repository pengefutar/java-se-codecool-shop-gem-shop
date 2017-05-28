package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;

import java.util.List;

/**
 * Adds, finds, removes, and lists all the lineitems from the storage.
 */
public interface LineItemDao {
    /**
     * Adds a lineitem to the storage.
     * @param lineItem
     */
    void add(LineItem lineItem);

    /**
     * Finds a lineitem from the storage.
     * @param id
     * @return
     */
    LineItem find(int id);

    /**
     * Removes a lineitem from the storage.
     * @param id
     */
    void remove(int id);

    /**
     * Returns a list of all the lineitems in the storage.
     * @return
     */
    List<LineItem> getAll();
}
