package crawler.net.repository;

import crawler.net.model.ProductNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductNetDao {
    @Autowired
    ProductNetRepository productNetRepository ;

    public void save(List<ProductNet> products) {
        productNetRepository.saveAll(products) ;
    }

    public List<ProductNet> loadAll() {
        return productNetRepository.findAll() ;
    }

    public void deleteAllProducts() {
        productNetRepository.deleteAll();
    }
}