package crawler.net;

import crawler.config.AppConfig;
import crawler.net.model.ProductNet;
import crawler.net.page.NetProductListPage;
import crawler.net.crawling.NetCrawingProducts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class NetProdcutListTest {
    @Autowired
    NetCrawingProducts netCrawingProducts ;

    @Test
    public void crawleOnePage() throws IOException {
        String url = "https://www.net-a-porter.com/cn/en/d/Shop/Clothing/Beachwear?cm_sp=topnav-_-clothing-_-beachwear&pn=1&npp=60&image_view=product&dScroll=0" ;
        NetProductListPage productPage = netCrawingProducts.doCrawle(url, "https://www.net-a-porter.com") ;
        System.out.println(productPage) ;
        Assert.assertEquals(productPage.currentPage, productPage.lastPage);
    }

    @Test
    public void crowleOneBrandTest() {
        String brand = "/cn/en/Shop/Clothing/Jackets?pn=1&npp=60&image_view=product&navlevel3=Blazers&cm_sp=topnav-_-clothing-_-blazers" ;
        netCrawingProducts.crawle(brand) ;
        List<ProductNet> products = netCrawingProducts.getCrawledProducts() ;
        System.out.println(products) ;
        Assert.assertEquals(695, products.size());
    }
}
