package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by eszti on 2017.05.17..
 */
public class ProductDaoTest {

    ProductDao productDao = ProductDaoMem.getInstance();

    ProductCategory testCategory = new ProductCategory("testName", "testDep", "testDesc");
    Supplier testSupplier = new Supplier("testName", "testDescription");
    Product testProduct = new Product("testName", 1,
            "USD", "testDescription",
            testCategory, testSupplier);
    Product testProduct2 = new Product("testName", 1,
            "USD", "testDescription",
            new ProductCategory("", "", ""),
            new Supplier("", ""));

    @Test
    public void testingAdd() {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        assertEquals(numOfItems+1, productDao.getAll().size());
        productDao.remove(productDao.getAll().size());
    }

    @Test
    public void testingFind() {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        assertEquals(testProduct, productDao.find(numOfItems+1));
        productDao.remove(productDao.getAll().size());
    }

    @Test
    public void testingRemove() {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        productDao.remove(productDao.getAll().size());
        assertEquals(numOfItems, productDao.getAll().size());
    }

    @Test
    public void testingGetAll() {
        List<Product> testList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            testList.add(testProduct);
            productDao.add(testProduct);
        }
        assertEquals(testList, productDao.getAll());
        for (int i=0; i<5; i++) {
            productDao.remove(5);
        }
    }

    @Test
    public void testingGetBySupplier() {
        productDao.add(testProduct);
        productDao.add(testProduct2);
        assertEquals(testProduct, productDao.getBy(testSupplier).get(0));
        productDao.remove(1);
        productDao.remove(2);
    }

    @Test
    public void testingGetByCategory() {
        productDao.add(testProduct);
        productDao.add(testProduct2);
        productDao.add(testProduct2);
        assertEquals(testProduct, productDao.getBy(testCategory).get(0));
        productDao.remove(1);
        productDao.remove(2);
        productDao.remove(3);
    }

}