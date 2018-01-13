package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.*;
import java.util.*;

public class CrawingProducts {
    public static final int TOTAL_PAGE = 250 ;
    public static final String fmt = "https://www.matchesfashion.com/us/womens/shop?page=%d&noOfRecordsPerPage=120&sort=" ;

    Jspoon jspoon = Jspoon.create();
    HtmlAdapter<Page> htmlAdapter = jspoon.adapter(Page.class);

    final static Logger logger = LoggerFactory.getLogger(CrawingProducts.class);

    public List<Product> crawle() throws IOException {
        String total  = System.getProperty("total") ;
        int totalPage = TOTAL_PAGE ;
        if ( total != null ) {
            totalPage = Integer.parseInt(total) ;
        }
        logger.info("max page: " + totalPage) ;

        int i ;
        List<Product> allProducts = new ArrayList<>() ;
        for (i = 1 ; i <= totalPage ; i++ ) {
            try {
                long t = System.currentTimeMillis() % 5 ;
                long w = (5 + t ) * 1000 ;
                System.out.println("Crawling page " + i + ", first waiting " + w);
                Thread.sleep( w ) ; // random stop sometime
                System.out.println("Starting Crawling page " + i );
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

        // post process
        for (Product product : page.products) {
            //logger.info(product.title+" lister__item__price:" + product.lister__item__price_full + " " + product.lister__item__price_down) ;
            if (!product.lister__item__price_down.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(product.lister__item__price_down);
            } else if (!product.lister__item__price_full.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(product.lister__item__price_full);
            } else {
                logger.info("No price crawled bor " + product.title) ;
            }

            product.product_Broken_Size = product.noStockSize ;
        }
        return page.products ;
    }
}
