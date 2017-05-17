package com.codecool.shop.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by eszti on 2017.05.15..
 */
public class DatabaseConnectionData {

    private static String filePath = "src/main/resources/connection.properties";
    private static String DB_URL = "jdbc:postgresql://localhost:5432/gem_shop";
    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;

    public static String getDATABASE() {
        return DB_URL;
    }

    public static String getDB_USER(){
        try {
            getUserAndPasswordFromFile();
            return DB_USER;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getDB_PASSWORD(){
        try {
            getUserAndPasswordFromFile();
            return DB_PASSWORD;
        } catch (IOException e){
            e.printStackTrace();
        }
        return DB_PASSWORD;
    }

    private static void getUserAndPasswordFromFile() throws IOException {
        StringBuilder content = new StringBuilder();
        List<String> allLinesList = Arrays.asList(Files.readAllLines(Paths.get(filePath)).
                toString().split("\n"));

        allLinesList.forEach(word -> content.append(word));
        System.out.println(content);

        DB_URL = allLinesList.get(0);
        DB_NAME = allLinesList.get(1);
        DB_USER = allLinesList.get(2);
        DB_PASSWORD = allLinesList.get(3);
    }

    public static void main(String[] args) throws IOException {
        DatabaseConnectionData.getUserAndPasswordFromFile();

    }
}
