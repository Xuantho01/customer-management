package service;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(int id);
    void save(Customer customer);
    void update( int id, Customer customer);
    boolean remove(int id);
}
