package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Load Hibernate Configuration
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Insert Customer Records
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAge(30);
        customer1.setLocation("New York");

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setAge(25);
        customer2.setLocation("California");

        Customer customer3 = new Customer();
        customer3.setName("Mike Ross");
        customer3.setEmail("mike.ross@example.com");
        customer3.setAge(35);
        customer3.setLocation("Texas");

        session.save(customer1);
        session.save(customer2);
        session.save(customer3);

        // Fetch Records Using HCQL with Criteria
        Criteria criteria = session.createCriteria(Customer.class);

        System.out.println("Customers whose age is greater than 25:");
        criteria.add(Restrictions.gt("age", 25));
        List<Customer> result1 = criteria.list();
        for (Customer customer : result1) {
            System.out.println(customer.getName() + " - " + customer.getAge());
        }

        System.out.println("\nCustomers whose location is like 'Cal%':");
        criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.like("location", "Cal%"));
        List<Customer> result2 = criteria.list();
        for (Customer customer : result2) {
            System.out.println(customer.getName() + " - " + customer.getLocation());
        }

        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
