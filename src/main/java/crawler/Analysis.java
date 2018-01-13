package crawler;

import java.util.*;

public class Analysis {
    private final static Locale CUSTOM_DEFAULT_LOCALE = Locale.US;

    public void analyze(List<Product> newProducts, List<Product> lastProducts) {
        HashMap<String, Product> newProductMap = new HashMap<>() ;
        for (Product product: newProducts ) {
            newProductMap.put(product.code, product) ;
        }
        HashMap<String, Product> lastProductMap = new HashMap<>() ;
        for (Product product: lastProducts ) {
            lastProductMap.put(product.code, product) ;
        }

        Date currentDate = new Date() ;
        for(Product product : newProducts ) {
           // 分析产品的上下架问题
            if (!lastProductMap.containsKey(product.code)) {
                // 新品上架
                product.product_Live = "Live" ;
                product.product_Live_Date = currentDate ;
                newProductMap.put(product.code, product) ;
            } else {
                // 已有的商品，保持上架状态
                Product lastProduct = lastProductMap.get(product.code);
                product.product_Live = lastProduct.product_Live;
                product.product_Live_Date = lastProduct.product_Live_Date;

                // 分析其它参数
                analyzeProduct(product, lastProduct);
            }
        }
        for (Product lastProduct : lastProducts ) {
            if(!newProductMap.containsKey(lastProduct.code)) {
                // 这次下架的商品
                if ( lastProduct.product_Live == null || !lastProduct.product_Live.equals("Soldout")) {
                    lastProduct.product_Live = "Soldout" ;
                    lastProduct.product_Soldout_Date = currentDate ;
                }
                newProducts.add(lastProduct) ;
            }
        }
    }

    private void analyzeProduct(Product product, Product lastProduct) {
        // 最新上下架 - 不在这里分析
        // 断码列表有变化吗？
        Set<String> noStocks = new TreeSet<>(product.noStockSize) ;
        Set<String> lastNoStack = new TreeSet<>(lastProduct.product_Broken_Size) ;
        noStocks.removeAll(lastNoStack) ;
        if (noStocks.isEmpty()) {
            // 没有变化
            product.product_Last_Broken_Size = lastProduct.product_Last_Broken_Size;
            product.product_Broken_Size_Date = lastProduct.product_Broken_Size_Date;
        } else {
            // 有变化
            product.product_Last_Broken_Size = new ArrayList<>(noStocks) ;
            product.product_Broken_Size_Date = new Date() ;
        }

        /** 降价(-)或升价的(+)% */
        if (product.price == lastProduct.price) {
            // 价钱未变
            product.sale_off_rate = lastProduct.sale_off_rate;
            product.sale_off_rate_date = lastProduct.sale_off_rate_date ;
        } else {
            // 价钱变了
            double price = product.price ;
            double lastPrice = lastProduct.price ;
            double rate = (price - lastPrice) / price ;
            product.sale_off_rate = (double)(long)(rate * 100) ;
            product.sale_off_rate_date = new Date() ;
        }

        /** 新补码的列表 */
        Set<String> sizes = new TreeSet<>(product.sizes) ;
        Set<String> lastSizes = new TreeSet<>(lastProduct.sizes) ;
        sizes.removeAll(lastSizes) ;
        product.product_restock = new ArrayList<>(sizes) ;
        if (!product.product_restock.isEmpty()) { // 今天补码了
            product.product_restock_Date = new Date() ;
        } else {
            // 如果今天没有补码，则保持上次的时间
            product.product_restock = lastProduct.product_restock;
            product.product_restock_Date = lastProduct.product_restock_Date;
        }
    }
}
