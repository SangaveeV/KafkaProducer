package com.sangavee.producer.service;

import com.sangavee.producer.entity.Transaction;

public interface TransactionService {

	public void publishTransactionMessage(Transaction transaction);
}
