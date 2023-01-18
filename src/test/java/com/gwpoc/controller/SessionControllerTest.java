package com.gwpoc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwpoc.client.CachClient;
import com.gwpoc.model.request.SessionRequest;
import com.gwpoc.model.response.AccountResponse;
import com.gwpoc.model.response.SessionResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

	@Autowired
	MockMvc mvc;
	@MockBean
	CachClient cache;
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void insertCacheTestOK() throws Exception {
		
		SessionRequest request = new SessionRequest();
		request.setBt("bt1");
		request.setChannel("web");
		request.setScope("l1");
		
		SessionResponse iResp = new SessionResponse();
		iResp.setInsert(true);
		
		when(cache.createSession(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/sess/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		SessionResponse response = mapper.readValue(resp, SessionResponse.class);
		
		assertThat(response.getInsert()).isTrue();
	}
	
	@Test
	public void getCacheTestOK() throws Exception {
		
		SessionRequest request = new SessionRequest();
		request.setBt("bt1");
		request.setChannel("web");
		request.setScope("l1");
		
		SessionResponse iResp = new SessionResponse();
		iResp.setInsert(true);
		iResp.setBt("bt1");
		iResp.setScope("l1");
		iResp.setValid(true);
		
		when(cache.getSession(any())).thenReturn(iResp);
		
		String resp = mvc.perform(post("/sess/get")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		SessionResponse response = mapper.readValue(resp, SessionResponse.class);
		
		assertThat(response.getScope()).isEqualTo("l1");
	}
}
