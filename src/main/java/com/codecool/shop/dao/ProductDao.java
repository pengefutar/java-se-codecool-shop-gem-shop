package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.SQLException;
import java.util.List;

/**
 * Adds, finds, removes, and lists all the products from the storage.
 */
public interface ProductDao {

    /**
     * Adds a product to the storage.
     * @param product
     */
    void add(Product product);

    /**
     * Finds a product from the storage.
     * @param id
     * @return
     */
    Product find(int id);

    /**
     * Removes a product from the storage.
     * @param id
     */
    void remove(int id);

    /**
     * Returns a list of all the products in the storage.
     * @return
     */
    List<Product> getAll();

    /**
     * Gets the products by the specified supplier from the storage.
     * @param supplier
     * @return
     */
    List<Product> getBy(Supplier supplier);

    /**
     * Gets the products by the specified product category from the storage.
     * @param productCategory
     * @return
     */
    List<Product> getBy(ProductCategory productCategory);

}
