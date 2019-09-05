package com.mq.kafka_producer.batchconfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

public class KafkaReader<T> implements ItemReader{
	private ConcurrentKafkaListenerContainerFactory<String, String> listener;
	private String topic;
	private static int i=0;
	private Consumer<? super String, ? super String> consumer;
	public KafkaReader(ConcurrentKafkaListenerContainerFactory<String, String> listener,String topic) {
		this.listener=listener;
		this.topic=topic;
		consumer= this.listener.getConsumerFactory().createConsumer("group-json", "consumer-01");
		consumer.subscribe(Arrays.asList(this.topic));
	}
	

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(i>0) {
			return null;
		}
		List list=new ArrayList();
		while(true) {
			ConsumerRecords<? super String, ? super String> records = consumer.poll(1000);
			i++;
			if(records.count()!=0) {
				i=0;
			}
			System.out.println(records.count()+"!!!!!!!!!!!!!!");
			for(ConsumerRecord<? super String, ? super String> record:records) {
				list.add(record.value());
			}
			
			return list;
		}
		
	}

}
