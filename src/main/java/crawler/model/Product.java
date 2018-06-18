package crawler.model;

import crawler.dao.ListStringConverter;
import org.hibernate.annotations.AttributeAccessor;
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
@Entity
@AttributeAccessor("field")
public class Product extends GenericProduct{
}
