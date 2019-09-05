package com.amqp.batch_rabbitmq_xml.rabbitconfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

@EnableBatchProcessing
@Configuration
public class RabbitBatchConfig {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step getStep() {
		return stepBuilderFactory.get("step1")
				.<Object,Record>chunk(10).reader(new RabbitReader<>(rabbitTemplate, "queue01"))
				.processor(new RabbitProccer()).writer(new RabbitRecordWriter())
				.build();
	}
	
	@Bean
	public Job getJob() {
		return jobBuilderFactory.get("job1")
				.flow(getStep()).end().build();
	}
	
	
}
