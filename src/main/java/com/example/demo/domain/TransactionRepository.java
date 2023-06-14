package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.userId=?1")
    List<Transaction> findCustomerByUserId(String userId);
//    @Query("SELECT c FROM transaction c where c.userId = ?1")
//    List<Transaction> findCustomerByUserId(String userId);


}
