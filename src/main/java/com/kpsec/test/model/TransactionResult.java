package com.kpsec.test.model;

public interface TransactionResult {
    String getTransDate();
    String getAccountNo();
    String getTransNo();
    String getAmount();
    String getFee();
    String getCancelYn();
}
