package com.sangavee.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sangavee.producer.entity.Transaction;
import com.sangavee.producer.service.TransactionServiceImpl;

@Controller
@RequestMapping("/kafka")
public class TransactionController {

	@Autowired
	TransactionServiceImpl transactionService;
	
	@PostMapping(path= "/publishMessage" ,consumes = "application/json" )
	public ResponseEntity<String> publishTransaction(@RequestBody Transaction transaction) {
		transactionService.publishTransactionMessage(transaction);
		return new ResponseEntity<String>("Transaction Published", HttpStatus.ACCEPTED);
	}
}
