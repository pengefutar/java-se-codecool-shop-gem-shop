package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
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

    @AfterEach
    public void cleanUp(SupplierDao supplierDao){
        System.out.println("cleanup!!");
        int numOfLines = supplierDao.getAll().size();
        Iterator<Supplier> iter = supplierDao.getAll().iterator();
        while (iter.hasNext()) {
            Supplier supplier = iter.next();
            supplierDao.remove(supplier.getId());
        }
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingAdd(SupplierDao supplierDao, Supplier testSupplier) {
        int numOfItems = supplierDao.getAll().size();
        supplierDao.add(testSupplier);
        testSupplier.setId(supplierDao.getAll().get(0).getId());
        assertEquals(numOfItems+1, supplierDao.getAll().size());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingFind(SupplierDao supplierDao, Supplier testSupplier) {
        supplierDao.add(testSupplier);
        int id = supplierDao.getAll().get(0).getId();
        testSupplier.setId(id);
        assertEquals(testSupplier, supplierDao.find(id));
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingRemove(SupplierDao supplierDao, Supplier testSupplier) {
        testSupplier.setId(1);
        int numOfItems = supplierDao.getAll().size();
        supplierDao.add(testSupplier);
        int id = supplierDao.getAll().get(0).getId();
        testSupplier.setId(id);
        supplierDao.remove(id);
        assertEquals(numOfItems, supplierDao.getAll().size());
    }

    @ParameterizedTest
    @MethodSource(names = "daoAndSupplierProvider")
    public void testingGetAll(SupplierDao supplierDao, Supplier testSupplier) {
        List<Supplier> testList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            testList.add(testSupplier);
            int id = supplierDao.getAll().get(i).getId();
            testSupplier.setId(id);
            supplierDao.add(testSupplier);
        }
        assertEquals(testList, supplierDao.getAll());
    }
}