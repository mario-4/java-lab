package com.appgenesislab.rest.controller;

import com.appgenesislab.rest.model.Customer;
import com.appgenesislab.rest.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.POST, value = "/create/")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer c) {
        Customer customer = new Customer(new User(), c.getFirstName(), c.getLastName(), new Date());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/customers/{customer}")
                .buildAndExpand( counter.incrementAndGet())
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(customer,httpHeaders, HttpStatus.CREATED);
    }
}
