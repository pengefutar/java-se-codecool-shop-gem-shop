package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;

import java.lang.reflect.Field;
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

    void add(T object, String tableName) throws SQLException, NoSuchFieldException {
        String query = "INSERT INTO ? (?, ?)";
        //System.out.println(object.getClass().getFields());
        Field name = object.getClass().getDeclaredField("name");
        Field id = object.getClass().getDeclaredField("id");

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id.toString()));
            stmt.setString(2, name.toString());
            executeQuery(stmt.toString());

            System.out.println(object.toString());
        }
    }}


