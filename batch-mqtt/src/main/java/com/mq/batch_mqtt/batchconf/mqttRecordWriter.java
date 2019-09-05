package com.mq.batch_mqtt.batchconf;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class mqttRecordWriter implements ItemWriter<List<Record>> {

	@Override
	public void write(List<? extends List<Record>> items) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
