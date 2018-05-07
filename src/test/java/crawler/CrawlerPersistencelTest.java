package crawler;

import crawler.persistence.DailyProductDao;
import crawler.persistence.HibernateUtils;
import crawler.persistence.ProductDao;
import crawler.persistence.TableNameUtils;

import java.io.IOException;
import java.util.List;

public class CrawlerPersistencelTest {
    public static void main(String[] args) throws IOException {
        HibernateUtils.setHibernateConfigFile("hibernate.cfg-test.xml");
        TableNameUtils.setTableNamePostfix("cn");

        CrawingProducts crawingProducts = new CrawingProducts() ;
        List<Product> products = crawingProducts.crawle(1) ;
        ProductDao.save(products);
        DailyProductDao.save(Main.makeDailyProductFromProduct(products));
        ProductDao.closeSessionFactory();
    }
}
