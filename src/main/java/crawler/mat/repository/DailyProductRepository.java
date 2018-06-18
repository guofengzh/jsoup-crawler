package crawler.mat.repository;

import crawler.mat.model.DailyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DailyProductRepository  extends JpaRepository<DailyProduct, Integer> {
}
