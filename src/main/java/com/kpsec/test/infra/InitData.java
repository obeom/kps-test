package com.kpsec.test.infra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.Branch;
import com.kpsec.test.model.entity.Transaction;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.BranchRepository;
import com.kpsec.test.repository.TransactionRepository;

@Component
public class InitData {
	
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    BranchRepository branchRepository;
    
    @PostConstruct
    public void initAccount() throws IOException {
        if (accountRepository.count() == 0) {
            Resource resource = new ClassPathResource("계좌정보.csv");
            List<Account> accountList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream().skip(1).map((Function<String, Account>) line -> {
                        String[] split = line.split(",");
                        return Account.builder().accountNo(split[0]).accountName(split[1]).branchCode(split[2]).build();
					}).collect(Collectors.toList());
            accountRepository.saveAll(accountList);
        }
    }
    
    @PostConstruct
    public void initTransaction() throws IOException {
        if (transactionRepository.count() == 0) {
            Resource resource = new ClassPathResource("거래내역.csv");
            List<Transaction> transactionList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream().skip(1).map((Function<String, Transaction>) line -> {
                        String[] split = line.split(",");
                        return Transaction.builder().transDate(split[0]).accountNo(split[1]).transNo(split[2]).amount(Integer.parseInt(split[3])).fee(Integer.parseInt(split[4])).cancelYn(split[5]).build();
					}).collect(Collectors.toList());
            transactionRepository.saveAll(transactionList);
        }
    }
    
    @PostConstruct
    public void initBranch() throws IOException {
        if (branchRepository.count() == 0) {
            Resource resource = new ClassPathResource("관리점정보.csv");
            List<Branch> branchList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream().skip(1).map((Function<String, Branch>) line -> {
                        String[] split = line.split(",");
                        return Branch.builder().branchCode(split[0]).branchName(split[1]).build();
					}).collect(Collectors.toList());
            branchRepository.saveAll(branchList);
        }
    }
    
}
