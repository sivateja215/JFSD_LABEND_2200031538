package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClientDemo {
    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure().buildSessionFactory();
        ClientDemo demo = new ClientDemo();

        // Inserting records
        demo.insertProduct("Electronics", "Smartphone", 699.99, "Latest model smartphone");
        demo.insertProduct("Electronics", "Laptop", 999.99, "Gaming laptop");
        
        // Aggregate functions
        demo.calculateAggregates();
        
        factory.close();
    }

    public void insertProduct(String category, String name, double cost, String productDescription) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Product product = new Product();
            product.setCategory(category);
            product.setName(name);
            product.setCost(cost);
            product.setProductDescription(productDescription);
            session.save(product);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void calculateAggregates() {
        Session session = factory.openSession();
        try {
            Query<Long> countQuery = session.createQuery("SELECT COUNT(p.id) FROM Product p", Long.class);
            Query<Double> sumQuery = session.createQuery("SELECT SUM(p.cost) FROM Product p", Double.class);
            Query<Double> avgQuery = session.createQuery("SELECT AVG(p.cost) FROM Product p", Double.class);
            Query<Double> maxQuery = session.createQuery("SELECT MAX(p.cost) FROM Product p", Double.class);
            Query<Double> minQuery = session.createQuery("SELECT MIN(p.cost) FROM Product p", Double.class);

            System.out.println("Count: " + countQuery.getSingleResult());
            System.out.println("Sum: " + sumQuery.getSingleResult());
            System.out.println("Average: " + avgQuery.getSingleResult());
            System.out.println("Max: " + maxQuery.getSingleResult());
            System.out.println("Min: " + minQuery.getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
