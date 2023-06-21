package com.example.demo.service;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.Record;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    public List<Transaction> getCustomers();
    public List<Record> getAllCustomersPoint();
    public List<Record> getCustomerPointById(String userId);
//    public List<Record> getCustomerPointByMonth();



}
