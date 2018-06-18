package crawler.net.crawling;

import crawler.net.page.NetBrandPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.IOException;

@Component
public class NetCrawingBrands {

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<NetBrandPage> htmlPageAdapter = jspoon.adapter(NetBrandPage.class);

    final static Logger logger = LoggerFactory.getLogger(NetCrawingBrands.class);

    public NetBrandPage crawle() throws IOException {
        Connection.Response response = null;
        response = Jsoup.connect(NetBrandPage.url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("CrawingBrands: Status Code: " + statusCode); ;
            return new NetBrandPage() ;
        } else {
            String htmlBodyContent = response.body();
            NetBrandPage brands = htmlPageAdapter.fromHtml(htmlBodyContent);
            return brands;
        }
    }
}
