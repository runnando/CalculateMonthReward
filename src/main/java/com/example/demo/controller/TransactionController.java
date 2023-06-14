package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.Record;
import com.example.demo.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl customerService;

    @Autowired
    public TransactionController(TransactionServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Transaction> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "/GetPoints")
    public List<Record> getAllCustomersPoint(){
        return customerService.getAllCustomersPoint();
    }

    @GetMapping(path = "/GetPoint/{userId}")
    public List<Record> getCustomerPoint(@PathVariable (name = "userId") String userId){
        return customerService.getCustomerPointById(userId);
    }
}
