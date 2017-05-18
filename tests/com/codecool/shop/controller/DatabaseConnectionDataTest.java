package com.codecool.shop.controller;

import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by keli on 2017.05.17..
 */
public class DatabaseConnectionDataTest {

    @Test
    public void testConnectToTheDbWithValidUser() throws SQLException{
        DatabaseConnectionData dbConn = new DatabaseConnectionData("testConnection.properties");
        Connection connection = DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }

    @Test
    public void testConnectToDbWithInvalidUser() throws SQLException {
        DatabaseConnectionData dbConn = new DatabaseConnectionData("testConnectionFake.properties");
        assertThrows(SQLException.class, () -> {
            Connection connection = DriverManager.getConnection(
                    dbConn.getDb(),
                    dbConn.getDbUser(),
                    dbConn.getDbPassword());
        });
    }

}