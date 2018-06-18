CREATE DATABASE crawler;

CREATE USER 'crawler'@'localhost' IDENTIFIED BY 'suPer$Usdr';
CREATE USER 'crawler'@'%' IDENTIFIED BY 'suPer$Usdr';

GRANT ALL PRIVILEGES ON crawler.* TO 'crawler'@'localhost';
GRANT ALL PRIVILEGES ON crawler.* TO 'crawler'@'%';

use crawler;

CREATE TABLE crawler_data_cn (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description    VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    Product_Live VARCHAR(30),
    Product_Live_Date Date,
    Product_Soldout_Date Date,
    Product_Broken_Size VARCHAR(255),
    Product_Last_Broken_Size VARCHAR(255),
    Product_Broken_Size_Date Date,
    sale_off_rate decimal(10,2),
    last_price      decimal(10,2),
    sale_off_rate_date Date,
    Product_restock VARCHAR(255),
    Product_restock_Date Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE crawler_data_hk (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description    VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    Product_Live VARCHAR(30),
    Product_Live_Date Date,
    Product_Soldout_Date Date,
    Product_Broken_Size VARCHAR(255),
    Product_Last_Broken_Size VARCHAR(255),
    Product_Broken_Size_Date Date,
    sale_off_rate decimal(10,2),
    last_price      decimal(10,2),
    sale_off_rate_date Date,
    Product_restock VARCHAR(255),
    Product_restock_Date Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE crawler_data_us (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description    VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    Product_Live VARCHAR(30),
    Product_Live_Date Date,
    Product_Soldout_Date Date,
    Product_Broken_Size VARCHAR(255),
    Product_Last_Broken_Size VARCHAR(255),
    Product_Broken_Size_Date Date,
    sale_off_rate decimal(10,2),
    last_price      decimal(10,2),
    sale_off_rate_date Date,
    Product_restock VARCHAR(255),
    Product_restock_Date Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE crawler_daily_data_cn (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description    VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    broken_Size VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    created_at Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE crawler_daily_data_hk (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    broken_Size VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    created_at Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE crawler_daily_data_us (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    brand      VARCHAR(255),
    description    VARCHAR(255),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    broken_Size VARCHAR(255),
    categories  VARCHAR(255),
    product_url VARCHAR(255),
    created_at Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
