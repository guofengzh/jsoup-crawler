package crawler.net;

import crawler.page.mat.BrandListPage;
import crawler.page.net.NetBrandListPage;
import crawler.service.mat.CrawingBrands;
import crawler.service.net.NetCrawingBrands;
import org.junit.Test;

import java.io.IOException;

public class NetBrandTest {
    @Test
    public void crawlingBrandsTest() throws IOException {
        NetCrawingBrands crawingBrands = new NetCrawingBrands() ;
        NetBrandListPage brands = crawingBrands.crawle() ;
        System.out.println(brands.clothing) ;
    }

}
