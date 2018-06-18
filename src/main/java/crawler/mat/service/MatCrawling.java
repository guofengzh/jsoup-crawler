package crawler.mat.service;

import crawler.GenericProduct;
import crawler.mat.repository.DailyProductDao;
import crawler.mat.repository.ProductDao;
import crawler.mat.model.DailyProduct;
import crawler.mat.model.Product;
import crawler.mat.page.BrandListPage;
import crawler.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MatCrawling {
    @Autowired
    ProductDao productDao ;
    @Autowired
    DailyProductDao dailyProductDao ;

    @Autowired
    Analysis analysis ;
    @Autowired
    CrawingProducts crawingProducts ;
    @Autowired
    CrawingBrands crawingBrands ;

    /**
     * store/load data from db
     *
     * @throws Exception
     */
    public void run() throws Exception {
        // load last crawled products
        List<Product> lastProducts = productDao.loadAll() ;

        // crawling
        BrandListPage brands = crawingBrands.crawle() ;
        crawingProducts.crawle(brands) ;
        List<Product> products = crawingProducts.getCrawledProducts() ;

        if (!lastProducts.isEmpty()) {
            // analysis
            List<GenericProduct> soldoutProducts = analysis.analyze(products, lastProducts) ;
            for ( GenericProduct genericProduct : soldoutProducts) {
                products.add((Product)genericProduct) ;
            }
        }

        productDao.save(products);
        dailyProductDao.save(makeDailyProductFromProduct(products));
    }

    public List<DailyProduct> makeDailyProductFromProduct(List<Product> products) {
        List<DailyProduct> dailyProducts = new ArrayList<>(products.size()) ;
        Date createdAt = new Date() ;
        for (Product product : products) {
            DailyProduct dailyProduct = new DailyProduct() ;
            dailyProduct.code = product.code ;
            dailyProduct.title = product.title ;
            dailyProduct.details = product.details ;
            dailyProduct.brands = product.brands ;
            dailyProduct.price = product.price ;
            dailyProduct.sizes = product.sizes ;
            dailyProduct.broken_Size = product.product_Broken_Size ;
            dailyProduct.productUrl = product.productUrl ;
            dailyProduct.created_at = createdAt ;

            dailyProducts.add(dailyProduct) ;
        }
        return dailyProducts ;
    }
}
