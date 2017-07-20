package com.appgenesislab.rest.repository;

import com.appgenesislab.rest.model.Customer;
import com.appgenesislab.rest.model.User;

import java.util.Collection;

public interface CrmService {

	Collection<Customer> search(long userId, String token) ;

	User findById(long userId);

	User createUser(String username, String password, String firstName, String lastName);

	User removeUser(long userId);

	User updateUser(long userId, String username, String password, String firstName, String lastName);

	User findUserByUsername(String username);

	Customer removeCustomer(long userId, long customerId);

	Customer addCustomer(long userId, String firstName, String lastName);

	Collection<Customer> loadCustomerAccounts(long userId);

	Customer findCustomerById(long customerId);

 }
