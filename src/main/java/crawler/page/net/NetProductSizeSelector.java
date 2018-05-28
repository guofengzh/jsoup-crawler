package crawler.page.net;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

@Selector(value=".products")
public class NetProductSizeSelector {
    /** product url */
    @Selector(value=".product-image > a", attr = "href")
    public String productUrl ;
    /** product details */
    @Selector(value=".description > br")
    public String details;
    /* product price - string like $ 456 */
    @Selector(value=".description .price")
    public String lister__item__price_full ;
    /* slug */
    @Selector(value=".description .slug")
    public String slug ; // SOLD AS A SET, Sold Out

    public NetProductSizeSelector() {
    }

    @Override
    public String toString() {
        return "NetProductSizeSelector{" +
                "productUrl='" + productUrl + '\'' +
                ", details='" + details + '\'' +
                ", lister__item__price_full='" + lister__item__price_full + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
