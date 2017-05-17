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
    private static String DB_URL;
    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;

    public static String getDb() {
        setupUserAndPasswordFromFile();
        return "jdbc:postgresql://" + DB_URL + "/" + DB_NAME;
    }

    public static String getDbUser(){
        setupUserAndPasswordFromFile();
        return DB_USER;
    }

    public static String getDbPassword(){
        setupUserAndPasswordFromFile();
        return DB_PASSWORD;
    }

    private static void setupUserAndPasswordFromFile() {
        try {
            List<String> allLinesList = Files.readAllLines(Paths.get(filePath));
            DB_URL = allLinesList.get(0);
            DB_NAME = allLinesList.get(1);
            DB_USER = allLinesList.get(2);
            DB_PASSWORD = allLinesList.get(3);
        } catch (IOException e){
            System.out.println("Cant read from config fole");
        }
    }
}
