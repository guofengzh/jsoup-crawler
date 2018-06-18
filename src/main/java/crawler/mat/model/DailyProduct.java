package crawler.mat.model;

import crawler.ListStringConverter;
import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AttributeAccessor("field")
public class DailyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    /** product code */
    public String code ;
    /** product ttile */
    public String brand;
    /** product description */
    public String description;
    // product categories
    @Convert(converter = ListStringConverter.class)
    public List<String> categories;
    /* product price */
    public double price ;
    /** product size */
    @Convert(converter = ListStringConverter.class)
    public List<String> sizes ;
    /** no stock size  */
    @Convert(converter = ListStringConverter.class)
    public List<String> broken_Size ;
    /** product url */
    @Column(name = "product_url")
    public String productUrl ;
    public Date created_at ;

    public DailyProduct() {
    }

    @Override
    public String toString() {
        return "DailyProduct{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", price=" + price +
                ", sizes=" + sizes +
                ", broken_Size=" + broken_Size +
                ", productUrl='" + productUrl + '\'' +
                ", create_by=" + created_at +
                '}';
    }
}
