package crawler.net.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

//@Selector(value="#select")
public class NetProductDetailPage {
    /** size */
    @Selector(value="#product-form > div.sizing-container > select-dropdown", attr="options")
    public String options ;  // NO_VALUE, JSON string

    @Selector(value = "div.sold-out-details > div > span")
    public String soldOut ;  // NO_VALUE, Item Sold Out

    public NetProductDetailPage() {
    }

    @Override
    public String toString() {
        return "NetProductDetailPage{" +
                "options='" + options + '\'' +
                ", soldOut='" + soldOut + '\'' +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OptionJson {
        public String id ;
        public String stockLevel ; // Low_Stock, In_Stock, Out_of_Stock
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
