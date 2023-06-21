package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.Record;
import com.example.demo.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;
    Record r1;
    private List<Transaction> testTransactionList1  = new ArrayList<>();
    private List<Record> testRecordList1  = new ArrayList<>();
    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        t1 = new Transaction(
                1L,
                "Tony",
                "t14523791",
                LocalDate.of(2023,1,24),
                63.5

        );
        t2 = new Transaction(
                2L,
                "Tony",
                "t14523791",
                LocalDate.of(2023,2,5),
                150.87

        );
        t3 = new Transaction(
                3L,
                "Tony",
                "t14523791",
                LocalDate.of(2023,2,24),
                132.84

        );
        t4 = new Transaction(
                4L,
                "Tony",
                "t14523791",
                LocalDate.of(2023,3,12),
                32.84

        );
        t5 = new Transaction(
                5L,
                "Mary",
                "m09821276",
                LocalDate.of(2023,1,5),
                111.24

        );
        r1 = new Record(
                "m09821276",
                "Mary",
                new HashMap<Integer,String>(){
                    {
                        put(1,"2006.62");
                        put(2,".00");
                        put(3,"285.54");
                    }
                },
                "2292.16"
        );

        //Test List one:
        //Contain two customer:
        //Tony and Mary
        testTransactionList1.add(t1);
        testTransactionList1.add(t2);
        testTransactionList1.add(t3);
        testTransactionList1.add(t4);
        testTransactionList1.add(t5);

        //Record List 1
        //Contain one customer's reward result:
        //Tony
        testRecordList1.add(r1);


    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetCustomers() throws Exception{
        when(transactionService.getCustomers()).thenReturn(testTransactionList1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void canGetAllCustomersPoint() throws Exception {
        when(transactionService.getAllCustomersPoint()).thenReturn(testRecordList1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/points")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void canGetCustomerPoint() throws Exception {
        when(transactionService.getCustomerPointById("m09821276")).thenReturn(testRecordList1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/points/" + "m09821276")).
                andDo(print()).andExpect(status().isOk());
    }
}