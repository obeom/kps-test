package com.kpsec.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.model.BranchResult;
import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.Branch;
import com.kpsec.test.model.entity.Transaction;

public interface BranchRepository extends JpaRepository<Branch, String> {

    @Query(value = "SELECT branch_name as branchName FROM branch WHERE branch_code = :branchCode", nativeQuery = true)
    List<BranchResult> getBranchByBranchCode(@Param("branchCode") String branchCode);
    

}
