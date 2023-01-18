package com.mns.cr.service.service;

import java.util.List;
import java.util.stream.Collectors;

import com.mns.cr.service.entity.Customer;
import com.mns.cr.service.model.CustomerVO;
import com.mns.cr.service.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<CustomerVO> getCustomerAll() {

        return customerRepository.findAll().parallelStream().map(ce -> new CustomerVO(ce.getName(), ce.calculateRewardPoints(), ce.calculateTotalPurchases())).collect(Collectors.toList());
    }

    public CustomerVO getCustomerById(Integer customerId) {
        CustomerVO customerVO = null;
        Customer ce = customerRepository.findById(customerId).orElse(null);
        if(!ObjectUtils.isEmpty(ce)){
            customerVO = new CustomerVO(ce.getName(), ce.calculateRewardPoints(), ce.calculateTotalPurchases());
        }
        return customerVO;
    }

}
