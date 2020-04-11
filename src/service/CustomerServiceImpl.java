package service;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", "root", "123456");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void save(Customer customer) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into user (name, email, address) value (?, ?, ?)")
        ) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {

        List<Customer> list = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from user")
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String ad = resultSet.getString(4);
                Customer customer = new Customer(id, name, email, ad);
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Customer findById(int id) {
//        List<Customer> list = new ArrayList<>();
        Customer customer = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select name , email, address from user where id = ?") // đoạn này ? thì a phải có tham số
        ) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                customer = new Customer(name, email, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
// đoạn này trả về đối tượng Cútomer
        return customer;
    }

    @Override
    public void update(int id, Customer customer) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "update user set name = ? , email = ?, address = ? where id = ?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getAddress());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        return false;
    }
}
