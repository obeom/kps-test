package com.kpsec.test.repository;

import java.util.List;

import org.dom4j.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.model.TransactionResult;
import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "SELECT trans_date as transDate, account_no as accountNo, trans_no as transNo, amount as amount, fee as fee, cancel_yn as cancelYn  FROM transaction WHERE trans_date = :transDate", nativeQuery = true)
    List<TransactionResult> getTransationByTransDate(@Param("transDate") String transDate);

}
