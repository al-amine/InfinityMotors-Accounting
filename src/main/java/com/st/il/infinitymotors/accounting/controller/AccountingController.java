package com.st.il.infinitymotors.accounting.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.st.il.infinitymotors.accounting.models.Order;
import com.st.il.infinitymotors.accounting.service.AccountingService;

@RestController
@RequestMapping("/accounting")
public class AccountingController {
	
	@Autowired
	private AccountingService accountingService;
	
	
	@GetMapping("/generateSalesReport")
	public ResponseEntity<Order> generateReport() {
		try {
			accountingService.generateReport();
		} 
		catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
