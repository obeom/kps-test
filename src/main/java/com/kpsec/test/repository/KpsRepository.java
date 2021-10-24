package com.kpsec.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.model.KpsApiResult01;
import com.kpsec.test.model.KpsApiResult02;
import com.kpsec.test.model.KpsApiResult03;
import com.kpsec.test.model.KpsApiResult04;
import com.kpsec.test.model.entity.Account;

public interface KpsRepository extends JpaRepository<Account, String> {

    @Query(value = "SELECT \n"
    		+ "'2018' AS year, A.ACCOUNT_NAME AS name, A.ACCOUNT_NO AS acctNo, B.SumAmt\n"
    		+ "FROM ACCOUNT A\n"
    		+ "INNER JOIN (\n"
    		+ "	SELECT\n"
    		+ "	ACCOUNT_NO\n"
    		+ "	, SUM(AMOUNT) AS AMOUNT\n"
    		+ "	, SUM(FEE) AS FEE\n"
    		+ "	, SUM(AMOUNT) - SUM(FEE) AS SumAmt\n"
    		+ "	FROM (SELECT * \n"
    		+ "		FROM TRANSACTION\n"
    		+ "		WHERE TRANS_DATE BETWEEN '20180101' AND '20181231'\n"
    		+ "		AND CANCEL_YN = 'N')\n"
    		+ "	GROUP BY ACCOUNT_NO\n"
    		+ "	ORDER BY SumAmt DESC LIMIT 1) B\n"
    		+ "ON A.ACCOUNT_NO = B.ACCOUNT_NO\n"
    		+ "\n"
    		+ "UNION ALL\n"
    		+ "\n"
    		+ "SELECT \n"
    		+ "'2019' AS year, A.ACCOUNT_NAME AS name, A.ACCOUNT_NO AS acctNo, B.SumAmt\n"
    		+ "FROM ACCOUNT A\n"
    		+ "INNER JOIN (\n"
    		+ "	SELECT\n"
    		+ "	ACCOUNT_NO\n"
    		+ "	, SUM(AMOUNT) AS AMOUNT\n"
    		+ "	, SUM(FEE) AS FEE\n"
    		+ "	, SUM(AMOUNT) - SUM(FEE) AS SumAmt\n"
    		+ "	FROM (SELECT * \n"
    		+ "		FROM TRANSACTION\n"
    		+ "		WHERE TRANS_DATE BETWEEN '20190101' AND '20191231'\n"
    		+ "		AND CANCEL_YN = 'N')\n"
    		+ "	GROUP BY ACCOUNT_NO\n"
    		+ "	ORDER BY SumAmt DESC LIMIT 1) B\n"
    		+ "ON A.ACCOUNT_NO = B.ACCOUNT_NO"
    		, nativeQuery = true)
    List<KpsApiResult01> getCoustHighSumAmtByYear();
    
    
    @Query(value = "SELECT  '2018' AS YEAR\n"
    		+ ", ACCOUNT.ACCOUNT_NAME AS NAME\n"
    		+ ", ACCOUNT. ACCOUNT_NO AS ACCTNO\n"
    		+ "FROM ACCOUNT\n"
    		+ "WHERE ACCOUNT.ACCOUNT_NO NOT IN (\n"
    		+ "  SELECT DISTINCT(ACCOUNT_NO)\n"
    		+ "  FROM TRANSACTION\n"
    		+ "  WHERE trans_date BETWEEN '20180101' AND '20181231'\n"
    		+ "  AND CANCEL_YN = 'N'\n"
    		+ ")\n"
    		+ "\n"
    		+ "UNION ALL\n"
    		+ "\n"
    		+ "SELECT  '2019' AS YEAR\n"
    		+ ", ACCOUNT.ACCOUNT_NAME AS NAME\n"
    		+ ", ACCOUNT. ACCOUNT_NO AS ACCTNO\n"
    		+ "FROM ACCOUNT\n"
    		+ "WHERE ACCOUNT.ACCOUNT_NO NOT IN (\n"
    		+ "  SELECT DISTINCT(ACCOUNT_NO)\n"
    		+ "  FROM TRANSACTION\n"
    		+ "  WHERE trans_date BETWEEN '20190101' AND '20191231'\n"
    		+ "  AND CANCEL_YN = 'N'\n"
    		+ ")" , nativeQuery = true)
    List<KpsApiResult02> getCustomerWithoutTrans();
    
    
    @Query(value = "SELECT \n"
    		+ "D.year AS year\n"
    		+ ", C.branch_name AS brName\n"
    		+ ", D.branch_code AS brCode\n"
    		+ ", D.sumAmt AS sumAmt\n"
    		+ "FROM BRANCH C\n"
    		+ "INNER JOIN (\n"
    		+ "	SELECT \n"
    		+ "	B.year\n"
    		+ "	, A.branch_code\n"
    		+ "	, SUM(B.sumAcc)  AS sumAmt\n"
    		+ "	FROM ACCOUNT  A\n"
    		+ "	INNER JOIN (\n"
    		+ "		SELECT \n"
    		+ "			SUBSTRING(trans_date ,1,4) as year\n"
    		+ "			, account_no \n"
    		+ "			, SUM(amount) AS sumAcc \n"
    		+ "		FROM TRANSACTION\n"
    		+ "		WHERE cancel_yn = 'N'\n"
    		+ "		GROUP BY SUBSTRING(trans_date ,1,4), account_no ) B\n"
    		+ "	ON A.account_no = B.account_no \n"
    		+ "	GROUP BY B.year, A.branch_code ) D\n"
    		+ "ON C.branch_code = D.branch_code\n"
    		+ "ORDER BY year, sumAmt DESC" , nativeQuery = true)
    List<KpsApiResult03> getManagementTransAmount();
    
    @Query(value = "SELECT D.BRANCH_NAME as brName, D.BRANCH_CODE AS brCode, SUM(C.AMOUNT) AS sumAmt\n"
    		+ "FROM TRANSACTION C\n"
    		+ "INNER JOIN (\n"
    		+ "	SELECT A.ACCOUNT_NO, A.BRANCH_CODE , B.BRANCH_NAME\n"
    		+ "	FROM ACCOUNT A\n"
    		+ "	LEFT JOIN  (\n"
    		+ "		SELECT \n"
    		+ "			BRANCH_CODE\n"
    		+ "			, BRANCH_NAME\n"
    		+ "		FROM BRANCH \n"
    		+ "		WHERE BRANCH_NAME = :branch_name) B \n"
    		+ "	ON A.BRANCH_CODE = B.BRANCH_CODE\n"
    		+ "	WHERE A.BRANCH_CODE =B.BRANCH_CODE\n"
    		+ ") D\n"
    		+ "ON C.ACCOUNT_NO = D.ACCOUNT_NO\n"
    		+ "WHERE C.CANCEL_YN = 'N'\n"
    		+ "GROUP BY BRANCH_NAME" , nativeQuery = true)
    List<KpsApiResult04> getBranchTransAmount(@Param("branch_name") String branch_name);
    
    
    
    @Query(value = "SELECT \n"
    		+ "(SELECT BRANCH_CODE FROM BRANCH WHERE BRANCH_NAME = '판교점') as brName\n"
    		+ ", '판교점' AS brCode\n"
    		+ ", SUM(C.AMOUNT) AS sumAmt\n"
    		+ "FROM TRANSACTION C\n"
    		+ "INNER JOIN (\n"
    		+ "	SELECT A.ACCOUNT_NO, A.BRANCH_CODE\n"
    		+ "	FROM ACCOUNT A\n"
    		+ "	LEFT JOIN  (\n"
    		+ "		SELECT \n"
    		+ "			BRANCH_CODE\n"
    		+ "		FROM BRANCH \n"
    		+ "		WHERE BRANCH_NAME IN ('판교점', '분당점')\n"
    		+ "	) B\n"
    		+ "	ON A.BRANCH_CODE = B.BRANCH_CODE\n"
    		+ "	WHERE A.BRANCH_CODE =B.BRANCH_CODE\n"
    		+ ") D\n"
    		+ "ON C.ACCOUNT_NO = D.ACCOUNT_NO\n"
    		+ "WHERE C.CANCEL_YN = 'N'" , nativeQuery = true)
    List<KpsApiResult04> getBranchTransAmountPangyo(@Param("branch_name") String branch_name);
    
}
