package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.List;

/**
 * Created by keli on 2017.05.15..
 */
public class ProductDaoImplJdbc extends JdbcDao implements ProductDao{

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/gem_shop";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "pass";


    @Override
    public void add(Product product) throws SQLException {
        String query = "INSERT INTO products (id, default_price, currency_id, category_id, " +
                "supplier_id) VALUES(?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, product.getId());
        stmt.setFloat(2, product.gerPriceInFloat());
        stmt.setString(3, product.getDefaultCurrency().toString());
        stmt.setInt(4, product.getProductCategory().getId());
        stmt.setInt(5, product.getSupplier().getId());
        executeQuery(stmt.toString());
    }

    @Override
    public Product find(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?;";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery(query);

        while (resultSet.next()){
            ProductCategory productCategory = getProductCategory(resultSet.getInt("category_id"));
            Supplier supplier = getSupplier(resultSet.getInt("supplier_id"));

            Product product = new Product(resultSet.getString("name"),
                    resultSet.getInt("default_price"),
                    resultSet.getString("currency_id"),
                    resultSet.getString("description"),
                    productCategory, supplier
                    );
            // ezt chekkold!!
            return product;
        }
        return null;
    }

    public ProductCategory getProductCategory(int id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id = ?;";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery(query);
        resultSet.getInt("id");
        return new ProductCategory(resultSet.getString("category_name"),
                resultSet.getString("department"),
                resultSet.getString("description"));
    }

    public Supplier getSupplier(int id) throws SQLException{
        String query = "SELECT * FROM suppliers WHERE id = ?;";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery(query);
        resultSet.getInt("id");
        return new Supplier(resultSet.getString("supplier_name"),
                resultSet.getString("description"));
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
}
