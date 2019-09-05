package com.mq.batch_activemq.batchconfig;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class activeWriter implements ItemWriter<List<String>>{

	@Override
	public void write(List<? extends List<String>> items) throws Exception {
		for(List<String> l:items) {
			for(String s:l) {
				System.out.println("......."+s);
			}
		}
		
	}

}
