package com.mq.batch_activemq.batchconfig;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mq.batch_activemq.activeconfig.ActivePojo;

@Configuration
@EnableBatchProcessing
public class activeBatchConfig {
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Step getstep() {
		return this.stepBuilderFactory.get("step01")
				.<List<String>,List<String>>chunk(10)
				.reader(new activereader(new ActivePojo()))
				.writer(new activeWriter())
				.build();
	}
	
	@Bean
	public Job getJob() {
		return this.jobBuilderFactory.get("job01")
				.start(getstep()).build();
	}
}
