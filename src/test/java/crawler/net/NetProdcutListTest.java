package crawler.net;

import crawler.net.page.NetProductListPage;
import crawler.net.crawling.NetCrawingProducts;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NetProdcutListTest {
    @Test
    public void crawleOnePage() throws IOException {
        // load last crawled products
        String url = "https://www.net-a-porter.com/cn/en/d/Shop/Clothing/Beachwear?cm_sp=topnav-_-clothing-_-beachwear&npp=60&image_view=product&dscroll=0&dscroll=0&pn=16" ;
        NetCrawingProducts crawingProducts = new NetCrawingProducts() ;
        NetProductListPage productPage = crawingProducts.doCrawle(url, "https://www.net-a-porter.com") ;
        System.out.println(productPage) ;
        Assert.assertEquals(productPage.currentPage, productPage.lastPage);
    }
}
