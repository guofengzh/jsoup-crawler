package crawler;

import crawler.persistence.ProductDao;
import crawler.persistence.TableNameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductCrawlerTest {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String stringDate = dateFormat.format(new Date());
    private static String path = "matchesfashion.csv" ;
    private static String backupFile = "matchesfashion-" + stringDate + ".csv" ;

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    @Test
    public void crowleOnePageTest() {
        // load last crawled products
        String url = "https://www.matchesfashion.com/intl/womens/shop/clothing/activewear" ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        try {
            ProductPage productPage = crawingProducts.doCrawle(url) ;
            Assert.assertEquals(productPage.nextPage, "/intl/womens/shop/clothing/activewear?page=2&noOfRecordsPerPage=60&sort=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crowleOneBrandTest() {
        String brand = "/intl/womens/shop/clothing/suits" ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        crawingProducts.crawle(brand) ;
        List<Product> products = crawingProducts.getCrawledProducts() ;
        System.out.println(products) ;
        Assert.assertEquals(242, products.size());
    }

}
