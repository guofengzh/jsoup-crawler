package crawler.dao;

import crawler.model.DailyProduct;
import crawler.repository.DailyProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyProductDao {
    @Autowired
    DailyProductRepository dailyProductRepository ;

    public void save(List<DailyProduct> products) {
        dailyProductRepository.saveAll(products) ;
    }
}