package crawler.persistence;

import java.util.List;

import crawler.Product;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Dao {
    public static void save(Product product) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            // All the action with DB via Hibernate
            // must be located in one transaction.
            // Start Transaction.
            session.getTransaction().begin();
            session.save(product) ;
            // Commit data.
            session.getTransaction().commit();
        } catch (Exception e) {
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw e ;
        }
    }

    public static List<Product> loadAll() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();
            String sql = "Select e from " + Product.class.getName() + " e" ;

            // Create Query object.
            Query<Product> query = session.createQuery(sql);
            // Execute query.
            List<Product> products = query.getResultList();

            // Commit data.
            session.getTransaction().commit();
            return products ;
        } catch (Exception e) {
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw e ;
        }
    }

}