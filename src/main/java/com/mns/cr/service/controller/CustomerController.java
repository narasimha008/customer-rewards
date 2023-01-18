package com.mns.cr.service.controller;

import com.mns.cr.service.model.CustomerVO;
import com.mns.cr.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/customers")
    public List<CustomerVO> findCustomerAll() {
        return customerService.getCustomerAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerVO> findCustomer(@PathVariable Integer id) {
        CustomerVO customer = customerService.getCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<CustomerVO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CustomerVO>(customer, HttpStatus.OK);
    }


}
