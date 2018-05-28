package crawler.page.net;

import crawler.page.mat.ProductSelector;
import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class NetProductListPage {
    public static final String base = "https://www.net-a-porter.com" ;
    public static final String queryStringFmt = "?cm_sp=topnav-_-clothing-_-beachwear&pn=%d&npp=60&image_view=product&dScroll=0" ;

    public static String getFirstPage(String seg) {
        return base + seg + String.format(queryStringFmt, 1) ;
    }

    /**
     *
     * @param /cn/zh/Shop/Clothing/Tops?cm_sp=topnav-_-clothing-_-tops
     * @return
     */
    public static String getNextPageUrl(String seg) {
        if ( seg == null || seg.trim().isEmpty()) return null ;
        else  return base + seg ;
    }

    @Selector(value=".page-numbers .next-page a", attr = "href")
    public String nextPage ;

    @Selector(value=".page-inks[ .next-page", attr = "data-lastpage")
    public Integer lastPage ;

    @Selector(value=".pagination-page-current")
    public Integer currentPage ;

    @Selector(".products")
    public List<ProductSelector> products;
}
