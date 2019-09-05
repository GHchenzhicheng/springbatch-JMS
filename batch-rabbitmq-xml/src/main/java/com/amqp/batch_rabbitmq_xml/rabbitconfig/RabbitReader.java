package com.amqp.batch_rabbitmq_xml.rabbitconfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class RabbitReader<T> implements ItemReader{
	
	private RabbitTemplate rabbitTemplate;
	private String QueueName;
	
	public RabbitReader(RabbitTemplate rabbitTemplate,String QueueName) {
		this.rabbitTemplate=rabbitTemplate;
		this.QueueName=QueueName;
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		T recived=(T)rabbitTemplate.receiveAndConvert(QueueName);
		System.out.println(recived);
		return recived;
	}

}
