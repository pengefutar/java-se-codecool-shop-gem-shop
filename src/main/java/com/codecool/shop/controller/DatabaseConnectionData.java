package com.codecool.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by eszti on 2017.05.15..
 */
public class DatabaseConnectionData {

    private static String filePath = "parameter.txt";
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/gem_shop";
    private static String DB_USER;
    private static String DB_PASSWORD;

    public static String getDATABASE() {
        return DATABASE;
    }

    public static String getDB_USER() {
        getUserAndPasswordFromFile();
        return DB_USER;
    }

    public static String getDB_PASSWORD() {
        getUserAndPasswordFromFile();
        return DB_PASSWORD;
    }

    private static void getUserAndPasswordFromFile() {

        String content = "";
        try {
            content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        }
        catch (IOException e) {
            System.out.println("Please make a parameter.txt file " +
                    "following the instructions in parameter_template.txt");
        }

        String userAndPass[] = content.split(";");
        DB_USER = userAndPass[0];
        DB_PASSWORD = userAndPass[1];
    }
}
