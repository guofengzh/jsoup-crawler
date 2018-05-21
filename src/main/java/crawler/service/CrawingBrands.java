package crawler.service;

import crawler.model.Brands;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;

import java.io.IOException;

@Component
public class CrawingBrands {

    private Jspoon jspoon = Jspoon.create();
    private HtmlAdapter<Brands> htmlPageAdapter = jspoon.adapter(Brands.class);

    final static Logger logger = LoggerFactory.getLogger(CrawingBrands.class);

    public Brands crawle() throws IOException {
        Connection.Response response = null;
        response = Jsoup.connect(Brands.url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(60000)
                .execute();
        int statusCode = response.statusCode();
        if ( statusCode != 200 ) {
            logger.error("CrawingBrands: Status Code: " + statusCode); ;
            return new Brands() ;
        } else {
            String htmlBodyContent = response.body();
            Brands brands = htmlPageAdapter.fromHtml(htmlBodyContent);
            return brands;
        }
    }
}
