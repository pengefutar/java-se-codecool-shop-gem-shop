package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * Created by eszti on 2017.05.17..
 */
public class ProductDaoTest {

//    ProductDao productDao = ProductDaoMem.getInstance();
//
//    ProductCategory testCategory = new ProductCategory("testName", "testDep", "testDesc");
//    Supplier testSupplier = new Supplier("testName", "testDescription");
//    Product testProduct = new Product("testName", 1,
//            "USD", "testDescription",
//            testCategory, testSupplier);
//    Product testProduct2 = new Product("testName", 1,
//            "USD", "testDescription",
//            new ProductCategory("", "", ""),
//            new Supplier("", ""));

    static Stream<Arguments> daoAndProductProvider() {
        ProductCategory testCategory = new ProductCategory("sport", "dep", "desc");
        ProductCategoryDaoImplJdbc productCategoryJdbcDao = new ProductCategoryDaoImplJdbc();
        productCategoryJdbcDao.add(testCategory);
        testCategory.setId(productCategoryJdbcDao.getAll().get(0).getId());
        Supplier testSupplier = new Supplier("ebay", "desc");
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
        supplierDaoJdbc.add(testSupplier);
        testSupplier.setId(supplierDaoJdbc.getAll().get(0).getId());
        Product testProduct = new Product("testName", 1,
                "USD", "testDescription",
                testCategory, testSupplier);
        Product testProduct2 = new Product("testName", 1,
            "USD", "testDescription",
            new ProductCategory("", "", ""),
            new Supplier("", ""));
        return Stream.of(ObjectArrayArguments.create(
                new ProductDaoImplJdbc(),
                testProduct,testProduct2,testSupplier,testCategory),
                ObjectArrayArguments.create(ProductDaoMem.getInstance(),
                        testProduct,testProduct2,testSupplier,testCategory));
    }



    @AfterEach
    public void cleanUp(ProductDao productDao){
        int numOfLines = productDao.getAll().size();
        Iterator<Product> iter = productDao.getAll().iterator();
        while (iter.hasNext()) {
            Product product = iter.next();
            iter.remove();
            productDao.remove(product.getId());
        }
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductProvider")
    public void testingAdd(ProductDao productDao, Product testProduct) {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        assertEquals(numOfItems+1, productDao.getAll().size());
        productDao.remove(productDao.getAll().size());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductProvider")
    public void testingFind(ProductDao productDao, Product testProduct) {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        assertEquals(numOfItems+1, productDao.getAll().size());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductProvider")
    public void testingRemove(ProductDao productDao, Product testProduct) {
        productDao.add(testProduct);
        int id = productDao.getAll().get(0).getId();
        testProduct.setId(id);
        assertEquals(testProduct.getId(), productDao.find(id).getId());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndProductProvider")
    public void testingGetAll(ProductDao productDao, Product testProduct) {
        int numOfItems = productDao.getAll().size();
        productDao.add(testProduct);
        int id = productDao.getAll().get(0).getId();
        testProduct.setId(id);
        productDao.remove(id);
        assertEquals(numOfItems, productDao.getAll().size());
    }

//    @ParameterizedTest
//    @MethodSource(names = "daoAndProductProvider")
//    public void testingGetBySupplier(ProductDao productDao, Product testProduct,Product testProduct2,Supplier testSupplier) {
//        productDao.add(testProduct);
//        productDao.add(testProduct2);
//        testSupplier.setId(1);
//        assertEquals(testProduct.getId(), productDao.getBy(testSupplier).get(0).getId());
//    }
//
//    @ParameterizedTest
//    @MethodSource(names = "daoAndProductProvider")
//    public void testingGetByCategory(ProductDao productDao, Product testProduct, Product testProduct2, ProductCategory testCategory) {
//        productDao.add(testProduct);
//        productDao.add(testProduct2);
//        testCategory.setId(1);
//        assertEquals(testProduct.getId(), productDao.getBy(testCategory).get(0).getId());
//    }

}