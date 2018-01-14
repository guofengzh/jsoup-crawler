CREATE DATABASE crawler;

CREATE USER 'crawler'@'localhost' IDENTIFIED BY 'suPer$Usdr';
CREATE USER 'crawler'@'%' IDENTIFIED BY 'suPer$Usdr';

GRANT ALL PRIVILEGES ON crawler.* TO 'crawler'@'localhost';
GRANT ALL PRIVILEGES ON crawler.* TO 'crawler'@'%';

use crawler;

CREATE TABLE crawler_data_cn (
    id   bigint(32) NOT NULL AUTO_INCREMENT,
    code VARCHAR(30) COMMENT '',
    title      VARCHAR(255),
    details    VARCHAR(30),
    price      decimal(10,2),
    sizes      VARCHAR(255),
    product_url VARCHAR(255),
    Product_Live VARCHAR(30),
    Product_Live_Date Date,
    Product_Soldout_Date Date,
    Product_Broken_Size VARCHAR(255),
    Product_Last_Broken_Size VARCHAR(255),
    Product_Broken_Size_Date Date,
    sale_off_rate decimal(6,2),
    sale_off_rate_date Date,
    Product_restock VARCHAR(255),
    Product_restock_Date Date,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
