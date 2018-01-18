package crawler;

import crawler.persistence.ProductDao;
import crawler.persistence.TableNameUtils;
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

public class CrawlerTest {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String stringDate = dateFormat.format(new Date());
    private static String path = "matchesfashion.csv" ;
    private static String backupFile = "matchesfashion-" + stringDate + ".csv" ;

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args ) {
        // load last crawled products
        System.setProperty("total", "1") ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        try {
            List<Product> products = crawingProducts.getPage(1) ;
            products.stream().forEach(p->{
                System.out.println(p);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
