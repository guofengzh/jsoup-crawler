package crawler;

import crawler.persistence.ListStringConverter;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;
import pl.droidsonroids.jspoon.annotation.Selector;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeAccessor("field")
public class Product {
    public static final String[] header =
            {"product_code", "title", "details", "price", "sizes", "product_Broken_Size", "product_url",
             "product_Live", "product_Live_Date", "product_Soldout_Date",
              "product_Last_Broken_Size", "product_Broken_Size_Date",
             "sale_off_rate", "sale_off_rate_date", "product_restock", "product_restock_Date" } ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    /** product code */
    @Selector(value=".productMainLink", attr = "href", format = "(\\d+)$")
    public String code ;
    /** product ttile */
    @Selector(value=".productMainLink .lister__item__title")
    public String title ;
    /** product details */
    @Selector(value=".productMainLink .lister__item__details")
    public String details;
    /* product price - string like $ 456 */
    @Selector(value=".productMainLink .lister__item__price-full")
    public transient String lister__item__price_full ;
    @Selector(value=".productMainLink .lister__item__price-down")
    public transient String lister__item__price_down ;
    @Selector(value=".productMainLink .lister__item__price strike")
    public transient String strike_price ;

    @Selector(value = "lister__item__inner, a",  attr="href")
    public transient String detailUrl ;

    /* product price */
    public double price ;
    /** Runaway */
    //@Selector(value=".productMainLink .lister__item__slug")
    //public String lister__item__slug ;
    /** product size */
    @Selector(value=".sizes li:not(.noStock)")
    @Convert(converter = ListStringConverter.class)
    public List<String> sizes ;
    /** no stock size  */
    @Selector(value=".sizes .noStock")
    public transient List<String> noStockSize ;
    /** product url */
    @Selector(value=".productMainLink", attr = "href")
    @Column(name = "product_url")
    public String productUrl ;
    /* --------------- 分析结果 -------------------*/
    /* 架日期，下架日期，是否新品，降价幅度，断码，补码 */
    public String product_Live = "" ; //是上架（true)，还是下架(false)
    /** 最新上架日期 */
    public Date product_Live_Date;
    /** 最新下架日期 */
    public Date product_Soldout_Date;
    /** 断码码列表 */
    @Convert(converter = ListStringConverter.class)
    public List<String> product_Broken_Size;
    /**最近一次发生的断码*/
    @Convert(converter = ListStringConverter.class)
    public List<String> product_Last_Broken_Size;
    /**最近一次发生代码缺失变化的时间*/
    public Date product_Broken_Size_Date;
    /** 降价(-)或升价的(+)% */
    public Double sale_off_rate ;
    /** 上次的价格 */
    public Double last_price ;
    /** 价钱变化 的时间 */
    public Date sale_off_rate_date ;
    /** 新补码的列表 */
    @Convert(converter = ListStringConverter.class)
    public List<String> product_restock;
    /** 补码的时间 */
    public Date product_restock_Date;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", lister__item__price_full='" + lister__item__price_full + '\'' +
                ", lister__item__price_down='" + lister__item__price_down + '\'' +
                ", strike_price='" + strike_price + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                ", price=" + price +
                ", sizes=" + sizes +
                ", noStockSize=" + noStockSize +
                ", productUrl='" + productUrl + '\'' +
                ", product_Live='" + product_Live + '\'' +
                ", product_Live_Date=" + product_Live_Date +
                ", product_Soldout_Date=" + product_Soldout_Date +
                ", product_Broken_Size=" + product_Broken_Size +
                ", product_Last_Broken_Size=" + product_Last_Broken_Size +
                ", product_Broken_Size_Date=" + product_Broken_Size_Date +
                ", sale_off_rate=" + sale_off_rate +
                ", last_price=" + last_price +
                ", sale_off_rate_date=" + sale_off_rate_date +
                ", product_restock=" + product_restock +
                ", product_restock_Date=" + product_restock_Date +
                '}';
    }
}
