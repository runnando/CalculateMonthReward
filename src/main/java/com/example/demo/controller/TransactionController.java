package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.Record;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/transaction")
public class TransactionController {

    public TransactionServiceImpl customerService;

    @Autowired
    public TransactionController(TransactionServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseBody
    //Return all customers' information
    public List<Transaction> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "/points")
    @ResponseBody
    //Return all customers' every month reward point
    public ResponseEntity<List<Record>> getAllCustomersPoint(){
        try{
            List<Record> result = customerService.getAllCustomersPoint();
            if (result.size() != 0){
                return ResponseEntity.ok().body(result);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/points/{userId}")
    @ResponseBody
    //Return the customer's every month reward according to parameter userId
    public ResponseEntity<List<Record>> getCustomerPoint(@PathVariable (name = "userId") String userId){
        try{
            List<Record> result = customerService.getCustomerPointById(userId);
            if (result.size() != 0){
                return ResponseEntity.ok().body(result);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
