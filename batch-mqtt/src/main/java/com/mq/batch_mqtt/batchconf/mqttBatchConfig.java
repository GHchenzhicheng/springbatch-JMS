package com.mq.batch_mqtt.batchconf;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mq.batch_mqtt.mqttconsumer.Client;


@EnableBatchProcessing
@Configuration
public class mqttBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	/*
	 * @Bean public ItemReader getReader(){ ItemReaderAdapter adapter=new
	 * ItemReaderAdapter(); adapter.setTargetObject(new Client());
	 * adapter.setTargetMethod("start"); // MyItemAdapter adapter=new
	 * MyItemAdapter(); return adapter; }
	 */
	
	@Bean
	public Step getStep() {
		return stepBuilderFactory.get("step1")
				.<List<String>,List<Record>>chunk(10).reader(new mqttReader(new Client()))
				.processor(new mqttProccer()).writer(new mqttRecordWriter())
				.build();
	}
	
	@Bean
	public Job getJob() {
		return jobBuilderFactory.get("job1")
				.flow(getStep()).end().build();
	}
	
	
}
