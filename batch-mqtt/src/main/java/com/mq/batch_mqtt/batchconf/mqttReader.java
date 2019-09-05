package com.mq.batch_mqtt.batchconf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.CollectionUtils;

import com.mq.batch_mqtt.mqttconsumer.Client;

public class mqttReader implements ItemReader<List<String>>{
	private Client client;
	private static int i=0;
	public mqttReader(Client client) {
		this.client=client;
	}
	
	@Override
	public List<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(i>0) {
			return null;
		}
		 List<String> list = client.start();
		 i++;
		 if(list.size()==0) {
			 i=0;
		 }
		 System.out.println(list);
		return list;
	
	}

}
