package com.mq.batch_mqtt.batchconf;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;


public class mqttProccer implements ItemProcessor<List<String>,List<Record>>{

	@Override
	public List<Record> process(List<String> item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
