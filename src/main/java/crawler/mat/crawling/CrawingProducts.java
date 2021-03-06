package crawler.mat.crawling;

import crawler.Utils;
import crawler.mat.page.BrandListPage;
import crawler.mat.model.Product;
import crawler.mat.page.ProductListPage;
import crawler.mat.page.ProductSelector;
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
            try {
                crawle(brand) ;
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    /**
     * crawling one brand
     *
     * @param brand
     * @return
     */
    public void crawle(String brand) throws Exception {
        String nextPage = ProductListPage.getFirstPage(brand) ;
        String referrer = "https://www.matchesfashion.com/intl/womens/shop" ;
        logger.info("Crawling " + brand) ;
        ProductListPage productPage = null ;
        do {
            long t = System.currentTimeMillis() % MOD;
            long w = (DELAY + t) * ONE_SECOND;
            logger.info("waiting " + w + " to crawle " + nextPage);
            Thread.sleep(w); // random stop sometime
            logger.info("Crawling " +  nextPage);

            productPage = doCrawle(nextPage, referrer);
            proessSelectedProducts(brand, productPage);
            if (productPage.nextPage == null ||
                    productPage.nextPage.trim().isEmpty()||
                    productPage.nextPage.equalsIgnoreCase("NO_VALUE"))
                break ;
            referrer = nextPage ;
            nextPage = ProductListPage.getNextPageUrl(productPage.nextPage) ;
        } while (nextPage != null ) ;
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
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.63 Safari/537.36")
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
            product.brand = slectedProduct.brand;
            product.description = slectedProduct.description;
            product.sizes = slectedProduct.sizes ;
            product.productUrl = slectedProduct.productUrl ;

            if (!slectedProduct.lister__item__price_down.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(slectedProduct.lister__item__price_down);
            } else if (!slectedProduct.lister__item__price_full.equalsIgnoreCase("NO_VALUE")) {
                product.price = Utils.toDouble(slectedProduct.lister__item__price_full);
            } else {
                logger.info("No price crawled for " + product.description) ;
            }
            product.product_Broken_Size = slectedProduct.noStockSize ;
            // set categories
            // first check if this products has bee crawled by other categories
            if ( !productMap.containsKey(product.code)) {
                productMap.put(product.code, product) ;
            }
            Product p =  productMap.get(product.code) ;
            if ( !p.categories.contains(lastSegment))
                productMap.get(product.code).categories.add(lastSegment) ;
        }
    }
}
