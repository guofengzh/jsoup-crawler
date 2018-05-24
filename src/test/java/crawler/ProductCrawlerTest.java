package crawler;

import crawler.model.Product;
import crawler.page.mat.ProductListPage;
import crawler.service.CrawingProducts;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            ProductListPage productPage = crawingProducts.doCrawle(url, "https://www.matchesfashion.com/intl/womens/shop") ;
            Assert.assertEquals(productPage.nextPage, "/intl/womens/shop/clothing/activewear?page=2&noOfRecordsPerPage=60&sort=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crowleOneBrandTest() {
        String brand = "/intl/womens/shop/clothing/jackets" ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        crawingProducts.crawle(brand) ;
        List<Product> products = crawingProducts.getCrawledProducts() ;
        System.out.println(products) ;
        Assert.assertEquals(695, products.size());
    }

}
