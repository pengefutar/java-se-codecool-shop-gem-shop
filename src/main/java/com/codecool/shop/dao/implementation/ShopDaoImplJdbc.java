package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;

import java.sql.*;


/**
 * Created by marti on 2017.05.15..
 */
public class ShopDaoImplJdbc<T> extends JdbcDao {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/gem_shop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    void add(T object, String tableName) throws SQLException {
        String query = "INSERT INTO ? (?, ?)";
        System.out.println(object.getClass().getFields());

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, object);
        stmt.setString(2, object.getName());
        executeQuery(stmt.toString());

        System.out.println(object.toString());
    }

    public static void main(String[] args) throws SQLException {
        ShopDaoImplJdbc shop = new ShopDaoImplJdbc();
        ProductCategory prodcat = new ProductCategory("jkl","tz","gjhg");
        //System.out.println(prodcat.getName());
        shop.add(prodcat , "gjhg" );

    }

    }


