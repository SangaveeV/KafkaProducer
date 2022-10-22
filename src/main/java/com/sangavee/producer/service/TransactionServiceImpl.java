package com.sangavee.producer.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import com.sangavee.producer.entity.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private KafkaTemplate<String, Transaction> kafkaTemplate;
	
	@Value("${kafka.topic.name}")
	private String topicName;

	@Override
	public void publishTransactionMessage(Transaction transaction) {
		
		publishMessage(transaction);
	}
	
	public void publishMessage(Transaction transaction) {
		
	//	ListenableFuture<SendResult<String, Transaction>> future = kafkaTemplate.send(topicName, transaction);
		
		ProducerRecord<String, Transaction> producerRecord = new ProducerRecord<String, Transaction>(topicName, transaction);
		producerRecord.headers()
		.add(KafkaHeaders.MESSAGE_KEY, transaction.getId().toString().getBytes())
		.add(KafkaHeaders.TIMESTAMP,new SimpleDateFormat().format(new Date()).getBytes())
		.add(KafkaHeaders.TOPIC,topicName.getBytes());
			
		ListenableFuture<SendResult<String, Transaction>> future = kafkaTemplate.send(topicName, transaction);
		SuccessCallback<? super SendResult<String, Transaction>> successCallback =sendResult -> {
			 System.out.println("Sent message with offset=[" 
		          + sendResult.getRecordMetadata().offset() + "]" +" to topic-partition@offset = " +
					 sendResult.getRecordMetadata().toString());
		};
		FailureCallback failureCallback= throwable -> {
			System.out.println("Message to topic " + topicName + " Failed !!");
		};
		future.addCallback(successCallback,failureCallback);
	}

}
