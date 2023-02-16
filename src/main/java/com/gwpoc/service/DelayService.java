package com.gwpoc.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DelayService {

	public String reflecting(){
		
		 try {
		        Thread.sleep(5000); // Sleep for 5 seconds (5000 milliseconds)
		    } catch (InterruptedException e) {
		        // Handle any exceptions that may occur
		    }
		
		return "delay";
	}
}
