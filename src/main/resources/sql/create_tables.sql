CREATE TABLE IF NOT EXISTS Currencies (
  id INTEGER PRIMARY KEY,
  currency_name VARCHAR(4)
);

CREATE TABLE IF NOT EXISTS Categories (
  id INTEGER PRIMARY KEY,
  category_name VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS Suppliers(
  id INTEGER PRIMARY KEY,
  supplier_name VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS Products(
  id INTEGER PRIMARY KEY,
  default_price INTEGER,
  currency_id INTEGER,
  category_id INTEGER,
  supplier_id INTEGER,
  FOREIGN KEY (currency_id) REFERENCES Currencies(id),
  FOREIGN KEY (category_id) REFERENCES Categories(id),
  FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

CREATE TABLE IF NOT EXISTS Order_status(
  id INTEGER PRIMARY KEY,
  status_name VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS Addresses(
  id INTEGER PRIMARY KEY,
  country VARCHAR(100),
  city VARCHAR(100),
  address VARCHAR(100),
  zip VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS Orders(
  id INTEGER PRIMARY KEY,
  status_id INTEGER,
  address_id INTEGER,
  FOREIGN KEY (status_id) REFERENCES Order_status(id),
  FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE IF NOT EXISTS Line_items(
  id INTEGER PRIMARY KEY,
  product_id INTEGER,
  quantity INTEGER,
  price INTEGER,
  order_id INTEGER,
  FOREIGN KEY (product_id) REFERENCES Products(id),
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);

