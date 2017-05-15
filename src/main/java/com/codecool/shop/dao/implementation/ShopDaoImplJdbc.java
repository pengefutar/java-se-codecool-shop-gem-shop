package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.lang.reflect.Field;
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


    void add(Product product) throws SQLException {
        String query = "INSERT INTO products (id, default_price, currency_id, category_id, " +
                "supplier_id) VALUES(?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, product.getId());
        stmt.setFloat(2, product.gerPriceInFloat());
        stmt.setString(3, product.getDefaultCurrency().toString());
        stmt.setInt(4, product.getProductCategory().getId());
        stmt.setInt(5, product.getSupplier().getId());
        executeQuery(stmt.toString());
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


