package crawler.net.page;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class NetProductListPage {
    public static final String base = "https://www.net-a-porter.com" ;
    public static final String queryStringFmt = "?cm_sp=topnav-_-clothing-_-beachwear&pn=%d&npp=60&image_view=product&dScroll=0" ;

    public static String getFirstPage(String seg) {
        return base + seg + String.format(queryStringFmt, 1) ;
    }

    public static String getNextPageUrl(String seg) {
        if ( seg == null || seg.trim().isEmpty()) return null ;
        else  return base + seg ;
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
        @Selector(value=".description a", attr = "href", format = "/product/(\\d+)/")
        public String code ;
        /** product description */
        @Selector(value=".description a", attr = "brand")
        public String details;

        @Selector(value=".description a .designer")
        public String designer ;

        /* product price - string like $456 */
        @Selector(value=".description .price")
        public String price ;

        /** product url */
        @Selector(value=".description > a", attr = "href")
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
