package crawler.model;

import org.hibernate.annotations.AttributeAccessor;

import javax.persistence.Entity;

/**
 * table prefix:
 *     matchesfashion: cn, hk, us
 *     net-a-porter: net_cn, net_hk, net_us
 */
@Entity
@AttributeAccessor("field")
public class ProductNet extends GenericProduct{
}
