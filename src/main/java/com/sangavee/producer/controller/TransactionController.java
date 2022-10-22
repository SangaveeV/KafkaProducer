package com.sangavee.producer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sangavee.producer.entity.Transaction;

@Controller
@RequestMapping
public class TransactionController {

	
	public void publishTransaction(@RequestBody Transaction transaction) {
		
	}
}
