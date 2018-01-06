package crawler;

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

    public static void main(String[] args ) throws IOException {
        File f = new File("matchesfashion.csv") ;
        CrawingProducts crawingProducts = new CrawingProducts() ;
        List<Product> products = crawingProducts.crawle() ;
        List<Product> lastProducts = new LoadProducts().load() ;
        new Analysis().analyze(products, lastProducts) ;
        new PersistProducts().persist(products);
        Path source = Paths.get(path);
        Path destination = Paths.get(backupFile);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
