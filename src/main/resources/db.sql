USE bazaSklep;
CREATE TABLE product_types(
    TypeID INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY,
    TypeName VARCHAR(255)
);
CREATE TABLE products(
    ProductID INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY,
     ProductName VARCHAR(255) NOT NULL,
     Price INTEGER,
     TypeID INTEGER FOREIGN KEY REFERENCES product_types(TypeID),
     Quantity INTEGER,
);

INSERT INTO products
VALUES ('Samsung 55"', 2999, 1, 1);

INSERT INTO product_types
VALUES ('TV');
INSERT INTO product_types
VALUES ('Laptop'),('Smartphone'),('Headphones'),('Refrigerator');