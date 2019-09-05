package com.mq.kafka_producer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mq.kafka_producer.config.KafkaConsumerConfig;
import com.mq.kafka_producer.config.KafkaProducerConfig;
import com.mq.kafka_producer.pojo.Student;

@SpringBootApplication
@RestController
public class kafkaStarterConsumer {
	
	public static void main(String[] args) {
		SpringApplication.run(kafkaStarterConsumer.class, args);
	}
	
	@RequestMapping("sendjson")
	public  String sendData() {
		KafkaTemplate<String, String> kafkaTemplate = KafkaProducerConfig.kafkaTemplate();
		for(int i=100;i<150;i++) {
			Student s=new Student();
			s.setId(i);
			s.setName("aaa"+i);
			s.setBirth(new Date(System.currentTimeMillis()));
			s.setAge(20+i);
			String jsonString = JSONObject.toJSONString(s);
			kafkaTemplate.send("json-data", jsonString);
		}
		return "aaaa";
	}
	@RequestMapping("received")
	public void received() {
		ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = (ConcurrentKafkaListenerContainerFactory<String, String>) KafkaConsumerConfig.kafkaListenerContainerFactory();
		Consumer<? super String, ? super String> createConsumer = kafkaListenerContainerFactory.getConsumerFactory().createConsumer();
		createConsumer.subscribe(Arrays.asList("json-data"));
		while(true) {
			ConsumerRecords<? super String, ? super String> records = createConsumer.poll(1000);
			for(ConsumerRecord<? super String, ? super String> record:records) {
				System.out.println(record.offset()+":::"+record.value());
			}
		}
		
		
	}
	
	@Autowired
	Job job;
	@Autowired
	JobLauncher	jobLauncher;
	@RequestMapping("launcher")
	public void launcher() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters=new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		jobLauncher.run(job, jobParameters);
	}
	
}
