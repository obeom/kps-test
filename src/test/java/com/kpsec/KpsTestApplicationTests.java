package com.kpsec;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import com.kpsec.test.TestApplication;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@NoArgsConstructor
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class KpsTestApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeAll
	static void beforeAll() {
		
	}
	
	@BeforeEach
	public void beforeEach() {
		objectMapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.modules(new JavaTimeModule())
				.build();
	}
	
	@DisplayName("연도별 합계 금액이 가장 많은 고객 조회")
	@Test
	public void getCoustHighSumAmtByYear() throws Exception {
		String url = "/kps/coustHighSumAmt";
		
		mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"))
		.andExpect(result -> {MockHttpServletResponse response = result.getResponse();
			log.info(response.getContentAsString());
			});
	}
	
	@DisplayName("2018년 또는 2019년에 거래가 없는 고객 조회")
	@Test
	public void getCustomerWithoutTrans() throws Exception {
		String url = "/kps/customerWithoutTrans";
		
		mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"))
		.andExpect(result -> {MockHttpServletResponse response = result.getResponse();
			log.info(response.getContentAsString());
			});
	}
	
	@DisplayName("관리점별 거래금액 합계 조회")
	@Test
	public void getManagementTransAmount() throws Exception {
		String url = "/kps/managementTransAmount";
		mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"))
		.andExpect(result -> {MockHttpServletResponse response = result.getResponse();
			log.info(response.getContentAsString());
			});
	}
	
	
	@DisplayName("지점 거래금액 합계 조회")
	@Test
	public void getBranchTransAmount() throws Exception {
		String branch_name = "강남점";
		String url = "/kps/branchTransAmount?branch_name="+branch_name;
		mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"))
		.andExpect(result -> {MockHttpServletResponse response = result.getResponse();
			log.info(response.getContentAsString());
			});
	}
}
