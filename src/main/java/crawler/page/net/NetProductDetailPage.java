package crawler.page.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

//@Selector(value="#select")
public class NetProductDetailPage {
    /** size */
    @Selector(value="#product-form > div.sizing-container > select-dropdown", attr="options")
    public String options ;

    public NetProductDetailPage() {
    }

    @Override
    public String toString() {
        return "NetProductDetailPage{" +
                "options='" + options + '\'' +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OptionJson {
        public String id ;
        public String stockLevel ; // Low_Stock, In_Stock
        public String displaySize ;

        @Override
        public String toString() {
            return "OptionJson{" +
                    "id='" + id + '\'' +
                    ", stockLevel='" + stockLevel + '\'' +
                    ", displaySize='" + displaySize + '\'' +
                    '}';
        }
    }
}
