package com.mns.cr.service.service;

import java.util.List;
import java.util.stream.Collectors;

import com.mns.cr.service.entity.Customer;
import com.mns.cr.service.entity.Transaction;
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

        return customerRepository.findAll().parallelStream().map(ce -> new CustomerVO(ce.getName(), calculateRewardPoints(ce), calculateTotalPurchases(ce))).collect(Collectors.toList());
    }

    public CustomerVO getCustomerById(Integer customerId) {
        CustomerVO customerVO = null;
        Customer ce = customerRepository.findById(customerId).orElse(null);
        if (!ObjectUtils.isEmpty(ce)) {
            customerVO = new CustomerVO(ce.getName(), calculateRewardPoints(ce), calculateTotalPurchases(ce));
        }
        return customerVO;
    }

    private Long calculateRewardPoints(Customer customer) {
        if (ObjectUtils.isEmpty(customer.getTransactions())) {
            return 0l;
        }
        return customer.getTransactions().parallelStream().map(t -> calculatePoints(t).intValue()).reduce(0, (t1, t2) -> t1 + t2).longValue();
    }

    private Double calculateTotalPurchases(Customer customer) {
        if (ObjectUtils.isEmpty(customer.getTransactions())) {
            return 0d;
        }
        return customer.getTransactions().parallelStream().map(t -> t.getTotal().doubleValue()).reduce(0d, (t1, t2) -> t1 + t2).doubleValue();
    }

    private Long calculatePoints(Transaction transaction) {
        long points = 0l;
        if (transaction.getTotal() > 50 && transaction.getTotal() <= 100) {
            points += (transaction.getTotal().intValue() - 50) * 1;
        }
        if (transaction.getTotal() > 100) {
            //1 point for every dollar spent over $50
            points += 50;
            //2 points for every dollar spent over $100
            points += (transaction.getTotal().intValue() - 100) * 2;
        }
        return points;
    }

}
