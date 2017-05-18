package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoImplJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by eszti on 2017.05.18..
 */
public class ProductCategoryDaoTest {

    static Stream<Arguments> daoAndProductCategoryProvider() {
        return Stream.of(ObjectArrayArguments.create(
                new ProductCategoryDaoImplJdbc(),
                new ProductCategory("testName", "testDep", "testDesc")),
                ObjectArrayArguments.create(ProductCategoryDaoMem.getInstance(),
                        new ProductCategory("testName", "testDep", "testDesc")));
    }

    @AfterEach
    public void cleanUp(ProductCategoryDao productCategoryDao){
        int numOfLines = productCategoryDao.getAll().size();
        Iterator<ProductCategory> iter = productCategoryDao.getAll().iterator();
        while (iter.hasNext()) {
            ProductCategory productCategory = iter.next();
            iter.remove();
        }
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductCategoryProvider")
    public void testingAdd(ProductCategoryDao productCategoryDao, ProductCategory testCategory) {
        int numOfItems = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        assertEquals(numOfItems+1, productCategoryDao.getAll().size());
    }
    @ParameterizedTest
    @MethodSource(names = "daoAndProductCategoryProvider")
    public void testingFind(ProductCategoryDao productCategoryDao, ProductCategory testCategory) {
        productCategoryDao.add(testCategory);
        int id = productCategoryDao.getAll().get(0).getId();
        testCategory.setId(id);
        assertEquals(testCategory.getId(), productCategoryDao.find(id).getId());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductCategoryProvider")
    public void testingRemove(ProductCategoryDao productCategoryDao, ProductCategory testCategory) {
        int numOfItems = productCategoryDao.getAll().size();
        productCategoryDao.add(testCategory);
        int id = productCategoryDao.getAll().get(0).getId();
        testCategory.setId(id);
        productCategoryDao.remove(id);
        assertEquals(numOfItems, productCategoryDao.getAll().size());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductCategoryProvider")
    public void testingGetAll(ProductCategoryDao productCategoryDao, ProductCategory testCategory) {
        List<ProductCategory> testList = new ArrayList<>();
        for (int i=0; i<productCategoryDao.getAll().size(); i++) {
            ProductCategory productCategory = new ProductCategory("test_name","test_dep","test_desc");
            int id = productCategoryDao.getAll().get(i).getId();
            productCategory.setId(id);
            testList.add(productCategory);
        }
        for (int i=0; i < productCategoryDao.getAll().size(); i++) {
            assertEquals(testList.get(i).getId(), productCategoryDao.getAll().get(i).getId());}
    }

}