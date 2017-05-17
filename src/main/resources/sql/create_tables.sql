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

CREATE TYPE Order_status AS ENUM ('new', 'hold', 'paid', 'shipped', 'delivered', 'closed');

CREATE TABLE IF NOT EXISTS Addresses(
  id SERIAL PRIMARY KEY,
  country VARCHAR(100),
  city VARCHAR(100),
  address VARCHAR(100),
  zip VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS Orders(
  id SERIAL PRIMARY KEY,
  order_name VARCHAR(40),
  status Order_status,
  address_id SERIAL,
  FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE IF NOT EXISTS Line_items(
  id SERIAL PRIMARY KEY,
  product_id SERIAL,
  quantity INTEGER,
  price INTEGER,
  order_id SERIAL,
  FOREIGN KEY (product_id) REFERENCES Products(id),
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);
