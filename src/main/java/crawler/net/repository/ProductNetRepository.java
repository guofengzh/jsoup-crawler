package crawler.net.repository;

import crawler.net.model.ProductNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductNetRepository extends JpaRepository<ProductNet, Integer> {
}
