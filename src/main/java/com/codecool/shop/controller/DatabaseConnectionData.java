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

    private static String filePath = "src/main/resources/";
    private static String DB_URL;
    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;


    public static String getDb() {
        return "jdbc:postgresql://" + DB_URL + "/" + DB_NAME;
    }

    public static String getDbUser(){
        return DB_USER;
    }

    public static String getDbPassword(){
        return DB_PASSWORD;
    }

    public static void setupUserAndPasswordFromFile(String fileName) {
        try {
            List<String> allLinesList = Files.readAllLines(Paths.get(filePath + fileName));
            DB_URL = allLinesList.get(0);
            DB_NAME = allLinesList.get(1);
            DB_USER = allLinesList.get(2);
            DB_PASSWORD = allLinesList.get(3);
        } catch (IOException e){
            System.out.println("Cant read from config file");
        }
    }
}
