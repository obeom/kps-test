package com.kpsec.test.model.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class TransactionPk implements Serializable {
    private String transDate;
    private String accountNo;
    private String transNo;
}
