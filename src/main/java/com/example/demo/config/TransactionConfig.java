package com.example.demo.config;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class TransactionConfig {

    @Bean
    CommandLineRunner commandLineRunner(TransactionRepository repository){
        return args -> {
            List<Transaction> customerRecord = new ArrayList<>();
            String[] customerName = new String[]{"Tony","Jason","John","Mary"};
            String[] customerUserId = new String[]{"t14523791","j33242212","j10289309","m09821276"};
            String customerEmail = "";
            double amount = 0;

            Random r = new Random();
            DecimalFormat df = new DecimalFormat( "0.00" );

            for(int i = 0;i<50;i++){
                int randomCustomerNo = r.nextInt(4);
                String randomCustomerName = customerName[randomCustomerNo];
                String randomCustomerId = customerUserId[randomCustomerNo];
                int minDay = (int) LocalDate.of(2023, 1, 1).toEpochDay();
                int maxDay = (int) LocalDate.of(2023, 3, 31).toEpochDay();
                long randomDay = minDay + r.nextInt(maxDay - minDay);
                double a=Math.random()*(r.nextInt(100)+200);
                double randomAmount = Double.parseDouble(df.format(a));
                Transaction record = new Transaction(
                        randomCustomerName,
                        randomCustomerId,
                        LocalDate.ofEpochDay(randomDay),
                        randomAmount
                );
                customerRecord.add(record);
            }
            repository.saveAll(customerRecord);

        };
    }
}
