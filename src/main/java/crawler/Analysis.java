package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Analysis {
    private final static Locale CUSTOM_DEFAULT_LOCALE = Locale.US;
    private final static DateFormat fullDateFormat = new SimpleDateFormat("yyyy.MM.dd", CUSTOM_DEFAULT_LOCALE);
    private final static String currentDate = fullDateFormat.format(new Date()) ;

    public void analyze(List<Product> newProducts, List<Product> lastProducts) {
        HashMap<String, Product> newProductMap = new HashMap<>() ;
        for (Product product: newProducts ) {
            newProductMap.put(product.code, product) ;
        }
        HashMap<String, Product> lastProductMap = new HashMap<>() ;
        for (Product product: lastProducts ) {
            lastProductMap.put(product.code, product) ;
        }

        for(Product product : newProducts ) {
            // 分析产品的上下架问题
            if (!lastProductMap.containsKey(product.code)) {
                // 新品上架
                product.on_off_shelf = "On Shelf" ;
                product.on_shelf_date = currentDate ;
                product.sizes_in_short = product.noStockSize ;
                newProductMap.put(product.code, product) ;
            } else {
                // 已有的商品
                Product lastProduct = lastProductMap.get(product.code);
                analyzeProduct(product, lastProduct);
            }
        }
        for (Product lastProduct : lastProducts ) {
            if( !newProductMap.containsKey(lastProduct.code)) {
                // 这次下架的商品
                if ( !lastProduct.on_off_shelf.equals("Off Shelf")) {
                    lastProduct.on_off_shelf = "Off Shelf" ;
                    lastProduct.off_shelf_date = currentDate ;
                }
                newProducts.add(lastProduct) ;
            }
        }
    }

    private void analyzeProduct(Product product, Product lastProduct) {
        // 最新上下架 - 不在这里分析
        // 断码列表有变化吗？
        Set<String> noStocks = new TreeSet<>(product.noStockSize) ;
        Set<String> lastNoStack = new TreeSet<>(lastProduct.noStockSize) ;
        noStocks.removeAll(lastNoStack) ;
        if (noStocks.isEmpty()) {
            // 没有变化
            product.sizes_in_short_date = lastProduct.sizes_in_short_date ;
        } else {
            // 有变化
            product.sizes_in_short_date = currentDate ;
            /** 断码列表 */
            product.sizes_in_short = product.noStockSize ;
        }

        /** 降价(-)或升价的(+)% */
        if (lastProduct != null) {
            if (product.lister__item__price == lastProduct.lister__item__price) {
                // 价钱未变
                product.sale_off_rate = lastProduct.sale_off_rate;
            } else {
                // 价钱变了
                double price = product.lister__item__price ;
                double lastPrice = lastProduct.lister__item__price ;
                double rate = (price - lastPrice) / price ;
                product.sale_off_rate = (double)(long)(rate * 100) ;
            }
        }

        /** 新补码的列表 */
        Set<String> sizes = new TreeSet<>(product.sizes) ;
        Set<String> lastSizes = new TreeSet<>(lastProduct.sizes) ;
        sizes.removeAll(lastSizes) ;
        product.complements = new ArrayList<>(sizes) ;
        if (!product.complements.isEmpty()) { // 今天补码了
            product.complement_date = currentDate ;
        } else {
            // 如果今天没有补码，则保持上次的时间
            product.complements = lastProduct.complements ;
            product.complement_date = lastProduct.complement_date ;
        }
    }
}
