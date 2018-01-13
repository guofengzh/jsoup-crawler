package crawler;

import crawler.persistence.ListStringConverter;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.annotations.Table;
import pl.droidsonroids.jspoon.annotation.Selector;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
@AttributeAccessor("field")
public class Product {
    public static final String[] header =
            {"product_code", "title", "details", "price", "sizes", "product_Broken_Size", "product_url",
             "product_Live", "product_Live_Date", "product_Soldout_Date",
              "product_Last_Broken_Size", "product_Broken_Size_Date",
             "sale_off_rate", "sale_off_rate_date", "product_restock", "product_restock_Date" } ;

    /** product code */
    @Selector(value=".productMainLink", attr = "href", format = "(\\d+)")
    @Id
    public String code ;
    /** product ttile */
    @Selector(value=".productMainLink .lister__item__title")
    public String title ;
    /** product details */
    @Selector(value=".productMainLink .details")
    public String details;
    /* product price - string like $ 456 */
    @Selector(value=".productMainLink .lister__item__price-full")
    public transient String lister__item__price ;
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
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", lister__item__price=" + lister__item__price +
//                ", lister__item__slug='" + lister__item__slug + '\'' +
                ", sizes=" + sizes +
                ", noStockSize=" + noStockSize +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }
}
