DROP TABLE IF EXISTS Categories, Currencies, Suppliers, Products, Order_status,
Addresses, Line_items, Orders;

CREATE TABLE IF NOT EXISTS Currencies (
  id VARCHAR(10) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Categories (
  id SERIAL PRIMARY KEY,
  category_name VARCHAR(40),
  category_department VARCHAR(255),
  category_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Suppliers(
  id SERIAL PRIMARY KEY,
  supplier_name VARCHAR(40),
  supplier_description VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS Products(
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

CREATE TABLE IF NOT EXISTS Order_status(
  status_name  VARCHAR(40) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Addresses(
  id SERIAL PRIMARY KEY,
  country VARCHAR(100),
  city VARCHAR(100),
  address VARCHAR(100),
  zip VARCHAR(10)
);


CREATE TABLE IF NOT EXISTS Orders(
  id SERIAL PRIMARY KEY,
  address_id SERIAL,
  status_name VARCHAR(40),
  FOREIGN KEY (status_name) REFERENCES Order_status(status_name),
  FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE IF NOT EXISTS Line_items(
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

INSERT INTO Categories(category_name, category_department, category_description) VALUES ('sport', 'dep', 'desc');
INSERT INTO Categories(category_name, category_department, category_description) VALUES ('ent', 'dep', 'desc');

INSERT INTO Suppliers(supplier_name, supplier_description) VALUES ('ebay','desc');
INSERT INTO Suppliers(supplier_name, supplier_description) VALUES ('ali', 'desc');

INSERT INTO Products(product_name, product_description, default_price, currency_id,
                     category_id, supplier_id) VALUES('product1', 'desc', 55, 'USD', 1, 1);
INSERT INTO Products(product_name, product_description, default_price, currency_id,
                     category_id, supplier_id) VALUES('product2', 'desc', 12, 'USD', 2, 2);
INSERT INTO Products(product_name, product_description, default_price, currency_id,
                     category_id, supplier_id) VALUES('product3', 'desc', 25, 'USD', 1, 2);

INSERT INTO Order_status(status_name) VALUES ('new');
INSERT INTO Order_status(status_name) VALUES ('hold');
INSERT INTO Order_status(status_name) VALUES ('paid');
INSERT INTO Order_status(status_name) VALUES ('shipped');
INSERT INTO Order_status(status_name) VALUES ('delivered');
INSERT INTO Order_status(status_name) VALUES ('closed');

INSERT INTO Addresses(country, city, address, zip)
VALUES('Hungary', 'Zalaegerszeg', 'Nekeresdi ut 26.', '8900');

INSERT INTO Orders(address_id, status_name) VALUES(1, 'new');

INSERT INTO Line_items (product_id, quantity, price, order_id)
VALUES (1, 1, 55, 1);


