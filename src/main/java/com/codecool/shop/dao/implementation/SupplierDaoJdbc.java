package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by marti on 2017.05.15..
 */
public class SupplierDaoJdbc extends JdbcDao implements SupplierDao {
    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (id, name) " +
                "VALUES ('" + supplier.getId() + "', '" + supplier.getName() + "');";
        executeQuery(query);
    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }

    @Override
    Connection getConnection() throws SQLException {
        return null;
    }
}

