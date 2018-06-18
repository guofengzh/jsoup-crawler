package crawler.mat.model;

import crawler.GenericProduct;
import org.hibernate.annotations.AttributeAccessor;

import javax.persistence.*;

/**
 * table prefix:
 *     matchesfashion: cn, hk, us
 *     net-a-porter: net_cn, net_hk, net_us
 */
@Entity
@AttributeAccessor("field")
public class Product extends GenericProduct {
}
