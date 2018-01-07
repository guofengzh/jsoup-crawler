package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CrawingProducts {
    public static final int totalPage = 5 ;

    public List<Product> crawle() throws IOException {
        int i ;
        List<Product> allProducts = new ArrayList<>() ;
        for (i = 1 ; i <= totalPage ; i++ ) {
            try {
                System.out.println("Extracting page " + i);
                long t = System.currentTimeMillis() % 5 ;
                Thread.sleep(  (5 + t ) * 1000 ) ; // random stop sometime
            } catch (InterruptedException e) {
                System.out.println("Page " + i) ;
                e.printStackTrace();
            }

            List<Product> products = getPage(i);
            if ( products.isEmpty())
                break ;
            allProducts.addAll(products) ;
        }
        System.out.println("Total products " + allProducts.size() + " on " + (i-1) + " pages") ;
        return allProducts;
    }

    public static final String fmt = "https://www.matchesfashion.com/us/womens/shop?page=%d&noOfRecordsPerPage=120&sort=" ;
    public static List<Product> getPage(int i ) throws IOException {
        String url = String.format(fmt, i) ;
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(10000)
                .execute();
        int statusCode = response.statusCode();
        List<Product> products = new ArrayList<>() ;
        if ( statusCode != 200 ) {
            System.out.println("Status Code: " + statusCode + " at page " + i) ;
            return products ;
        }
        Document doc = response.parse();
        Elements lister__item__innerElements = doc.getElementsByClass("lister__item__inner") ;
        for (Element element : lister__item__innerElements ) {
            Product product = makeProduct(element) ;
            products.add(product) ;
        }

        return products ;
    }

    public static Product makeProduct(Element element) {
        Element productMainLink = element.getElementsByClass("productMainLink").first() ;
        Product product = processProductMainLink(productMainLink) ;
        Element sizesElement = element.getElementsByClass("sizes").first() ;
        List<String> sizes = getSizes(sizesElement) ;
        List<String> noStockSizes = getNoStockSizes(sizesElement) ;
        String productUrl = product.productUrl ;
        product.code = productUrl.substring(productUrl.lastIndexOf("-") + 1) ;
        product.sizes = sizes;
        product.noStockSize = noStockSizes ;
        return product ;
    }

    public static Product processProductMainLink(Element content) {
        String url = content.attr("href") ;
        String title = content.getElementsByClass("lister__item__title").text() ;
        String lister__item__details = content.getElementsByClass("lister__item__details").text() ;
        Double lister__item__price = Utils.toDouble(content.getElementsByClass("lister__item__price").text()) ;
        String lister__item__slug = content.getElementsByClass("lister__item__slug").text() ;
        Product product = new Product(title, lister__item__details, lister__item__price, lister__item__slug) ;
        product.productUrl = url ;
        return product ;
    }

    public static List<String> getNoStockSizes(Element element) {
        Elements liElements = element.getElementsByTag("li") ;
        List<String> sizes = new ArrayList<>() ;
        for ( Element oneElement : liElements ) {
            String noStock = oneElement.attr("class"); // "noStock"
            Element anchorElement = oneElement.getElementsByTag("a").first() ;
            String sizeText = anchorElement.text() ;
            if ( noStock != null && !noStock.isEmpty())
                sizes.add(sizeText) ;
        }
        return sizes ;
    }

    public static List<String> getSizes(Element element) {
        Elements liElements = element.getElementsByTag("li") ;
        List<String> sizes = new ArrayList<>() ;
        for ( Element oneElement : liElements ) {
            String noStock = oneElement.attr("class"); // "noStock"
            Element anchorElement = oneElement.getElementsByTag("a").first() ;
            String sizeText = anchorElement.text() ;
            if ( noStock == null  || noStock.isEmpty())
                sizes.add(sizeText) ;
        }
        return sizes ;
    }
}
