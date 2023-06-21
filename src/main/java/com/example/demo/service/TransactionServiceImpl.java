package com.example.demo.service;

import com.example.demo.domain.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.domain.Record;
import com.example.demo.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Util.NaturalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getCustomers() throws ApiRequestException {
        //This method return all customer's information
        return transactionRepository.findAll();
    }

    @Override
    public List<Record> getAllCustomersPoint() throws ApiRequestException {
        //This method will read data from database first
        //Store the data in a array list(the type is Transaction)
        List<Transaction> customersTransaction = transactionRepository.findAll();
        List<Record> customersPoint = new ArrayList<>();
        HashMap<String,HashMap<Integer,String>> customerPoint= new HashMap<>();
        HashMap<String,String> customerIdAndName= new HashMap<>();
        //Traversal all element of the list customersTransaction
        for(Transaction c: customersTransaction){
            if(c.getUserId() != null){
                //customerIdAndName store UserId-Name pair
                //If key does not exist, crate a new key and put value
                if(!customerIdAndName.containsKey(c.getUserId())) {
                    customerIdAndName.put(c.getUserId(), c.getName());
                }
                if(!customerPoint.containsKey(c.getUserId())){
                    //If User ID does not exist
                    //Create new UserId-(month-point) pair and put into hashmap
                    int m = c.getProcessDate().getMonthValue();
                    String point = NaturalUtils.calculatePoint(c.getAmount());
                    HashMap<Integer,String> pointPerMonth = new HashMap<>();
                    pointPerMonth.put(m,point);
                    customerPoint.put(c.getUserId(),pointPerMonth);
                }
                else{
                    //If User ID does already exist
                    //get original value and do calculation
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
        //Create record and store in list customersPoint
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
        //This method will read data from database first
        //Store the data in a array list(the type is Transaction)
        //If list size == 0, means the user id does not exist
        //Throw exception
        List<Transaction> customerByUserId = transactionRepository.findCustomerByUserId(userId);
        if(customerByUserId.size() == 0){
            throw new ApiRequestException("User ID can not been found");
        }
        List<Record> customersPoint = new ArrayList<>();
        HashMap<Integer,String> pointPerMonth = new HashMap<>();
        //User id is unique
        String name = customerByUserId.get(0).getName();
        //Traversal all element of the list customersByUserId
        for(Transaction c: customerByUserId){
            //pointPerMonth store month-point pair
            //If it does not contain the month
            //Create one and put new reward point
            //Otherwise, get original reward point
            //Do calculation and put back
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


}
