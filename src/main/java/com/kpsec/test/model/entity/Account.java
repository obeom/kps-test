package com.kpsec.test.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Account {
    @Id
    private String accountNo;
    
    private String accountName;
    
    private String branchCode;
}
