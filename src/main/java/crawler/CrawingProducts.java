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
    public static final int DELAY = 5 ;
    public static final int MOD = 4 ;
    public static final int ONE_SECOND = 1000 ;
    public static final int TOTAL_PAGE = 1000 ;
    public static final String base_url = "https://www.matchesfashion.com" ;
    public static final String fmt = "https://www.matchesfashion.com/us/womens/shop?page=%d&noOfRecordsPerPage=60&sort=" ;

    Jspoon jspoon = Jspoon.create();
    HtmlAdapter<Page> htmlPageAdapter = jspoon.adapter(Page.class);
    HtmlAdapter<PageDetail> htmlPageDetailAdapter = jspoon.adapter(PageDetail.class);

    final static Logger logger = LoggerFactory.getLogger(CrawingProducts.class);

    public List<Product> crawle() throws IOException {
        String total  = System.getProperty("total") ;
        int totalPage = TOTAL_PAGE ;
        if ( total != null ) {
            totalPage = Integer.parseInt(total) ;
        }
        return crawle(totalPage) ;
    }

    public List<Product> crawle(int totalPage) throws IOException {
        logger.info("max page: " + totalPage);
        int i;
        List<Product> allProducts = new ArrayList<>();
        for (i = 1; i <= totalPage; i++) {
            try {
                long t = System.currentTimeMillis() % MOD;
                long w = (DELAY + t) * ONE_SECOND;
                logger.info("Crawling page " + i + ", first waiting " + w);
                Thread.sleep(w); // random stop sometime
                logger.info("Starting Crawling page " + i);

                List<Product> products = getPage(i);
                if (products.isEmpty())
                    break;
                allProducts.addAll(products);

                crawleDetails(products) ;

            } catch (Throwable e) {
                logger.error("Page " + i, e);
            }
        }
        logger.info("Total products " + allProducts.size() + " on " + (i-1) + " pages"); ;
        return allProducts;
    }

    public void crawleDetails(List<Product> products) {
            int count = 0 ;
            Date begin = new Date() ;
            System.out.println("Detail page begin: " + begin) ;
            for (Product product : products) {
                String pageDetailUrl = base_url + product.detailPageUrl ;
                long t = System.currentTimeMillis() % MOD ;
                long w = (DELAY + t ) * ONE_SECOND ;
                try {
                    count++ ;
                    logger.info(count + " Crawling detail page, first waiting " + w + " "+ pageDetailUrl);
                    Thread.sleep( w ) ;
                    logger.info(count + " Starting Crawling detail page " + pageDetailUrl );
                    List<String> brands = getPageDetail(pageDetailUrl) ;
                    if (!brands.isEmpty())
                      product.brands = brands.subList(1, brands.size()) ;
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info("Detail page done: " + new Date()) ;
            logger.info("Detail page done - taken in seconds: " + (new Date().getTime() - begin.getTime())/1000) ;
    }

    public List<Product> getPage(int i ) throws IOException {
        String url = String.format(fmt, i) ;
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("Status Code: " + statusCode + " at page " + i); ;
            return new ArrayList<>() ;
        }
        String htmlBodyContent = response.body() ;
        Page page = htmlPageAdapter.fromHtml(htmlBodyContent);

        // post process
        for (Product product : page.products) {
            product.code = product.code.trim() ;
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

    public List<String> getPageDetail(String url) throws IOException {
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("Status Code: " + statusCode + " at page " + url); ;
            return new ArrayList<>() ;
        }
        String htmlBodyContent = response.body() ;
        PageDetail pageDetail = htmlPageDetailAdapter.fromHtml(htmlBodyContent);
        return  pageDetail.brands ;
    }
}
