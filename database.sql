--SELECT * FROM test;

--create TABLE categories(
--id BIGINT NOT NULL AUTO_INCREMENT,
--name VARCHAR(100) NOT NULL,
--PRIMARY KEY (id)
--)ENGINE=InnoDb;
--COMMIT;
--SELECT * FROM categories;

--CREATE TABLE products(
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    name VARCHAR(100) NOT NULL,
--    price BIGINT NOT NULL,
--    category_id BIGINT NOT NULL,
--    PRIMARY KEY (id),
--    FOREIGN KEY fk_products_categories(category_id) REFERENCES categories(id)
--)ENGINE = InnoDb;

--COMMIT;

SELECT * FROM products;