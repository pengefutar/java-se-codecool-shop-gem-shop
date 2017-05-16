package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by keli on 2017.05.15..
 */
public class ProductDaoImplJdbc extends JdbcDao implements ProductDao{

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/gem_shop";
    private static final String DB_USER = "keli";
    private static final String DB_PASSWORD = "pg_Abc5354!";


    public void addForeignKey(){
        ProductCategory cat = new ProductCategory("ent", "department", "desc");
        Supplier supplier = new Supplier("ebay", "desc");
        Supplier supplier2 = new Supplier("ali", "desc");

        String categoriesQuery = "INSERT INTO categories (category_name, department, description)" +
                "VALUES('" + cat.getName() + "', '" + cat.getDepartment() + "', '" +
                cat.getDescription() + "');";

        String supplierQuery = "INSERT INTO Suppliers(supplier_name, description) " +
                "VALUES('" + supplier.getName() +"', '" + supplier.getDescription() + "');";

        String supplierQuery2 = "INSERT INTO Suppliers(supplier_name, description) " +
                "VALUES('" + supplier2.getName() +"', '" + supplier2.getDescription() + "');";

        // proba currencies
        String queryCurrencies = "INSERT INTO currencies VALUES('" +
                Currency.getInstance("USD").getCurrencyCode().toString() + "');";

        executeQuery(supplierQuery2);
        executeQuery(supplierQuery);
        executeQuery(queryCurrencies);
        executeQuery(categoriesQuery);
    }

    @Override
    public void add(Product product){
        String query = "INSERT INTO products (name, description, default_price, category_id, supplier_id) " +
                "VALUES(?, ?, ?, ?, ?);";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getDefaultPrice());
            stmt.setInt(4, getProductCategoryID(product.getProductCategory().getName()));
            stmt.setInt(5, getSupplierID(product.getSupplier().getName()));
            executeQuery(stmt.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) {
        String query = "SELECT * FROM products WHERE name = ?;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }


//        while (resultSet.next()){
//            ProductCategory productCategory = getProductCategory(resultSet.getInt("category_id"));
//            Supplier supplier = getSupplier(resultSet.getInt("supplier_id"));
//
//            Product product = new Product(resultSet.getString("name"),
//                    resultSet.getInt("default_price"),
//                    resultSet.getString("currency_id"),
//                    resultSet.getString("description"),
//                    productCategory, supplier
//                    );
//            // ezt chekkold!!
//            return product;
        return null;
        }

    public int getProductCategoryID(String categoryName) {
        String query = "SELECT * FROM categories WHERE category_name = ? ;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, categoryName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                return resultSet.getInt("id");
            } else {
                return 0;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Supplier getSupplierInstance(int id) {
        String query = "SELECT * FROM suppliers WHERE id = ?;";
        try{
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                return new Supplier(resultSet.getString("supplier_name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ProductCategory getProductCategoryInstance(int id) {
        String query = "SELECT * FROM categories WHERE id = ?;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                return new ProductCategory(resultSet.getString("category_name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public int getSupplierID(String supplierName) {
        String query = "SELECT * FROM suppliers WHERE supplier_name = ?;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, supplierName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                return resultSet.getInt("id");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void remove(int id){
        String query = "DELETE FROM products WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();

        String query = "SELECT * from products;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Product product = new Product(resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency_id"),
                        resultSet.getString("description"),
                        getProductCategoryInstance(resultSet.getInt("category_id")),
                        getSupplierInstance(resultSet.getInt("supplier_id")));
                productList.add(product);
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        return productList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public static void main(String[] args){
        // new ProductDaoImplJdbc().addForeignKey();

//        ProductCategory productCategory = new ProductCategory("sport", "department", "desc");
//        Supplier supplier = new Supplier("ebay", "desc");
//
//
        ProductDaoImplJdbc a = new ProductDaoImplJdbc();
//        Product product = new Product("YEYEYEE", 55,
//                Currency.getInstance("EUR").toString(),"desc",
//                productCategory, supplier);
        try {
            List<Product> list = a.getAll();
            list.forEach(p -> System.out.println(p.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
