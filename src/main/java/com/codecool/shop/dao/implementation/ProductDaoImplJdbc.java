package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by keli on 2017.05.15..
 */
public class ProductDaoImplJdbc extends JdbcDao implements ProductDao{

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    @Override
    public void add(Product product){
        String query = "INSERT INTO products (product_name, product_description, default_price, currency_id, " +
                "category_id, supplier_id) VALUES(?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getDefaultPrice());
            stmt.setString(4, product.getDefaultCurrency().toString());
            stmt.setInt(5, product.getProductCategory().getId());
            stmt.setInt(6, product.getSupplier().getId());
            executeQuery(stmt.toString());
            logger.info("New product( {} ) added to the database.", product.getName());
            connection.close();
        } catch (SQLException e){
            System.out.println("Couldn't add product");
        }

    }

    @Override
    public Product find(int id) {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
        String query = "SELECT * FROM products WHERE id = ?;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                logger.info("Product( {} ) found.", product.getName());
                product.setId(resultSet.getInt("id"));
                return product;
            }
            connection.close();
        } catch (SQLException e){
            System.out.println("Couldn't find product");
        }

        return null;
    }

    @Override
    public void remove(int id){
        String query = "DELETE FROM products WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            logger.info("Product( {} ) removed from the database.", id);
            connection.close();
        } catch (SQLException e){
            System.out.println("Couldn't remove the product");
        }
    }

    public List<Product> getAll() {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        List<Product> productList = new ArrayList<>();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();

        String query = "SELECT * from products;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
                productList.add(product);
                logger.info("Product( {} ) got from the database.", product.getName());
            }
            connection.close();
        } catch (SQLException exception){
            System.out.println("Couldn't get all product");
        }
        return productList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE supplier_id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, supplier.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplier);
                product.setId(resultSet.getInt("id"));
                productList.add(product);
            }
            connection.close();
        } catch (SQLException e){
            System.out.println("Cannot get products by supplier");
        }
        return productList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> productListByCategory = new ArrayList<>();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
        String query = "SELECT * FROM products WHERE category_id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, productCategory.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategory,
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
                productListByCategory.add(product);
            }
            connection.close();
        } catch (SQLException e){
            System.out.println("Cannot get products by product category");
        }
        return productListByCategory;
    }

    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData dbConn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }
}
