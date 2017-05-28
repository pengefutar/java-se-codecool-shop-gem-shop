package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

/**
 * Adds, finds, removes, and lists all product categories from the storage.
 */
public interface ProductCategoryDao {

    /**
     * Adds a product category to the storage.
     * @param category
     */
    void add(ProductCategory category);

    /**
     * Finds a product category from the storage.
     * @param id
     * @return
     */
    ProductCategory find(int id);

    /**
     * Removes a product category from the storage.
     * @param id
     */
    void remove(int id);

    /**
     * Returns a list of all the product categories in the storage.
     * @return
     */
    List<ProductCategory> getAll();

}
