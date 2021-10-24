package com.kpsec.test.contoller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kpsec.test.model.KpsApiResult01;
import com.kpsec.test.model.KpsApiResult02;
import com.kpsec.test.model.KpsApiResult03;
import com.kpsec.test.model.KpsApiResult04;
import com.kpsec.test.service.KpsTestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "test")
@RestController
@RequestMapping("/kps/")
public class KpsTestController {
    
    @Autowired
    private KpsTestService kpsTestService;
    
    @ApiOperation(value = "연도별 합계 금액이 가장 많은 고객", notes = "2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 조회합니다.")
    @GetMapping(value = "/coustHighSumAmt", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public ResponseEntity<?> getCoustHighSumAmtByYear() {
    	ResponseEntity<?> responseEntity = null;
    	HttpStatus httpStatus;
    	JsonArray jsonArr = new JsonArray();
    	
    	List<KpsApiResult01> list = kpsTestService.getCoustHighSumAmtByYear();
    	
    	for(int i=0; i<list.size(); i++) {
    		JsonObject jsonObj = new JsonObject();

    		jsonObj.addProperty("year",  list.get(i).getYear());
    		jsonObj.addProperty("name",  list.get(i).getName());
    		jsonObj.addProperty("acctNo",  list.get(i).getAcctNo());
    		jsonObj.addProperty("sumAmt",  list.get(i).getSumAmt());
    		
    		jsonArr.add(jsonObj);
    	}
    	
    	httpStatus = HttpStatus.OK;
    	responseEntity = ResponseEntity.status(httpStatus).body(jsonArr.toString());
    	
    	return responseEntity;
    }
    
    @ApiOperation(value = "거래가 없는 고객 조회", notes = "2018년 또는 2019년에 거래가 없는 고객을 조회합니다.")
    @GetMapping(value = "/customerWithoutTrans", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public ResponseEntity<?> getCustomerWithoutTrans() {
    	ResponseEntity<?> responseEntity = null;
    	HttpStatus httpStatus;
    	JsonArray jsonArr = new JsonArray();
    	
    	List<KpsApiResult02 > list = kpsTestService.getCustomerWithoutTrans();
    	
    	for(int i=0; i<list.size(); i++) {
    		JsonObject jsonObj = new JsonObject();

    		jsonObj.addProperty("year",  list.get(i).getYear());
    		jsonObj.addProperty("name",  list.get(i).getName());
    		jsonObj.addProperty("acctNo",  list.get(i).getAcctNo());
    		
    		jsonArr.add(jsonObj);
    	}
    	
    	httpStatus = HttpStatus.OK;
    	responseEntity = ResponseEntity.status(httpStatus).body(jsonArr.toString());
    	
    	return responseEntity;
    }
    
    @ApiOperation(value = "관리점별 거래금액 합계 조회", notes = "연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 조회합니다.")
    @GetMapping(value = "/managementTransAmount", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public ResponseEntity<?> getManagementTransAmount() {
    	ResponseEntity<?> responseEntity = null;
    	HttpStatus httpStatus;
    	JsonArray jsonArr = new JsonArray();
    	
    	List<KpsApiResult03> list = kpsTestService.getManagementTransAmount();
    	
    	//연도별 그룹핑
    	Map<String, List<KpsApiResult03>> grouping = list.stream().collect(Collectors.groupingBy(KpsApiResult03::getYear));
    	
    	//map sort
    	List<Map.Entry<String, List<KpsApiResult03>>> entries = new LinkedList<>(grouping.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
        LinkedHashMap<String, List<KpsApiResult03>> result = new LinkedHashMap<>();
        for (Map.Entry<String, List<KpsApiResult03>> entry : entries) {
           result.put(entry.getKey(), entry.getValue());
        }
    	
    	try {
    		ObjectMapper mapper = new ObjectMapper();
    		Iterator<String> keys = result.keySet().iterator();
    		while (keys.hasNext()){
    			String key = keys.next(); 
    			List<KpsApiResult03> root = result.get(key);
    			JsonObject jsonObj = new JsonObject();
    			JsonArray items = new JsonArray();
    			for(int i=0; i<root.size(); i++) {
    				String brName =  root.get(i).getBrName();
    				String brCode =  root.get(i).getBrCode();
    				String sumAmt =  root.get(i).getSumAmt();
    				JsonObject item = new JsonObject();
    				item.addProperty("brName", brName);
    				item.addProperty("brCode", brCode);
    				item.addProperty("sumAmt", sumAmt);
    				items.add(item);
    			}
    			jsonObj.addProperty("year", key);
    			jsonObj.add("dataList", items);
    			jsonArr.add(jsonObj);
    		}
    		
			httpStatus = HttpStatus.OK;
			responseEntity = ResponseEntity.status(httpStatus).body(jsonArr.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("code", "500");
			jsonObj.addProperty("메세지", "Internal Server Error");
		} 
    	return responseEntity;
    }
    
    @ApiOperation(value = "지점 거래금액 합계 조회", notes = "연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 조회합니다.")
    @GetMapping(value = "/branchTransAmount", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public ResponseEntity<?> getBranchTransAmount(String branch_name) {
    	ResponseEntity<?> responseEntity = null;
    	HttpStatus httpStatus;
    	JsonArray jsonArr = new JsonArray();
    	
    	if(branch_name == null) {
    		httpStatus = HttpStatus.NOT_FOUND;
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("code", "404");
			jsonObj.addProperty("메세지", "br code not found error");
    	} else {
    		if(branch_name.equals("분당점")) {
    			httpStatus = HttpStatus.NOT_FOUND;
    			JsonObject jsonObj = new JsonObject();
    			jsonObj.addProperty("code", "404");
    			jsonObj.addProperty("메세지", "br code not found error");
    			
    			responseEntity = ResponseEntity.status(httpStatus).body(jsonObj.toString());
    		} else {
    			List<KpsApiResult04> list = kpsTestService.getBranchTransAmount(branch_name);
    			
    			for(int i=0; i<list.size(); i++) {
    				JsonObject jsonObj = new JsonObject();
    				jsonObj.addProperty("brName", list.get(i).getBrName());
    				jsonObj.addProperty("brCode", list.get(i).getBrCode());
    				jsonObj.addProperty("sumAmt", list.get(i).getSumAmt());
    				jsonArr.add(jsonObj);
    			}
    			
    			httpStatus = HttpStatus.OK;
    			responseEntity = ResponseEntity.status(httpStatus).body(jsonArr.toString());
    		}
    	}
    	return responseEntity;
    }
}
