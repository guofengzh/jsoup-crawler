package crawler;

import com.univocity.parsers.annotations.NullString;
import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class Product {
    public static final String[] header =
            {"product_code", "title", "details", "price", "sizes", "sizes_in_short", "product_url",
             "on_off_shelf", "on_shelf_date", "off_shelf_date",
              "sizes_in_short_last", "sizes_in_short_date",
             "sale_off_rate", "sale_off_rate_date", "complements", "complements_date" } ;

    /** product code */
    @Selector(value=".productMainLink", attr = "href", format = "(\\d+)")
    public String code ;
    /** product ttile */
    @Selector(value=".productMainLink .lister__item__title")
    public String title ;
    /** product details */
    @Selector(value=".productMainLink .lister__item__details")
    public String lister__item__details ;
    /* product price - like $ 456 */
    @Selector(value=".productMainLink .lister__item__price-full")
    private String lister__item__price ;
    /** Runaway */
    @Selector(value=".productMainLink .lister__item__slug")
    public String lister__item__slug ;
    /** product size */
    @Selector(value=".sizes li:not(.noStock)")
    public List<String> sizes ;
    /** no stock size */
    @Selector(value=".sizes .noStock")
    public List<String> noStockSize ;
    /** product url */
    @Selector(value=".productMainLink", attr = "href")
    public String productUrl ;
    /* --------------- 分析结果 -------------------*/
    /* 架日期，下架日期，是否新品，降价幅度，断码，补码 */
    public String on_off_shelf  = "" ; //是上架（true)，还是下架(false)
    /** 最新上架日期 */
    public String on_shelf_date ;
    /** 最新下架日期 */
    public String off_shelf_date ;
    /** 断码码列表 */
    public List<String> sizes_in_short ;
    /**最近一次发生的断码*/
    public List<String> sizes_in_short_last ;
    /**最近一次发生代码缺失变化的时间*/
    public String sizes_in_short_date ;
    /** 降价(-)或升价的(+)% */
    public Double sale_off_rate ;
    /** 价钱变化 的时间 */
    public String sale_off_rate_date ;
    /** 新补码的列表 */
    public List<String> complements ;
    /** 补码的时间 */
    public String complement_date ;

    public Product() {
    }
    public Double getPrice() {
        return Utils.toDouble(lister__item__price) ;
    }

    /** not use it, use getPrice instead */
    public String getLister__item__price() {
        return lister__item__price;
    }

    public void setLister__item__price(String lister__item__price) {
        this.lister__item__price = lister__item__price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", lister__item__details='" + lister__item__details + '\'' +
                ", lister__item__price=" + lister__item__price +
                ", lister__item__slug='" + lister__item__slug + '\'' +
                ", sizes=" + sizes +
                ", noStockSize=" + noStockSize +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }
}
