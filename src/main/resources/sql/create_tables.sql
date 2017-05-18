DROP TABLE IF EXISTS Categories, Currencies, Suppliers, Products, Order_status,
Addresses, Line_items, Orders;

CREATE TABLE Currencies (
  id VARCHAR(10) PRIMARY KEY
);

CREATE TABLE Categories (
  id SERIAL PRIMARY KEY,
  category_name VARCHAR(40),
  category_department VARCHAR(255),
  category_description VARCHAR(255)
);

CREATE TABLE Suppliers(
  id SERIAL PRIMARY KEY,
  supplier_name VARCHAR(40),
  supplier_description VARCHAR(40)
);

CREATE TABLE Products(
  id SERIAL PRIMARY KEY,
  product_name VARCHAR(40),
  product_description VARCHAR(255),
  default_price FLOAT,
  currency_id VARCHAR(10),
  category_id INTEGER,
  supplier_id INTEGER,
  FOREIGN KEY (currency_id) REFERENCES Currencies(id),
  FOREIGN KEY (category_id) REFERENCES Categories(id),
  FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

CREATE TABLE Order_status(
  status_name  VARCHAR(40) PRIMARY KEY
);

CREATE TABLE Addresses(
  id SERIAL PRIMARY KEY,
  country VARCHAR(100),
  city VARCHAR(100),
  address VARCHAR(100),
  zip VARCHAR(10)
);


CREATE TABLE Orders(
  id SERIAL PRIMARY KEY,
  address_id SERIAL,
  status_name VARCHAR(40),
  FOREIGN KEY (status_name) REFERENCES Order_status(status_name),
  FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE Line_items(
  id SERIAL PRIMARY KEY,
  product_id SERIAL,
  quantity INTEGER,
  price INTEGER,
  order_id INTEGER,
  FOREIGN KEY (product_id) REFERENCES Products(id),
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);
INSERT INTO Currencies VALUES('USD');
INSERT INTO Currencies VALUES('EUR');

-- ALTER SEQUENCE suppliers_id_seq RESTART WITH 0 INCREMENT BY 1;
-- ALTER SEQUENCE categories_id_seq RESTART WITH 0 INCREMENT BY 1;
-- ALTER SEQUENCE products_id_seq RESTART WITH 0 INCREMENT BY 1;
-- ALTER SEQUENCE orders_id_seq RESTART WITH 0 INCREMENT BY 1;

--
-- INSERT INTO Categories(category_name, category_department, category_description)
-- VALUES ('Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
-- INSERT INTO Categories(category_name, category_department, category_description)
-- VALUES ('Potato', 'Food', 'A very delicious dish. Edible. Vegan. No sugar, no lactose. Much healthy.');
--
-- INSERT INTO Suppliers(supplier_name, supplier_description) VALUES ('Amazon','Digital content and services');
-- INSERT INTO Suppliers(supplier_name, supplier_description) VALUES ('Lenovo', 'Computers');
-- INSERT INTO Suppliers(supplier_name, supplier_description) VALUES ('Lajos', 'Potatoes');
--
--
-- INSERT INTO Products(product_name, product_description, default_price, currency_id, category_id, supplier_id)
-- VALUES('Amazon Fire', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.',
--        49.9, 'USD', 0, 0);
--
-- INSERT INTO Products(product_name, product_description, default_price, currency_id,category_id, supplier_id)
-- VALUES('Lenovo IdeaPad Miix 700', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.',
--        479, 'USD', 0, 1);
--
-- INSERT INTO Products(product_name, product_description, default_price, currency_id, category_id, supplier_id)
-- VALUES('Amazon Fire HD 8', 'Amazon s latest Fire HD 8 tablet is a great value for media consumption.',
--        89, 'USD', 0, 0);
--
-- INSERT INTO Products(product_name, product_description, default_price, currency_id, category_id, supplier_id)
-- VALUES('Happy Potato', 'A potato who is very very happy.', 3, 'USD', 1, 2);
--
-- INSERT INTO Order_status(status_name) VALUES ('new');
-- INSERT INTO Order_status(status_name) VALUES ('hold');
-- INSERT INTO Order_status(status_name) VALUES ('paid');
-- INSERT INTO Order_status(status_name) VALUES ('shipped');
-- INSERT INTO Order_status(status_name) VALUES ('delivered');
-- INSERT INTO Order_status(status_name) VALUES ('closed');
--
-- INSERT INTO Addresses(country, city, address, zip)
-- VALUES('Hungary', 'Zalaegerszeg', 'Nekeresdi ut 26.', '8900');
--
-- INSERT INTO Orders(address_id, status_name) VALUES(1, 'new');
