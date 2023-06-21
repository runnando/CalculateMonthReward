package com.example.demo.service;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.*;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;
    private AutoCloseable autoCloseable;
    private Record r1;
    private Record r2;
    private Record r3;
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;
    private Transaction t4;
    private Transaction t5;
    private List<Transaction> testTransactionList1  = new ArrayList<>();
    private List<Transaction> testTransactionList2  = new ArrayList<>();
    private List<Transaction> testTransactionList3  = new ArrayList<>();

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository);

//        r1 = new Record(
//                "m09821276",
//                "Mary",
//                new HashMap<Integer,String>(){
//                    {
//                        put(1,"2006.62");
//                        put(2,".00");
//                        put(3,"285.54");
//                    }
//                },
//                "2292.16"
//        );

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
        //Test List one:
        //Contain two customer:
        //Tony and Mary
        testTransactionList1.add(t1);
        testTransactionList1.add(t2);
        testTransactionList1.add(t3);
        testTransactionList1.add(t4);
        testTransactionList1.add(t5);

        //Test List two:
        //Contain one customer:
        //Tony
        testTransactionList2.add(t1);
        testTransactionList2.add(t2);
        testTransactionList2.add(t3);
        testTransactionList2.add(t4);

        //Test List three:
        //Contain one customer:
        //Mary
        testTransactionList3.add(t5);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetCustomers() {
        mock(Transaction.class);
        mock(TransactionRepository.class);
        when(transactionRepository.findAll()).thenReturn(new ArrayList<Transaction>(Collections.singleton(t1)));
        //By mocking, compare name,userId,and amount with assert value
        assertThat(transactionService.getCustomers().get(0).getUserId()).
                isEqualTo("t14523791");
        assertThat(transactionService.getCustomers().get(0).getName()).
                isEqualTo("Tony");
        assertThat(transactionService.getCustomers().get(0).getAmount()).
                isEqualTo(63.5);
    }

    @Test
    void canGetAllCustomersPoint() {
        //By mocking, compare total point and point per month  with assert value
        //testTransactionList1 have two customer
        mock(Transaction.class);
        mock(Record.class);
        mock(TransactionRepository.class);
        when(transactionRepository.findAll()).thenReturn(testTransactionList1);
        assertThat(transactionService.getAllCustomersPoint().get(0).getTotalamount()).
                isEqualTo("72.48");
        assertThat(transactionService.getAllCustomersPoint().get(1).getTotalamount()).
                isEqualTo("280.92");
        assertThat(transactionService.getAllCustomersPoint().get(1).getRewardPerMonth()
                .get(1)).isEqualTo("13.50");
        assertThat(transactionService.getAllCustomersPoint().get(1).getRewardPerMonth()
                .get(2)).isEqualTo("267.42");
        assertThat(transactionService.getAllCustomersPoint().get(1).getRewardPerMonth()
                .get(3)).isEqualTo(".00");


    }

    @Test
    void canGetCustomerPointById() {
        //By mocking, compare total point and point per month  with assert value
        //testTransactionList2 have one customer: Tony
        //testTransactionList3 have one customer: Mary
        mock(Transaction.class);
        mock(Record.class);
        mock(TransactionRepository.class);
        when(transactionRepository.findCustomerByUserId("t14523791")).thenReturn(testTransactionList2);
        assertThat(transactionService.getCustomerPointById("t14523791").get(0).getTotalamount()).
                isEqualTo("280.92");
        assertThat(transactionService.getCustomerPointById("t14523791").get(0).getRewardPerMonth().
                get(1)).isEqualTo("13.50");
        assertThat(transactionService.getCustomerPointById("t14523791").get(0).getRewardPerMonth().
                get(2)).isEqualTo("267.42");
        assertThat(transactionService.getCustomerPointById("t14523791").get(0).getRewardPerMonth().
                get(3)).isEqualTo(".00");
        when(transactionRepository.findCustomerByUserId("m09821276")).thenReturn(testTransactionList3);
        assertThat(transactionService.getCustomerPointById("m09821276").get(0).getTotalamount()).
                isEqualTo("72.48");
        assertThat(transactionService.getCustomerPointById("m09821276").get(0).getRewardPerMonth().
                get(1)).isEqualTo("72.48");
        assertThat(transactionService.getCustomerPointById("m09821276").get(0).getRewardPerMonth().
                get(2)).isEqualTo(null);
        assertThat(transactionService.getCustomerPointById("m09821276").get(0).getRewardPerMonth().
                get(3)).isEqualTo(null);


    }
}