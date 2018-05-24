package crawler.page.mat;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class ProductListPage {
    public static final String base = "https://www.matchesfashion.com" ;
    //?sort=cat-curation-list&noOfRecordsPerPage=240&q=
    public static final String queryStringFmt = "?page=%s&noOfRecordsPerPage=240&sort=" ;

    public static String getFirstPage(String seg) {
        return base + seg + String.format(queryStringFmt, 1) ;
    }

    /**
     *
     * @param seg /intl/womens/shop/clothing/activewear or /intl/womens/shop/clothing/activewear?page=2&noOfRecordsPerPage=60&sort=
     * @return https://www.matchesfashion.com/intl/womens/shop/clothing/activewear
     */
    public static String getNextPageUrl(String seg) {
        if ( seg == null || seg.trim().isEmpty()) return null ;
        else  return base + seg ;
    }

    @Selector(value=".redefine__right__pager .next a", attr = "href")
    public transient String nextPage ;

    @Selector(".lister__item")
    public List<ProductSelector> products;
}
