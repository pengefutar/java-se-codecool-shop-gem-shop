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
    public void testToConnectToTheDb() throws SQLException{
        DatabaseConnectionData.setupUserAndPasswordFromFile("testConnection.properties");
        Connection connection = DriverManager.getConnection(
                DatabaseConnectionData.getDb(),
                DatabaseConnectionData.getDbUser(),
                DatabaseConnectionData.getDbPassword());

        assertEquals(4, 4);

    }

}