CREATE TABLE IF NOT EXISTS T_PRODUCT_STOCK (
	ID VARCHAR(64) PRIMARY KEY NOT NULL,
	PRODUCTID VARCHAR(64) NOT NULL,
	TOTAL INT,
	UNIT VARCHAR(64) NOT NULL,
	WARNING INT
);