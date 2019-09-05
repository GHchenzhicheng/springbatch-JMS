package com.mq.kafka_producer.batchconfig;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;



public class KafkaProccer implements ItemProcessor{

	@Override
	public Object process(Object item) throws Exception {
		System.out.println(item+"?????????");
		return item;
	}

	

}
