package com.mq.kafka_producer.batchconfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import com.mq.kafka_producer.config.KafkaConsumerConfig;

@EnableBatchProcessing
@Configuration
public class KafkaBatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step getStep() {
		return stepBuilderFactory.get("step1")
				.<Object,Record>chunk(10).reader(new KafkaReader((ConcurrentKafkaListenerContainerFactory<String, String>)(KafkaConsumerConfig.kafkaListenerContainerFactory()), "json-data"))
				.processor(new KafkaProccer()).writer(new KafkaRecordWriter())
				.build();
	}
	
	@Bean
	public Job getJob() {
		return jobBuilderFactory.get("job1")
				.flow(getStep()).end().build();
	}
	
	
}
