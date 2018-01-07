package crawler;

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
                newProductMap.put(product.code, product) ;
            } else {
                // 已有的商品，保持上架状态
                Product lastProduct = lastProductMap.get(product.code);
                product.on_off_shelf = lastProduct.on_off_shelf ;
                product.on_shelf_date = lastProduct.on_shelf_date ;

                // 分析其它参数
                analyzeProduct(product, lastProduct);
            }
        }
        for (Product lastProduct : lastProducts ) {
            if(!newProductMap.containsKey(lastProduct.code)) {
                // 这次下架的商品
                if ( lastProduct.on_off_shelf == null || !lastProduct.on_off_shelf.equals("Off Shelf")) {
                    lastProduct.on_off_shelf = "Off Shelf" ;
                    lastProduct.off_shelf_date = currentDate ;
                }
                newProducts.add(lastProduct) ;
            } else {
                // 保持下架状态
                Product newProduct = newProductMap.get(lastProduct.code) ;
                newProduct.on_off_shelf = lastProduct.on_off_shelf ;
                newProduct.off_shelf_date = lastProduct.off_shelf_date ;
            }
        }
    }

    private void analyzeProduct(Product product, Product lastProduct) {
        // 最新上下架 - 不在这里分析
        // 断码列表有变化吗？
        Set<String> noStocks = new TreeSet<>(product.noStockSize) ;
        Set<String> lastNoStack = new TreeSet<>(lastProduct.sizes_in_short) ;
        noStocks.removeAll(lastNoStack) ;
        if (noStocks.isEmpty()) {
            // 没有变化
            product.sizes_in_short_last = lastProduct.sizes_in_short_last ;
            product.sizes_in_short_date = lastProduct.sizes_in_short_date ;
        } else {
            // 有变化
            product.sizes_in_short_last = new ArrayList<>(noStocks) ;
            product.sizes_in_short_date = currentDate ;
        }

        /** 降价(-)或升价的(+)% */
        if (product.lister__item__price.equals(lastProduct.lister__item__price)) {
            // 价钱未变
            product.sale_off_rate = lastProduct.sale_off_rate;
            product.sale_off_rate_date = lastProduct.sale_off_rate_date ;
        } else {
            // 价钱变了
            double price = product.lister__item__price ;
            double lastPrice = lastProduct.lister__item__price ;
            double rate = (price - lastPrice) / price ;
            product.sale_off_rate = (double)(long)(rate * 100) ;
            product.sale_off_rate_date = currentDate ;
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
