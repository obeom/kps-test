package com.kpsec.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.model.KpsApiResult01;
import com.kpsec.test.model.KpsApiResult02;
import com.kpsec.test.model.KpsApiResult03;
import com.kpsec.test.model.KpsApiResult04;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.KpsRepository;

@Service
@Transactional
public class KpsTestService {

    @Autowired
    private KpsRepository kpsRepository;

    public List<KpsApiResult01> getCoustHighSumAmtByYear(){
        List<KpsApiResult01> result = kpsRepository.getCoustHighSumAmtByYear();
        return result;
    }
    
    public List<KpsApiResult02> getCustomerWithoutTrans(){
        List<KpsApiResult02> result = kpsRepository.getCustomerWithoutTrans();
        return result;
    }
    
    public List<KpsApiResult03> getManagementTransAmount(){
        List<KpsApiResult03> result = kpsRepository.getManagementTransAmount();
        return result;
    }
    
    public List<KpsApiResult04> getBranchTransAmount(String branch_name){
        List<KpsApiResult04> result = null;
        
        if(branch_name.equals("판교점")) {
        	result = kpsRepository.getBranchTransAmountPangyo(branch_name);
        } else {
        	result = kpsRepository.getBranchTransAmount(branch_name);
        }
        
        return result;
    }
    
    
}
