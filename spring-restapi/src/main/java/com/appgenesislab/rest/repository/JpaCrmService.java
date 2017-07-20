package com.appgenesislab.rest.repository;


import com.appgenesislab.rest.exception.CustomerNotFoundException;
import com.appgenesislab.rest.exception.UserNotFoundException;
import com.appgenesislab.rest.model.Customer;
import com.appgenesislab.rest.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
@Transactional
class JpaCrmService implements CrmService {
    private CustomerRepository customerRepository;
    private UserRepository userRepository;

    @Autowired
    public JpaCrmService(CustomerRepository customerRepository,
                         UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Collection<Customer> search(long userId, String token) {
        return this.customerRepository.search(userId, "%" + token + "%");
    }

    @Override
    public User findById(long userId) {
        User user = userRepository.findOne(userId);
        if (null == user)
            throw new UserNotFoundException(userId);
        return user;
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName) {
        User user = new User(username, password, firstName, lastName);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User removeUser(long userId) {
        User u = userRepository.findOne(userId);
        this.userRepository.delete(userId);
        return u;
    }

    @Override
    public User updateUser(long userId, String username, String password, String firstName, String lastName) {
        User user = userRepository.findOne(userId);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        return this.userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Customer removeCustomer(long userId, long customerId) {
        User user = userRepository.findOne(userId);
        Customer customer = customerRepository.findOne(customerId);
        user.getCustomers().remove(customer);
        this.userRepository.save(user);
        customer.setUser(null);
        this.customerRepository.delete(customer);
        return customer;
    }

    @Override
    public Customer addCustomer(long userId, String firstName, String lastName) {
        Customer customer = new Customer(this.userRepository.findOne(userId), firstName, lastName, new Date());
        return this.customerRepository.save(customer);
    }

    @Override
    public Collection<Customer> loadCustomerAccounts(long userId) {
        List<Customer> customersList = this.customerRepository.findByUserId(userId);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (Customer c : customersList) {
            Hibernate.initialize(c);
            User user = new User(userId);
            c.setUser(user);
            customers.add(c);
        }
        return Collections.unmodifiableList(customers);
    }

    @Override
    public Customer findCustomerById(long customerId) {
        Customer customer = customerRepository.findOne(customerId);
        if (null == customer)
            throw new CustomerNotFoundException(customerId);
        Hibernate.initialize(customer.getUser());
        return customer;
    }


}
