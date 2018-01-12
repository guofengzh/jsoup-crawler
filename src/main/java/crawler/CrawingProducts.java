package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CrawingProducts {
    public static final int totalPage = 250 ;
    public static final String fmt = "https://www.matchesfashion.com/us/womens/shop?page=%d&noOfRecordsPerPage=120&sort=" ;

    Jspoon jspoon = Jspoon.create();
    HtmlAdapter<Page> htmlAdapter = jspoon.adapter(Page.class);

    final static Logger logger = LoggerFactory.getLogger(CrawingProducts.class);

    public List<Product> crawle() throws IOException {
        int i ;
        List<Product> allProducts = new ArrayList<>() ;
        for (i = 1 ; i <= totalPage ; i++ ) {
            try {
                System.out.println("Crawling page " + i);
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
        logger.info("Total products " + allProducts.size() + " on " + (i-1) + " pages"); ;
        return allProducts;
    }

    public List<Product> getPage(int i ) throws IOException {
        String url = String.format(fmt, i) ;
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(10000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            System.out.println("Status Code: " + statusCode + " at page " + i) ;
            return new ArrayList<>() ;
        }
        String htmlBodyContent = response.body() ;
        Page page = htmlAdapter.fromHtml(htmlBodyContent);

        return page.products ;
    }
}
