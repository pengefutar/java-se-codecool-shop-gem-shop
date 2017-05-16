package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
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
    public void add(Product product) throws SQLException {
        String query = "INSERT INTO products (name, description, default_price, category_id, supplier_id) " +
                "VALUES(?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getDescription());
        stmt.setFloat(3, product.getDefaultPrice());
        stmt.setInt(4, getProductCategoryID(product.getProductCategory().getName()));
        stmt.setInt(5, getSupplierID(product.getSupplier().getName()));
        executeQuery(stmt.toString());
    }

    @Override
    public Product find(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?;";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

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

    public int getProductCategoryID(String categoryName) throws SQLException {
        String query = "SELECT * FROM categories WHERE category_name = ? ;";

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
    }

    public int getSupplierID(String supplierName) throws SQLException{
        String query = "SELECT * FROM suppliers WHERE supplier_name = ?;";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, supplierName);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.getInt("id");
        if (resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            return resultSet.getInt("id");
        }
        return 0;
    }

    @Override
    public void remove(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        executeQuery(stmt.toString());
    }

    @Override
    public List<Product> getAll() {
        return null;
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

        ProductCategory productCategory = new ProductCategory("category", "department", "desc");
        Supplier supplier = new Supplier("ebay", "desc");


        ProductDaoImplJdbc a = new ProductDaoImplJdbc();
        Product product = new Product("YEYEYEE", 55,
                Currency.getInstance("EUR").toString(),"desc",
                productCategory, supplier);
        try {
            a.add(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
