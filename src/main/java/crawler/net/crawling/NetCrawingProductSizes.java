package crawler.net.crawling;

import crawler.net.page.NetProductDetailPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.IOException;

@Component
@Scope("prototype")
public class NetCrawingProductSizes {
    final static Logger logger = LoggerFactory.getLogger(NetCrawingProductSizes.class);
    public static final int DELAY = 5 ;
    public static final int MOD = 4 ;
    public static final int ONE_SECOND = 1000 ;

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<NetProductDetailPage> htmlPageAdapter = jspoon.adapter(NetProductDetailPage.class);

    public NetProductDetailPage crawle(String url, String referer) throws IOException {
        logger.info("NetCrawingProductSizes:referrer:" + referer);
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .referrer(referer)
                .followRedirects(true)
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("NetCrawingProducts:Status Code: " + statusCode + " at page " + url); ;
            return new NetProductDetailPage() ;
        }
        String htmlBodyContent = response.body() ;
        NetProductDetailPage page = htmlPageAdapter.fromHtml(htmlBodyContent);
        return page ;
    }
}
