package com.mq.batch_rocketmq.batchconfig;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class RocketmqRecordWriter implements ItemWriter<Record> {

	@Override
	public void write(List<? extends Record> items) throws Exception {
		System.out.println(items.size()+">>>>>"+items);
		
	}

}
