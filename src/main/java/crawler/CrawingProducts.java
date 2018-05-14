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
    final static Logger logger = LoggerFactory.getLogger(CrawingProducts.class);
    public static final int DELAY = 5 ;
    public static final int MOD = 4 ;
    public static final int ONE_SECOND = 1000 ;

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<ProductPage> htmlPageAdapter = jspoon.adapter(ProductPage.class);
    private final Map<String, Product> productMap = new HashMap<>() ;

    public List<Product> getCrawledProducts() {
        return new ArrayList<>(productMap.values()) ;
    }

    public void crawle(Brands brands ) {
        crawle(brands.clothing) ;
        crawle(brands.bags) ;
        crawle(brands.shoes) ;
        crawle(brands.jewellery) ;
        crawle(brands.fine__jewellery) ;
        crawle(brands.accessories) ;
    }

    /**
     * crawling all brand of a category (clothing shoes etc.)
     *
     * @param brands
     * @return
     */
    public void crawle(List<String> brands) {
        for (String brand: brands) {
            crawle(brand) ;
        }
    }

    /**
     * crawling one brand
     *
     * @param brand
     * @return
     */
    public void crawle(String brand) {
        String[] split = brand.split("/") ;
        String lastSegment = split[split.length - 1] ;

        ProductPage productPage = new ProductPage() ;
        String nextPage = Product.getNextPageUrl(brand) ;
        int loop = 0 ; // count the error
        do {
            loop = 0 ; // start a new page
            try {
                long t = System.currentTimeMillis() % MOD;
                long w = (DELAY + t) * ONE_SECOND;
                logger.info("waiting " + w + " to crawle " + nextPage);
                Thread.sleep(w); // random stop sometime
                logger.info("Starting Crawling " +  nextPage);

                productPage = doCrawle(nextPage);
                for ( Product product : productPage.products) {
                    // set brands
                    // first check if this products has bee crawled by other brands
                    if ( !productMap.containsKey(product.code)) {
                        productMap.put(product.code, product) ;
                    }
                    productMap.get(product.code).brands.add(lastSegment) ;
                }
            } catch (Throwable e) {
                loop++ ;  // this page has error occurred, increase it
                logger.error(loop + ": brand " + brand, e);
            }
            if (productPage.nextPage.equalsIgnoreCase("NO_VALUE"))
                break ;
            nextPage = Product.getNextPageUrl(productPage.nextPage) ;
        } while (nextPage != null && loop < 4 ) ;
    }

    /**
     * crawling one page
     *
     * @param url
     * @return
     * @throws IOException
     */
    public ProductPage doCrawle(String url) throws IOException {
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("Status Code: " + statusCode + " at page " + url); ;
            return new ProductPage() ;
        }
        String htmlBodyContent = response.body() ;
        ProductPage page = htmlPageAdapter.fromHtml(htmlBodyContent);

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
        return page ;
    }
}
