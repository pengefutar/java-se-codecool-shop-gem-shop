package com.codecool.shop.dao.implementation;
import java.sql.*;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.16..
 */
public class LineItemDaoImplJdbc extends JdbcDao implements LineItemDao {

    ProductDaoImplJdbc productJdbc = new ProductDaoImplJdbc();
    private static final Logger logger = LoggerFactory.getLogger(LineItem.class);

    @Override
    public void add(LineItem lineItem) {
        String query = "INSERT INTO Line_items (product_id, quantity, price) " +
                "VALUES(?, ?, ?);";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, lineItem.getProduct().getId());
            stmt.setInt(2, lineItem.getQuantity());
            stmt.setFloat(3, lineItem.getPrice());
            logger.info("New lineitem( {} ) added to the database.", lineItem.getProduct().getName());
            executeQuery(stmt.toString());
        }
        catch (SQLException e) {
            System.out.println("LineItem could not be added to the database.");
        }
    }

    @Override
    public LineItem find(int id) {
        String query = "SELECT * FROM Line_items WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                LineItem lineItem = new LineItem(productJdbc.find(
                        resultSet.getInt("product_id")));
                logger.info("Lineitem( {} ) found.", lineItem.getProduct().getName());
                return lineItem;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM Line_items WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            logger.info("Lineitem( {} ) removed from the database.", id);
            executeQuery(stmt.toString());
        }
        catch (SQLException e) {
            System.out.println("LineItem could not be removed.");
        }
    }

    @Override
    public List<LineItem> getAll() {
        List<LineItem> results = new ArrayList<>();
        String query = "SELECT * FROM Line_items;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                LineItem lineItem = new LineItem(productJdbc.find(
                        resultSet.getInt("product_id")));
                lineItem.setId(dbId);
                results.add(lineItem);
                logger.info("Lineitem( {} ) got from the database.", lineItem.getProduct().getName());
            }
            return results;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData conn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                conn.getDb(),
                conn.getDbUser(),
                conn.getDbPassword());
    }
}

