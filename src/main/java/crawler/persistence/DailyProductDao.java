package crawler.persistence;

import crawler.DailyProduct;
import crawler.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DailyProductDao {
    public static void save(List<DailyProduct> products) {
        for (DailyProduct product : products) {
            save(product);
        }
    }
    public static void save(DailyProduct product) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.save(product);
            // Commit data.
            session.getTransaction().commit();
        } catch (Exception e) {
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw e ;
        }
    }
}