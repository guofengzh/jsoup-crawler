package crawler;

import org.hibernate.annotations.GenericGenerator;
import pl.droidsonroids.jspoon.annotation.Selector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * table prefix:
 *     matchesfashion: cn, hk, us
 *     net-a-porter: net_cn, net_hk, net_us
 */
@MappedSuperclass
public class GenericProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    /** product code */
    public String code ;
    /** product brand */
    public String brand ;
    /** product description */
    public String description;
    // product categories
    @Convert(converter = ListStringConverter.class)
    public List<String> categories = new ArrayList<>();

    /* product price */
    public double price ;
    /** product size */
    @Convert(converter = ListStringConverter.class)
    public List<String> sizes ;
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

    public GenericProduct() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", brand ='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", price=" + price +
                ", sizes=" + sizes +
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
