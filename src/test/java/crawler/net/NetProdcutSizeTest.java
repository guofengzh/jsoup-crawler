package crawler.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawler.page.net.NetProductDetailPage;
import crawler.page.net.NetProductListPage;
import crawler.service.net.NetCrawingProductSizes;
import crawler.service.net.NetCrawingProducts;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class NetProdcutSizeTest {
    @Test
    public void crawleProductSize() throws Exception {
        // load last crawled products
        String url = "https://www.net-a-porter.com/cn/en/product/1081393/marysia/nassau-reversible-striped-bikini-top" ;
        NetCrawingProductSizes crawingProductSizes = new NetCrawingProductSizes() ;
        NetProductDetailPage productDetailPage = crawingProductSizes.crawle(url, "https://www.net-a-porter.com") ;
        System.out.println(productDetailPage) ;

        ObjectMapper mapper = new ObjectMapper();
        List<NetProductDetailPage.OptionJson> options = mapper.readValue(productDetailPage.options, new TypeReference<List<NetProductDetailPage.OptionJson>>(){});
        System.out.println(options);

    }
}
