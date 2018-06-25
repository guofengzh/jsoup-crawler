package crawler.net.page;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class NetProductListPage {
    public static final String base = "https://www.net-a-porter.com" ;
    public static final String queryStringFmt = "?cm_sp=topnav-_-clothing-_-beachwear&pn=%d&npp=60&image_view=product&dScroll=0" ;

    public static String getFirstPage(String seg) {
        return base + seg + String.format(queryStringFmt, 1) ;
    }

    public static String getNextPageUrl(String prevUrl, String seg) throws Exception {
        if ( seg == null || seg.trim().isEmpty()) return null ;
        int n = prevUrl.lastIndexOf("/") ;
        String urlStr = prevUrl.substring(0, n + 1 ) + seg ;
        return new URI(urlStr).normalize().toURL().toExternalForm();
    }

    @Selector(value=".page-numbers a.next-page", attr = "href")
    public String nextPage ;

    @Selector(value=".page-numbers .pagination-links", attr = "data-lastpage")
    public Integer lastPage ;

    @Selector(value=".page-numbers .pagination-links .pagination-page-current")
    public Integer currentPage ;

    @Selector("#product-list .products li")
    public List<ProductDivision> products;

    public boolean hasNextPage() {
        return currentPage.intValue() != lastPage.intValue() ;
    }

    @Override
    public String toString() {
        return "NetProductListPage{" +
                "nextPage='" + nextPage + '\'' +
                ", lastPage=" + lastPage +
                ", currentPage=" + currentPage + "\n" +
                ", products=" + products +
                '}';
    }

    public static class ProductDivision {
        /** product code */
        @Selector(value="div.description > a", attr = "href", format = "/product/(\\d+)/")
        public String code ;
        /** product description */
        @Selector(value="div.description > a", attr = "title")
        public String details;

        @Selector(value="div.description > a > span")
        public String designer ;

        /* product price - string like $456 */
        @Selector(value="div.description > span.price")
        public String price ;

        /** product url */
        @Selector(value="div.description > a", attr = "href")
        public String productUrl ;

        /** product size */
        public List<String> sizes ;
        /** no stock size  */
        public List<String> noStockSize ;

        public ProductDivision() {
        }

        public static String getProdcutFullUrl(String seg) {
            return base + seg ;
        }

        @Override
        public String toString() {
            return "ProductDivision{" +
                    "code='" + code + '\'' +
                    ", description='" + details + '\'' +
                    ", designer='" + designer + '\'' +
                    ", price='" + price + '\'' +
                    ", productUrl='" + productUrl + '\'' +
                    ", sizes=" + sizes +
                    ", noStockSize=" + noStockSize +
                    '}';
        }
    }
}
