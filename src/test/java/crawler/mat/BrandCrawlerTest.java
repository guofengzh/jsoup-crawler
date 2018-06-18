package crawler.mat;

import crawler.mat.page.BrandListPage;
import crawler.mat.crawling.CrawingBrands;
import org.junit.Test;

import java.io.IOException;

public class BrandCrawlerTest {
    @Test
    public void crawlingBrandsTest() throws IOException {
        CrawingBrands crawingBrands = new CrawingBrands() ;
        BrandListPage brands = crawingBrands.crawle() ;
        System.out.println(brands) ;
    }
}
