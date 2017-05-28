package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.16..
 */


public class ProductCategoryDaoImplJdbc extends JdbcDao implements ProductCategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategory.class);
    @Override
    public void add(ProductCategory category) {
        try {
            String query = "INSERT INTO Categories " +
                    "(category_name, category_department, category_description)" +
                    " VALUES(?,?,?);";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDepartment());
            stmt.setString(3, category.getDescription());
            executeQuery(stmt.toString());
            logger.info("New category( {} ) added to the database.", category.getName());
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Category could not be added to the database.");
        }

    }

    @Override
    public ProductCategory find(int id)  {
        String query = "SELECT * FROM categories WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()){
                ProductCategory category = new ProductCategory(
                        resultSet.getString("category_name"),
                        resultSet.getString("category_department"),
                        resultSet.getString("category_description"));
                category.setId(resultSet.getInt("id"));
                logger.info("Category( {} ) found.", category.getName());
                connection.close();
                return category;
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void remove(int id) {

        try {
            String query = "DELETE FROM categories WHERE id = ?";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            logger.info("Category( {} ) removed from the database.", id);
            connection.close();}
        catch (SQLException e) {
            System.out.println("Could not remove category from database.");
        }

    }

    @Override
    public List<ProductCategory> getAll() {

        try {
            List<ProductCategory> results = new ArrayList<>();
            String query = "SELECT * FROM categories;";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                ProductCategory category = new ProductCategory(
                        resultSet.getString("category_name"),
                        resultSet.getString("category_department"),
                        resultSet.getString("category_description"));
                category.setId(dbId);
                results.add(category);
                logger.info("Category( {} ) got from the database.", category.getName());
            }
            connection.close();
            return results;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData dbConn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }
}

