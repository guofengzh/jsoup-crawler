package crawler.net.crawling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawler.Utils;
import crawler.net.model.ProductNet;
import crawler.net.page.NetBrandPage;
import crawler.net.page.NetProductDetailPage;
import crawler.net.page.NetProductListPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class NetCrawingProducts {
    final static Logger logger = LoggerFactory.getLogger(NetCrawingProducts.class);

    public static final int DELAY = 5 ;
    public static final int MOD = 4 ;
    public static final int ONE_SECOND = 1000 ;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private NetCrawingProductSizes netCrawingProductSizes ;

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<NetProductListPage> htmlPageAdapter = jspoon.adapter(NetProductListPage.class);
    private final Map<String, ProductNet> productMap = new HashMap<>() ;

    public List<ProductNet> getCrawledProducts() {
        return new ArrayList<>(productMap.values()) ;
    }

    public void crawle(NetBrandPage brands ) {
        crawle(brands.clothing) ;
        crawle(brands.shoes) ;
        crawle(brands.bags) ;
        crawle(brands.accessories) ;
        crawle(brands.jewellery) ;
        crawle(brands.lingerie) ;
        crawle(brands.beauty) ;
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
                logger.error(e.getMessage(), e);
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
        String nextPage = NetProductListPage.getFirstPage(brand) ;
        String referrer = NetProductListPage.base ;
        logger.info("NetCrawingProducts::Crawling " + brand) ;
        NetProductListPage productPage = null ;
        do {
            delay( nextPage) ;
            logger.info("NetCrawingProducts::Crawling product " +  nextPage);
            productPage = doCrawle(nextPage, referrer);
            proessSelectedProducts(brand, productPage);
            referrer = nextPage ;
            nextPage = NetProductListPage.getNextPageUrl(nextPage, productPage.nextPage) ;
        } while (productPage.hasNextPage()) ;
        logger.info("Crawling done " +  brand);
    }

    /**
     * crawling one page
     *
     * @param url
     * @return
     * @throws IOException
     */
    public NetProductListPage doCrawle(String url, String referer) throws IOException {
        //logger.info("NetCrawingProducts:referrer:" + referer);
        Connection.Response response = null;
        // org.jsoup.HttpStatusException: HTTP error fetching URL
        // it doesn't want you to scrape their site for data.
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .referrer(referer)
                .followRedirects(true)
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("NetCrawingProducts:Status Code: " + statusCode + " at page " + url); ;
            return new NetProductListPage() ;
        }
        String htmlBodyContent = response.body() ;
        NetProductListPage page = htmlPageAdapter.fromHtml(htmlBodyContent);

        // crawling product sizes on the linked pages
        crawleProductSizes(page) ;

        return page ;
    }

    private void crawleProductSizes(NetProductListPage productPage) throws IOException {
        for (NetProductListPage.ProductDivision productDivision : productPage.products) {
            String prodcutUrl = NetProductListPage.ProductDivision.getProdcutFullUrl(productDivision.productUrl) ;
            delay( prodcutUrl) ;
            logger.info("NetCrawingProducts::Crawling size " +  prodcutUrl);
            NetProductDetailPage productDetailPage = netCrawingProductSizes.crawle(prodcutUrl, "https://www.net-a-porter.com");
            productDivision.sizes = new ArrayList<>();
            productDivision.noStockSize = new ArrayList<>();
            if (!productDetailPage.options.equalsIgnoreCase("NO_VALUE")) {
                List<NetProductDetailPage.OptionJson> options = mapper.readValue(productDetailPage.options, new TypeReference<List<NetProductDetailPage.OptionJson>>() {});
                for (NetProductDetailPage.OptionJson optionJson : options ) {
                    if ( optionJson.stockLevel.equalsIgnoreCase("Out_of_Stock")) {
                        productDivision.noStockSize.add(optionJson.displaySize) ;
                    } else {
                        productDivision.sizes.add(optionJson.displaySize) ;
                    }
                }
            }
        }
    }

    private void proessSelectedProducts(String brand, NetProductListPage productListPage) {
        int n = brand.indexOf("?") ;
        String[] split = brand.substring(0, n).split("/") ;
        String lastSegment = split[split.length - 1] ;

        for ( NetProductListPage.ProductDivision slectedProduct : productListPage.products) {
            ProductNet product = new ProductNet() ;
            product.code = slectedProduct.code.trim() ;
            product.description = slectedProduct.details ;
            product.brand = slectedProduct.designer ;
            product.sizes = slectedProduct.sizes ;
            product.productUrl = slectedProduct.productUrl ;
            product.price = Utils.toDouble(slectedProduct.price);
            product.product_Broken_Size = slectedProduct.noStockSize ;
            // set categories
            // first check if this products has bee crawled by other categories
            if ( !productMap.containsKey(product.code)) {
                productMap.put(product.code, product) ;
            }
            ProductNet p =  productMap.get(product.code) ;
            if ( !p.categories.contains(lastSegment))
                productMap.get(product.code).categories.add(lastSegment) ;
        }
    }

    private void delay(String nextPage) {
        long t = System.currentTimeMillis() % MOD;
        long w = (DELAY + t) * ONE_SECOND;
        logger.info("NetCrawingProducts::waiting " + w + " to crawle " + nextPage);
        try {
            Thread.sleep(w); // random stop sometime
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
