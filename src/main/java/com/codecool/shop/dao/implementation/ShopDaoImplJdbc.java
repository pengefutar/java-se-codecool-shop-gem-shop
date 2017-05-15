package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.Currency;


/**
 * Created by marti on 2017.05.15..
 */
public class ShopDaoImplJdbc extends JdbcDao {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/gem_shop";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "pass";

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }


    public static void main(String[] args) throws SQLException {
        ShopDaoImplJdbc shop = new ShopDaoImplJdbc();
        ProductCategory prodcat = new ProductCategory("jkl","tz","gjhg");
        System.out.println(prodcat.getName());
        Supplier supplier = new Supplier("sad", "desc");
        // shop.add(prodcat);
        Product a = new Product("asd", 55,
                Currency.getInstance("USD").toString(), "descp", prodcat, supplier);
        System.out.println(a.getDefaultCurrency());
        // shop.add(a);
    }

}


