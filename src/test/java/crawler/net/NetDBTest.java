package crawler.net;

import crawler.TableNameUtils;
import crawler.config.AppConfig;
import crawler.mat.crawling.MatCrawling;
import crawler.mat.model.Product;
import crawler.mat.repository.ProductRepository;
import crawler.net.crawling.NetCrawling;
import crawler.net.model.ProductNet;
import crawler.net.repository.ProductNetRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class NetDBTest {
    @Autowired
    ProductNetRepository productRepository ;
    @Autowired
    NetCrawling crawling;

    @BeforeClass
    public static void setup() {
        TableNameUtils.setTableNamePostfix("cn");
    }

    @Test
    @Ignore
    public void repositoryTest() {
        ProductNet product = prepareProduct() ;
        productRepository.saveAll(Arrays.asList(product, product)) ;
        List<ProductNet> products = productRepository.findAll() ;
        Assert.assertEquals(products.size(), 1);
    }

    @Test
    @Ignore
    public void crawlingTest() throws Exception {
        crawling.run();
    }

    private ProductNet prepareProduct() {
        ProductNet product = new ProductNet() ;
        product.id = 1L ;
        product.code = "1234" ;

        product.brand = "brand1";
        product.description = "detail2";
        product.price  = 1234.5;
        product.sizes = Arrays.asList("12", "34", "56");
        product.productUrl = "/bin/product/1234";
        product.product_Live = "Live" ;
        product.product_Live_Date = new Date();
        product.product_Soldout_Date = new Date() ;
        product.product_Broken_Size = Arrays.asList("97", "88");
        product.product_Last_Broken_Size = Arrays.asList("97");
        product.product_Broken_Size_Date = new Date();
        product.sale_off_rate = 1025.333 ;
        product.last_price = 1000.0 ;
        product.sale_off_rate_date  = new Date();
        product.product_restock = Arrays.asList("56");
        product.product_restock_Date = new Date();
        return product ;
    }
}
