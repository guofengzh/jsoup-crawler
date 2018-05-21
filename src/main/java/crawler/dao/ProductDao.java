package crawler.dao;

import java.util.List;

import crawler.model.Product;
import crawler.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * If the goal is to modify an entity, you don't need any update method. You get the object from the database, modify it, and JPA saves it automatically:
 *   User u = repository.findOne(id);
 *   u.setFirstName("new first name");
 *   u.setLastName("new last name");
 *
 * If you have a detached entity and want to merge it, then use the save() method of CrudRepository:
 *   User attachedUser = repository.save(detachedUser);
 */
@Component
public class ProductDao {
    @Autowired
    ProductRepository productRepository ;

    public void save(List<Product> products) {
        productRepository.saveAll(products) ;
    }

    public List<Product> loadAll() {
        return productRepository.findAll() ;
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}