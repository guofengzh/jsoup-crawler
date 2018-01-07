package crawler;

import com.univocity.parsers.annotations.NullString;

import java.util.List;

public class Product {
    public static final String[] header =
            {"product_code", "title", "details", "price", "sizes", "product_url",
             "on_off_shelf", "on_shelf_date", "off_shelf_date", "sizes_in_short", "sizes_in_short_date",
             "sale_off_rate", "complements", "complements_date" } ;
    /** product code */
    public String code ;
    /** product ttile */
    public String title ;
    /** product details */
    public String lister__item__details ;
    /* product price - like $ 456 */
    public Double lister__item__price ;
    /** Runaway */
    public String lister__item__slug ;
    /** product size */
    public List<String> sizes ;
    /** no stock size */
    public List<String> noStockSize ;
    /** product url */
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
    /**最近一次发生代码缺失变化的时间*/
    public String sizes_in_short_date ;
    /** 降价(-)或升价的(+)% */
    public Double sale_off_rate ;
    /** 新补码的列表 */
    public List<String> complements ;
    /** 补码的时间 */
    public String complement_date ;

    public Product() {
    }

    public Product(String title, String lister__item__details, Double lister__item__price, String lister__item__slug) {
        this.title = title;
        this.lister__item__details = lister__item__details;
        this.lister__item__price = lister__item__price;
        this.lister__item__slug = lister__item__slug;
    }

}
