package com.amqp.batch_rabbitmq_xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amqp.batch_rabbitmq_xml.rabbitconfig.ObjectToXML;
import com.amqp.batch_rabbitmq_xml.rabbitconfig.User;

@SpringBootApplication
@RestController
public class rabbitXMLStarter {
	public static void main(String[] args) {
		SpringApplication.run(rabbitXMLStarter.class, args);
	}
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	Job job;
	@Autowired
	JobLauncher	jobLauncher;
	
	@RequestMapping("sendxml")
	public void sendxml() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		List<User> list1=new ArrayList<User>();
		for(int i=0;i<3;i++) {
			User u1=new User();
			u1.setId(i);
			u1.setName("aaa"+i);
			u1.setAge(20+i);
			list1.add(u1);
		}
		List<User> list2=new ArrayList<User>();
		for(int i=100;i<104;i++) {
			User u2=new User();
			u2.setId(i);
			u2.setName("bbb"+i);
			u2.setAge(20+i);
			list2.add(u2);
		}
		String xml1 = ObjectToXML.toXml(list1);
		String xml2 = ObjectToXML.toXml(list2);
		rabbitTemplate.convertAndSend("queue01", xml1);
		rabbitTemplate.convertAndSend("queue01", xml2);
		
	}
	
	@RequestMapping("launcher")
	public String launcher() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters=new JobParametersBuilder().addDate("date", new Date()).addString("ququeName", "queue01").toJobParameters();
		jobLauncher.run(job, jobParameters);
		return "SUCCESSED";
	}
}
