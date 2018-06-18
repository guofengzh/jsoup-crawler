package crawler.net.service;

import crawler.Analysis;
import crawler.GenericProduct;
import crawler.net.model.ProductNet;
import crawler.net.page.NetBrandPage;
import crawler.net.repository.ProductNetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NetCrawling {
    @Autowired
    ProductNetDao productNetDao ;

    @Autowired
    Analysis analysis ;
    @Autowired
    NetCrawingProducts crawingProducts ;
    @Autowired
    NetCrawingBrands crawingBrands ;

    /**
     * store/load data from db
     *
     * @throws Exception
     */
    public void run() throws Exception {
        // load last crawled products
        List<ProductNet> lastProducts = productNetDao.loadAll() ;

        // crawling
        NetBrandPage brands = crawingBrands.crawle() ;
        crawingProducts.crawle(brands) ;
        List<ProductNet> products = crawingProducts.getCrawledProducts() ;

        if (!lastProducts.isEmpty()) {
            // analysis
            List<GenericProduct> soldoutProducts = analysis.analyze(products, lastProducts) ;
            for ( GenericProduct genericProduct : soldoutProducts) {
                products.add((ProductNet) genericProduct) ;
            }
        }
        productNetDao.save(products);
    }
}
