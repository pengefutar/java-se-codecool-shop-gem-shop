import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.enumeration.OrderStatus;
import com.codecool.shop.enumeration.UserState;
import com.codecool.shop.model.*;
import jdk.nashorn.internal.parser.JSONParser;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import org.json.simple.JSONObject;


public class Main {

    private static ProductDao productDataStore = ProductDaoMem.getInstance();
    private static LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();
    private static ShoppingCart shoppingCart = new ShoppingCart();

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();

        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        // Equivalent with above
        get("/index", (Request req, Response res) -> {
           return new ThymeleafTemplateEngine().render( ProductController.renderProducts(req, res) );
        });

        get("/index/category/:id", ProductController::renderByCategory, new ThymeleafTemplateEngine());

        get("/index/supplier/:id", ProductController::renderBySupplier, new ThymeleafTemplateEngine());

        //get("/cart", ProductController::renderToCart, new ThymeleafTemplateEngine());
        // Add this line to your project to enable the debug screen

        post("/increase-lineitem/:lineitem-id", (req, res) -> {
            LineItem lineItem = lineItemDataStore.find(Integer.parseInt(req.params(":lineitem-id")));
            lineItem.setQuantity(lineItem.getQuantity() + 1);
            JSONObject jsonObj = CreateLineItemJSONObject(lineItem);
            res.type("application/json");
            return jsonObj;
        });

        post("/decrease-lineitem/:lineitem-id", (req, res) -> {
            Integer ID = Integer.parseInt(req.params(":lineitem-id"));
            LineItem lineItem = lineItemDataStore.find(ID);
            lineItem.setQuantity(lineItem.getQuantity() - 1);
            if (lineItem.getQuantity() == 0) {
                shoppingCart.remove(lineItem);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("status", "deleted");
                jsonObj.put("id", ID);
                jsonObj.put("totalprice", shoppingCart.getTotalPrice());
                res.type("application/json");
                return jsonObj;
            }
            else {
                JSONObject jsonObj = CreateLineItemJSONObject(lineItem);
                res.type("application/json");
                return jsonObj;
            }
        });

        post("/add-product/:product-id", (req, res) -> {
            System.out.println(req.params(":product-id"));
            Product product = productDataStore.find(Integer.parseInt(req.params(":product-id")));
            LineItem lineItem = new LineItem(product);
            lineItemDataStore.add(lineItem);
            shoppingCart.add(lineItem);
            Integer numOfLineItems = shoppingCart.getShoppingList().size();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("numOfLineItems", numOfLineItems);
            res.type("application/json");
            return jsonObj;
        });

        get("/cart", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( ProductController.renderToCart(req, res, shoppingCart) );
        });

        enableDebugScreen();
    }


    public static JSONObject CreateLineItemJSONObject(LineItem lineItem) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("quantity", lineItem.getQuantity());
        jsonObj.put("price", lineItem.getPrice());
        jsonObj.put("currency", lineItem.getProduct().getDefaultCurrency().toString());
        jsonObj.put("status", "exists");
        jsonObj.put("totalprice", shoppingCart.getTotalPrice());
        return jsonObj;
    }


    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier lajos = new Supplier("Lajos", "Potatoes");
        supplierDataStore.add(lajos);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        ProductCategory potato = new ProductCategory("Potato", "Food", "A very delicious dish. Edible. Vegan. No sugar, no lactose. Much healthy.");
        productCategoryDataStore.add(potato);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        Product potato2 = new Product("Happy Potato", 3, "USD", "A potato who is very very happy.", potato, lajos);
        productDataStore.add(potato2);

    }


}
