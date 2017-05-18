package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by eszti on 2017.05.18..
 */
public class ProductCategoryDaoTest {

    ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();
    ProductCategory testCategory = new ProductCategory("testName", "testDep", "testDesc");

    @Test
    public void testingAdd() {
        int numOfItems = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        assertEquals(numOfItems+1, productCategoryDao.getAll().size());
        productCategoryDao.remove(productCategoryDao.getAll().size());
    }
    @Test
    public void testingFind() {
        int numOfItems = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        assertEquals(testCategory, productCategoryDao.find(numOfItems+1));
        productCategoryDao.remove(productCategoryDao.getAll().size());
    }

    @Test
    public void testingRemove() {
        int numOfItems = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        productCategoryDao.remove(productCategoryDao.getAll().size());
        assertEquals(numOfItems, productCategoryDao.getAll().size());
    }

    @Test
    public void testingGetAll() {
        List<ProductCategory> testList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            testList.add(testCategory);
            productCategoryDao.add(testCategory);
        }
        assertEquals(testList, productCategoryDao.getAll());
        for (int i=0; i<5; i++) {
            productCategoryDao.remove(5);
        }
    }

}