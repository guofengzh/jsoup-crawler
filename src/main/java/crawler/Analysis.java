package crawler;

import crawler.Main;
import crawler.mat.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Analysis {
    private final static Locale CUSTOM_DEFAULT_LOCALE = Locale.US;
    private final static String LIVE = "Live" ;
    private final static String SOLD_OUT = "Soldout" ;

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public List<GenericProduct> analyze(List<? extends GenericProduct> newProducts, List<? extends GenericProduct> lastProducts) {
        logger.info("analysis data");
        HashMap<String, GenericProduct> newProductMap = new HashMap<>() ;
        for (GenericProduct product: newProducts ) {
            newProductMap.put(product.code, product) ;
        }
        HashMap<String, GenericProduct> lastProductMap = new HashMap<>() ;
        for (GenericProduct product: lastProducts ) {
            lastProductMap.put(product.code, product) ;
        }

        Date currentDate = new Date() ;
        for(GenericProduct product : newProducts ) {
           // 分析产品的上下架问题
            if (!lastProductMap.containsKey(product.code)) {
                // 新品上架
                product.product_Live = LIVE ;
                product.product_Live_Date = currentDate ;
                newProductMap.put(product.code, product) ;
            } else {
                // 已有的商品，
                GenericProduct lastProduct = lastProductMap.get(product.code);
                product.id = lastProduct.id ;
                //  1, 下架的商品
                String live = lastProduct.product_Live ;
                if (live.equalsIgnoreCase(SOLD_OUT)) {
                    // 下架的商品又上架了
                    product.product_Live = LIVE ;
                    product.product_Live_Date = currentDate ;
                 } else {
                    // 本来就是上架的状态 - 保持上架状态
                    product.product_Live = lastProduct.product_Live;
                    product.product_Live_Date = lastProduct.product_Live_Date;
                }

                // 分析其它参数
                analyzeProduct(product, lastProduct);
            }
        }
        List<GenericProduct> soldOutProducts = new ArrayList<>() ;
        for (GenericProduct lastProduct : lastProducts ) {
            if(!newProductMap.containsKey(lastProduct.code)) {
                // 这次下架的商品 - last商品被下架的了
                if ( lastProduct.product_Live == null || !lastProduct.product_Live.equals("Soldout")) {
                    lastProduct.product_Live = SOLD_OUT ;
                    lastProduct.product_Soldout_Date = currentDate ;
                }
                soldOutProducts.add(lastProduct) ;
            }
        }
        return soldOutProducts ;
    }

    private void analyzeProduct(GenericProduct product, GenericProduct lastProduct) {
        // 最新上下架 - 不在这里分析
        // 断码列表有变化吗？
        Set<String> noStocks = new TreeSet<>(product.product_Broken_Size) ;
        Set<String> lastNoStack = new TreeSet<>(lastProduct.product_Broken_Size) ;
        noStocks.removeAll(lastNoStack) ;
        if (noStocks.isEmpty()) {
            // 没有变化
            product.product_Last_Broken_Size = lastProduct.product_Last_Broken_Size;
            product.product_Broken_Size_Date = lastProduct.product_Broken_Size_Date;
        } else {
            // 有变化
            product.product_Last_Broken_Size = new ArrayList<>(noStocks) ; // 断码的列表
            product.product_Broken_Size_Date = new Date() ;
        }

        /** 降价(-)或升价的(+)% */
        if (product.price == lastProduct.price) {
            // 价钱未变
            product.sale_off_rate = lastProduct.sale_off_rate;
            product.last_price = lastProduct.last_price ;
            product.sale_off_rate_date = lastProduct.sale_off_rate_date ;
        } else {
            // 价钱变了
            double price = product.price ;
            double lastPrice = lastProduct.price ;
            double rate = (price - lastPrice) / price ;
            product.sale_off_rate = (double)(long)(rate * 100) ;
            product.last_price = lastPrice ;
            product.sale_off_rate_date = new Date() ;
        }

        /** 新补码的列表 */
        Set<String> sizes = new TreeSet<>(product.sizes) ;
        Set<String> lastSizes = new TreeSet<>(lastProduct.sizes) ;
        sizes.removeAll(lastSizes) ;
        product.product_restock = new ArrayList<>(sizes) ;
        if (!product.product_restock.isEmpty()) { // 今天补码了
            product.product_restock = new ArrayList<>(sizes) ; // 补码的列表
            product.product_restock_Date = new Date() ;
        } else {
            // 如果今天没有补码，则保持上次的时间
            product.product_restock = lastProduct.product_restock;
            product.product_restock_Date = lastProduct.product_restock_Date;
        }
    }
}
