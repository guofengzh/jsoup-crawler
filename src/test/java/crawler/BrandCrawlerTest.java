package crawler;

import crawler.model.Brands;
import crawler.service.CrawingBrands;
import org.junit.Test;

import java.io.IOException;

public class BrandCrawlerTest {
    @Test
    public void crawlingBrandsTest() throws IOException {
        CrawingBrands crawingBrands = new CrawingBrands() ;
        Brands brands = crawingBrands.crawle() ;
        System.out.println(brands) ;
    }
}
