package crawler.net;

import crawler.net.page.NetBrandPage;
import crawler.net.service.NetCrawingBrands;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NetBrandTest {
    @Test
    public void crawlingBrandsTest() throws IOException {
        NetCrawingBrands crawingBrands = new NetCrawingBrands() ;
        NetBrandPage brands = crawingBrands.crawle() ;
        System.out.println(brands) ;
        Assert.assertFalse(brands.clothing.isEmpty());
        Assert.assertFalse(brands.shoes.isEmpty());
        Assert.assertFalse(brands.bags.isEmpty());
        Assert.assertFalse(brands.accessories.isEmpty());
        Assert.assertFalse(brands.jewellery.isEmpty());
        Assert.assertFalse(brands.lingerie.isEmpty());
        Assert.assertFalse(brands.beauty.isEmpty());
    }

}
