package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {
    private static ProductDao productDataStore = ProductDaoMem.getInstance();
    private static ProductCategoryDaoMem productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private static SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
    private static LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();

    public static ModelAndView renderProducts(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getAll());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderByCategory(Request req, Response res) {
        int searchedId = Integer.parseInt(req.params(":id"));

        Map params = new HashMap<>();
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(searchedId)));
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderBySupplier(Request req, Response res) {
        int searchedId = Integer.parseInt(req.params(":id"));
        Map params = new HashMap<>();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getBy(supplierDataStore.find(searchedId)));
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderToCart(Request req, Response res){
        Map params = new HashMap<>();
        params.put("lineitems", lineItemDataStore.getAll());
        return new ModelAndView(params, "product/cart");
    }
}
