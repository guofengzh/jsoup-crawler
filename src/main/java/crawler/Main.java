package crawler;

import crawler.persistence.DailyProductDao;
import crawler.persistence.ProductDao;
import crawler.persistence.TableNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String stringDate = dateFormat.format(new Date());
    private static String path = "matchesfashion.csv" ;
    private static String backupFile = "matchesfashion-" + stringDate + ".csv" ;

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args ) {
        try {
            logger.info("Starting crawling products") ;
            if (args.length != 1 ) {
                System.out.println("Usage: java -jar jafile.jar tablename" ) ;
                System.exit(-1);
            }
            TableNameUtils.setTableNamePostfix(args[0]);
            new Main().runDb() ;
            logger.info("Starting crawling products - Done") ;
        } catch (Throwable t ) {
            logger.error(t.getMessage(), t);
            System.exit(-1);
        } finally {
            ProductDao.closeSessionFactory();
        }
        System.exit(0);
    }

    /**
     * store/load data from db
     *
     * @throws Exception
     */
    private void runDb() throws Exception {
        // load last crawled products
        List<Product> lastProducts =ProductDao.loadAll() ;

        // crawling
        CrawingBrands crawingBrands = new CrawingBrands() ;
        Brands brands = crawingBrands.crawle() ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        crawingProducts.crawle(brands) ;
        List<Product> products = crawingProducts.getCrawledProducts() ;

        if (!lastProducts.isEmpty()) {
            // analysis
            new Analysis().analyze(products, lastProducts) ;
        }

        ProductDao.save(products);
        DailyProductDao.save(makeDailyProductFromProduct(products));
    }

    public static List<DailyProduct> makeDailyProductFromProduct(List<Product> products) {
        List<DailyProduct> dailyProducts = new ArrayList<>(products.size()) ;
        Date createdAt = new Date() ;
        for (Product product : products) {
            DailyProduct dailyProduct = new DailyProduct() ;
            dailyProduct.code = product.code ;
            dailyProduct.title = product.title ;
            dailyProduct.details = product.details ;
            dailyProduct.brands = product.brands ;
            dailyProduct.price = product.price ;
            dailyProduct.sizes = product.sizes ;
            dailyProduct.broken_Size = product.product_Broken_Size ;
            dailyProduct.productUrl = product.productUrl ;
            dailyProduct.created_at = createdAt ;

            dailyProducts.add(dailyProduct) ;
        }
        return dailyProducts ;
    }
}
