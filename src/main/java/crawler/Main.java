package crawler;

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

public class Main {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String stringDate = dateFormat.format(new Date());
    private static String path = "matchesfashion.csv" ;
    private static String backupFile = "matchesfashion-" + stringDate + ".csv" ;

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args ) throws IOException {
        File f = new File("matchesfashion.csv") ;

        logger.info("test---------");
        // crawling
        CrawingProducts crawingProducts = new CrawingProducts() ;
        List<Product> products = crawingProducts.crawle() ;

        // load last crawled products
        File lastFile = new File(path) ;
        if (lastFile.exists()) {
            List<Product> lastProducts = new LoadProducts().load(new File(path));
            // analysis
            new Analysis().analyze(products, lastProducts) ;

            // backup
            Path source = Paths.get(path);
            Path destination = Paths.get(backupFile);
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        // persist new products
        new PersistProducts().persist(products, lastFile);
    }
}
