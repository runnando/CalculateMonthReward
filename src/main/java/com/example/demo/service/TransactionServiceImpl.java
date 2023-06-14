package com.example.demo.service;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.TransactionRepository;
import com.example.demo.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Util.NaturalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static TransactionRepository customerRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Transaction> getCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public List<Record> getAllCustomersPoint(){
        List<Transaction> customersTransaction = customerRepository.findAll();
        List<Record> customersPoint = new ArrayList<>();
        HashMap<String,HashMap<Integer,String>> customerPoint= new HashMap<>();
        HashMap<String,String> customerIdAndName= new HashMap<>();

        for(Transaction c: customersTransaction){
            if(c.getUserId() != null){
                if(!customerIdAndName.containsKey(c.getUserId())){
                    customerIdAndName.put(c.getUserId(),c.getName());
                }
                if(!customerPoint.containsKey(c.getUserId())){
                    //Create new UserId-(month-point) pair and put into hashmap
                    int m = c.getProcessDate().getMonthValue();
                    String point = NaturalUtils.calculatePoint(c.getAmount());
                    HashMap<Integer,String> pointPerMonth = new HashMap<>();
                    pointPerMonth.put(m,point);
                    customerPoint.put(c.getUserId(),pointPerMonth);
                }
                else{
                    int m = c.getProcessDate().getMonthValue();
                    String point = NaturalUtils.calculatePoint(c.getAmount());
                    if(!customerPoint.get(c.getUserId()).containsKey(m)){
                        //Put new month-point pair into hashmap
                        customerPoint.get(c.getUserId()).put(m,point);
                    }
                    else{
                        //Get point from hashmap according user id, then plus new point.
                        double temp = Double.valueOf(customerPoint.get(c.getUserId()).get(m)) + Double.valueOf(point);
                        customerPoint.get(c.getUserId()).put(m,String.format("%.2f", temp));
                    }

                }
            }

        }
        for(String userId:customerPoint.keySet()){
            String totalAmount = NaturalUtils.calculateTotalPoint(customerPoint.get(userId));
            Record r = new Record(
                    userId,
                    customerIdAndName.get(userId),
                    customerPoint.get(userId),
                    totalAmount
            );
            customersPoint.add(r);

        }

        return customersPoint;

    }

    @Override
    public List<Record> getCustomerPointById(String userId){
        List<Transaction> customerByUserId = customerRepository.findCustomerByUserId(userId);
        if(customerByUserId.size() == 0){
            throw new IllegalStateException("User ID can not been found");
        }
        List<Record> customersPoint = new ArrayList<>();
        HashMap<Integer,String> pointPerMonth = new HashMap<>();
        String name = customerByUserId.get(0).getName();
        for(Transaction c: customerByUserId){
            int m = c.getProcessDate().getMonthValue();
            String point = NaturalUtils.calculatePoint(c.getAmount());
            if(!pointPerMonth.containsKey(m)){
                pointPerMonth.put(m,point);
            }
            else{
                double temp = Double.valueOf(pointPerMonth.get(m)) + Double.valueOf(point);
                pointPerMonth.put(m,String.format("%.2f", temp));
            }
        }
        String totalAmount = NaturalUtils.calculateTotalPoint(pointPerMonth);
        Record r = new Record(
                userId,
                name,
                pointPerMonth,
                totalAmount
        );
        customersPoint.add(r);

        return customersPoint;

    }



//    @Override
//    public List<Record> getAllCustomerPointByMonth(){
//
//    }


}
