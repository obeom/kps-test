package com.kpsec.test.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(TransactionPk.class)
public class Transaction {
    @Id
    @Column(name = "trans_date")
    private String transDate;
    
    @Id
    @Column(name = "account_no")
    private String accountNo;
    
    @Id
    @Column(name = "trans_no")
    private String transNo;
    
    @Column(name = "amount")
    private int amount;
    
    @Column(name = "fee")
    private int fee;
    
    @Column(name = "cancel_yn")
    private String cancelYn;

}
