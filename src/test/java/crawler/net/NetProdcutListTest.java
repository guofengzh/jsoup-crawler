package crawler.net;

import crawler.config.AppConfig;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class NetProdcutListTest {
    @Autowired
    NetCrawingProducts netCrawingProducts ;

    @Test
    public void crawleOnePage() throws IOException {
        // load last crawled products
        String url = "https://www.net-a-porter.com/cn/en/d/Shop/Clothing/Beachwear?cm_sp=topnav-_-clothing-_-beachwear&pn=1&npp=60&image_view=product&dScroll=0" ;
        NetProductListPage productPage = netCrawingProducts.doCrawle(url, "https://www.net-a-porter.com") ;
        System.out.println(productPage) ;
        Assert.assertEquals(productPage.currentPage, productPage.lastPage);
    }
}
