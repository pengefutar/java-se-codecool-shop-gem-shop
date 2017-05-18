package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by eszti on 1017.05.18..
 */
public class SupplierDaoTest {

    static Stream<Arguments> daoAndSupplierProvider() {
        return Stream.of(ObjectArrayArguments.create(
                new SupplierDaoJdbc(), new Supplier("testName", "testDesc")),
                ObjectArrayArguments.create(SupplierDaoMem.getInstance(),
                        new Supplier("testName", "testDesc")
                ));
    }


    public static void cleanUp(SupplierDao supplierDao){
        int numOfLines = supplierDao.getAll().size();
        for (int i=0; i<numOfLines+1; i++) {
            supplierDao.remove(i);
        }
    }


    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingAdd(SupplierDao supplierDao, Supplier testSupplier) {
        testSupplier.setId(1);
        int numOfItems = supplierDao.getAll().size();
        supplierDao.add(testSupplier);
        assertEquals(numOfItems+1, supplierDao.getAll().size());
        supplierDao.remove(1);
        cleanUp(supplierDao);
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingFind(SupplierDao supplierDao, Supplier testSupplier) {
        testSupplier.setId(1);
        supplierDao.add(testSupplier);
        assertEquals(testSupplier, supplierDao.find(1));
        supplierDao.remove(1);
        cleanUp(supplierDao);
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingRemove(SupplierDao supplierDao, Supplier testSupplier) {
        testSupplier.setId(1);
        int numOfItems = supplierDao.getAll().size();
        supplierDao.add(testSupplier);
        supplierDao.remove(1);
        assertEquals(numOfItems, supplierDao.getAll().size());
        cleanUp(supplierDao);
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingGetAll(SupplierDao supplierDao, Supplier testSupplier) {
        List<Supplier> testList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            testSupplier.setId(i+1);
            testList.add(testSupplier);
            supplierDao.add(testSupplier);
        }
        assertEquals(testList, supplierDao.getAll());
        for (int i=0; i<5; i++) {
            supplierDao.remove(1);
        }
        cleanUp(supplierDao);
    }
}