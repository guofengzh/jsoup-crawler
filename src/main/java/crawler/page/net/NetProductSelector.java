package crawler.page.net;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

@Selector(value=".products")
public class NetProductSelector {
    /** product code */
    public String code ;
    /** product ttile */
    public String title ;
    /** product details */
    @Selector(value=".description > br")
    public String details;
    /* product price - string like $ 456 */
    @Selector(value="..description > span")
    public String lister__item__price_full ;
    public String lister__item__price_down ;
    /** product size */
    public List<String> sizes ;
    /** no stock size  */
    public List<String> noStockSize ;

    /** product url */
    @Selector(value=".product-image > a", attr = "href")
    public String productUrl ;

    public NetProductSelector() {
    }

    @Override
    public String toString() {
        return "ProductSelector{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", lister__item__price_full='" + lister__item__price_full + '\'' +
                ", lister__item__price_down='" + lister__item__price_down + '\'' +
//                ", strike_price='" + strike_price + '\'' +
//                ", detailPageUrl='" + detailPageUrl + '\'' +
                ", sizes=" + sizes +
                ", noStockSize=" + noStockSize +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }
}
