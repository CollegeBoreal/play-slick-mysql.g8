# products schema

# --- !Ups

CREATE TABLE PRODUCTS (
  `product` INT NOT NULL AUTO_INCREMENT COMMENT '	',
  `sku` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `updated` DATETIME NULL,
  PRIMARY KEY (`product`)
);

# --- !Downs

DROP TABLE PRODUCTS;
