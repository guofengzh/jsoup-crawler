package crawler;

import crawler.persistence.HibernateUtils;
import crawler.persistence.ProductDao;
import crawler.persistence.TableNameUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DBTest {
    @Test
    @Ignore
    public void dbLoad() {
        HibernateUtils.setHibernateConfigFile("hibernate.cfg-test.xml");
        TableNameUtils.setTableName("crawler_data_cn");
        List<Product> products = ProductDao.loadAll() ;
        Assert.assertEquals(products.size(), 1);
        HibernateUtils.shutdown();
    }

    @Test
    @Ignore
    public void dbLoadSave() {
        HibernateUtils.setHibernateConfigFile("hibernate.cfg-test.xml");
        TableNameUtils.setTableName("crawler_data_cn");
        ProductDao.deleteAllProducts();
        List<Product> products = prepareProducts() ;
        ProductDao.save(products); ;
        List<Product> saveProducts = ProductDao.loadAll() ;
        Assert.assertTrue(saveProducts.iterator().next().id != null);
        ProductDao.save(saveProducts);
        List<Product> pLast = ProductDao.loadAll() ;
        Assert.assertEquals(pLast.size(), 1);
        HibernateUtils.shutdown();
    }

    @Test
    @Ignore
    public void dbSave() {
        HibernateUtils.setHibernateConfigFile("hibernate.cfg-test.xml");
        TableNameUtils.setTableName("crawler_data_cn");
        List<Product> products = prepareProducts() ;
        ProductDao.save(products);
        HibernateUtils.shutdown();
    }

    private List<Product> prepareProducts() {
        List<Product> products = new ArrayList<>() ;

        Product product = new Product() ;
        product.code = "1234" ;

        product.title = "title1";
        product.details = "detail2";
        product.lister__item__price_full  = "$ 1234";
        product.price  = 1234.5;
        product.sizes = Arrays.asList("12", "34", "56");
        product.noStockSize = Arrays.asList("99", "88");
        product.productUrl = "/bin/product/1234";
        product.product_Live = "Live" ;
        product.product_Live_Date = new Date();
        product.product_Soldout_Date = new Date() ;
        product.product_Broken_Size = Arrays.asList("97", "88");
        product.product_Last_Broken_Size = Arrays.asList("97");
        product.product_Broken_Size_Date = new Date();
        product.sale_off_rate = 1025.333 ;
        product.sale_off_rate_date  = new Date();
        product.product_restock = Arrays.asList("56");
        product.product_restock_Date = new Date();
        products.add(product) ;
        return products ;
    }

    @Test
    @Ignore
    public void saveInCsv() throws Exception {
        List<Product> products = prepareProducts() ;
        new PersistProducts().persist(products, new File("./test.csv"));
    }

    @Test
    @Ignore
    public void loadFromCsv() throws Exception {
        List<Product> products = new LoadProducts().load(new File("./test.csv")) ;
        Product product = products.iterator().next() ;
        Assert.assertEquals(product.details,"detail2" );
    }
}
