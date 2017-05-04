import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.enumeration.OrderStatus;
import com.codecool.shop.enumeration.UserState;
import com.codecool.shop.model.*;
import jdk.nashorn.internal.parser.JSONParser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import org.json.simple.JSONObject;


public class Main {

    private static ProductDao productDataStore = ProductDaoMem.getInstance();
    private static LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();
    private static OrderDao orderDataStore = OrderDaoMem.getInstance();
    private static ShoppingCart shoppingCart = new ShoppingCart();

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();

        redirect.get("/", "/index");

        before("/index", (req, res) -> {
            req.session().attribute("shoppingCart", shoppingCart);
            System.out.println(shoppingCart.getShoppingList().size());
        });

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

        get("/cart", ProductController::renderToCart, new ThymeleafTemplateEngine());
        // Add this line to your project to enable the debug screen

        post("/increase-product/:product-id", (req, res) -> {
            Product product = productDataStore.find(Integer.parseInt(req.params(":product-id")));
            System.out.println(product);
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        post("/add-product/:product-id", (req, res) -> {
            Product product = productDataStore.find(Integer.parseInt(req.params(":product-id")));
            LineItem lineItem = new LineItem(product);
            lineItemDataStore.add(lineItem);
            shoppingCart.add(lineItem);
            Integer numOfLineItems = shoppingCart.getShoppingList().size();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("numOfLineItems", numOfLineItems);
            res.type("application/json");
            // ShoppingCart shoppingCart = req.session().attribute("shoppingCart");
            // shoppingCart.getShoppingList().forEach(item -> System.out.println(item.getProduct()));
            return jsonObj;
        });

        get("/cart", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( ProductController.renderToCart(req, res) );
        });

        get("/checkout", (Request req, Response res) -> {
            //Order order = new Order(req.session().attribute("shoppingCart"));
            //orderDataStore.add(order);
            return new ThymeleafTemplateEngine().render( ProductController.renderToCheckout(req, res) );
        });

        enableDebugScreen();
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
