package crawler.mat.page;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class ProductSelector {
    /** product code */
    @Selector(value=".productMainLink", attr = "href", format = "(\\d+)$")
    public String code ;
    /** product ttile */
    @Selector(value=".productMainLink .lister__item__title")
    public String brand;
    /** product description */
    @Selector(value=".productMainLink .lister__item__details")
    public String description;
    /* product price - string like $ 456 */
    @Selector(value=".productMainLink .lister__item__price-full")
    public String lister__item__price_full ;
    @Selector(value=".productMainLink .lister__item__price-down")
    public String lister__item__price_down ;
//    @Selector(value=".productMainLink .lister__item__price strike")
//    public transient String strike_price ;
    //@Selector(value = "lister__item__inner, a",  attr="href")
    //public transient String detailPageUrl;
    /** product size */
    @Selector(value=".sizes li:not(.noStock)")
    public List<String> sizes ;
    /** no stock size  */
    @Selector(value=".sizes .noStock")
    public List<String> noStockSize ;
    /** product url */
    @Selector(value=".productMainLink", attr = "href")
    public String productUrl ;

    public ProductSelector() {
    }

    @Override
    public String toString() {
        return "ProductSelector{" +
                "code='" + code + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
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
