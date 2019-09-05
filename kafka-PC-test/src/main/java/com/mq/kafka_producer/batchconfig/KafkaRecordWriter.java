package com.mq.kafka_producer.batchconfig;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class KafkaRecordWriter implements ItemWriter<Record> {

	@Override
	public void write(List<? extends Record> items) throws Exception {
		System.out.println("$$$$$$$$$$"+items);
		
	}

}
