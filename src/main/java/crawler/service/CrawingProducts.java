package crawler.service;

import crawler.Utils;
import crawler.page.mat.BrandListPage;
import crawler.model.Product;
import crawler.page.mat.ProductListPage;
import crawler.page.mat.ProductSelector;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.*;
import java.util.*;

@Component
@Scope("prototype")
public class CrawingProducts {
    final static Logger logger = LoggerFactory.getLogger(CrawingProducts.class);
    public static final int DELAY = 5 ;
    public static final int MOD = 4 ;
    public static final int ONE_SECOND = 1000 ;

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<ProductListPage> htmlPageAdapter = jspoon.adapter(ProductListPage.class);
    private final Map<String, Product> productMap = new HashMap<>() ;

    public List<Product> getCrawledProducts() {
        return new ArrayList<>(productMap.values()) ;
    }

    public void crawle(BrandListPage brands ) {
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
        String nextPage = ProductListPage.getFirstPage(brand) ;
        String referrer = "https://www.matchesfashion.com/intl/womens/shop" ;
        logger.info("Crawling " + brand) ;
        int loop = 0 ; // count the error
        ProductListPage productPage = null ;
        do {
            loop = 0 ; // start a new page
            try {
                long t = System.currentTimeMillis() % MOD;
                long w = (DELAY + t) * ONE_SECOND;
                logger.info("waiting " + w + " to crawle " + nextPage);
                Thread.sleep(w); // random stop sometime
                logger.info("Crawling " +  nextPage);

                productPage = doCrawle(nextPage, referrer);
                proessSelectedProducts(brand, productPage);
            } catch (Throwable e) {
                loop++ ;  // this page has error occurred, increase it
                logger.error(loop + ": brand " + brand, e);
            }
            if (productPage.nextPage == null ||
                    productPage.nextPage.trim().isEmpty()||
                    productPage.nextPage.equalsIgnoreCase("NO_VALUE"))
                break ;
            referrer = nextPage ;
            nextPage = ProductListPage.getNextPageUrl(productPage.nextPage) ;
        } while (nextPage != null && loop < 4 ) ;
        logger.info("Crawling done " +  brand);
    }

    /**
     * crawling one page
     *
     * @param url
     * @return
     * @throws IOException
     */
    public ProductListPage doCrawle(String url, String referer) throws IOException {
        logger.info("referrer:" + referer);
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .referrer(referer)
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("Status Code: " + statusCode + " at page " + url); ;
            return new ProductListPage() ;
        }
        String htmlBodyContent = response.body() ;
        ProductListPage page = htmlPageAdapter.fromHtml(htmlBodyContent);
        return page ;
    }

    private void proessSelectedProducts(String brand, ProductListPage productListPage) {
        String[] split = brand.split("/") ;
        String lastSegment = split[split.length - 1] ;

        for ( ProductSelector slectedProduct : productListPage.products) {
            Product product = new Product() ;
            product.code = slectedProduct.code.trim() ;
            product.title = slectedProduct.title ;
            product.details = slectedProduct.details ;
            product.sizes = slectedProduct.sizes ;
            product.productUrl = slectedProduct.productUrl ;

            if (!slectedProduct.lister__item__price_down.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(slectedProduct.lister__item__price_down);
            } else if (!slectedProduct.lister__item__price_full.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(slectedProduct.lister__item__price_full);
            } else {
                logger.info("No price crawled bor " + product.title) ;
            }

            product.product_Broken_Size = slectedProduct.noStockSize ;
            // set brands
            // first check if this products has bee crawled by other brands
            if ( !productMap.containsKey(product.code)) {
                productMap.put(product.code, product) ;
            }
            Product p =  productMap.get(product.code) ;
            if ( !p.brands.contains(lastSegment))
                productMap.get(product.code).brands.add(lastSegment) ;
        }
    }
}
