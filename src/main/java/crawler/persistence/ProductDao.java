package crawler.persistence;

import java.util.List;

import crawler.Product;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ProductDao {
    public static void save(List<Product> products) {
        for (Product product : products) {
            save(product);
        }
    }
    public static void save(Product product) {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            // All the action with DB via Hibernate
            // must be located in one transaction.
            // Start Transaction.
            session.getTransaction().begin();
            if (product.id == null ) {
                session.save(product);
            } else {
                session.update(product);
            }
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
            String sql = "select e from " + Product.class.getName() + " e" ;

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

    public static void deleteAllProducts() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();
            String sql = "delete from " + Product.class.getName()  ;

            // Create Query object.
            Query<Product> query = session.createQuery(sql);
            query.executeUpdate();

            // Commit data.
            session.getTransaction().commit();
        } catch (Exception e) {
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw e ;
        }
    }

    public static void closeSessionFactory() {
        SessionFactory factory = HibernateUtils.getSessionFactory();
        factory.close();
    }
}