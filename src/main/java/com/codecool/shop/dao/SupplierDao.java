package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

import java.sql.SQLException;
import java.util.List;

/**
 * Adds, finds, removes, and lists all the suppliers from the storage.
 */
public interface SupplierDao {

    /**
     * Adds a supplier to the storage.
     * @param supplier
     */
    void add(Supplier supplier);

    /**
     * Finds a supplier from the storage.
     * @param id
     * @return
     */
    Supplier find(int id);

    /**
     * Removes a supplier from the storage.
     * @param id
     */
    void remove(int id);

    /**
     * Returns a list of all the suppliers in the storage.
     * @return
     */
    List<Supplier> getAll();
}
