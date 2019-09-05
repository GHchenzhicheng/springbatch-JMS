package com.amqp.batch_rabbitmq_xml.rabbitconfig;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class RabbitRecordWriter implements ItemWriter<Record> {

	@Override
	public void write(List<? extends Record> items) throws Exception {
		System.out.println(items);
		
	}

}
