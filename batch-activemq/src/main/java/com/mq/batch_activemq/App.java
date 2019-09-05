package com.mq.batch_activemq;

import java.util.Date;
import java.util.List;

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

import com.mq.batch_activemq.activeconfig.ActivePojo;
import com.mq.batch_activemq.activeconfig.Consumerconfig;
import com.mq.batch_activemq.activeconfig.ProducerConfig;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @RequestMapping("send")
    public String send() {
    	ActivePojo active=new ActivePojo();
    	active.setUserName("admin");
    	active.setPassword("admin");
    	active.setConUrl("tcp://192.168.100.84:61616");
    	active.setModel("topic");
    	active.setDestion("topic03");
    	ProducerConfig.send(active);
		return "SUCCESSED";
    }
    
    @RequestMapping("receive")
    public String receive() {
    	ActivePojo active=new ActivePojo();
    	active.setUserName("admin");
    	active.setPassword("admin");
    	active.setConUrl("tcp://192.168.100.84:61616");
    	active.setModel("queue");
    	active.setDestion("queue02");
    	List<String> recived = Consumerconfig.recived(active);
    	for(String s:recived) {
    		System.out.println("!!!!"+s);
    	}
		return "SUCCESSED";
    }
    
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;
    @RequestMapping("launcher")
	public String launcher() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters=new JobParametersBuilder().addDate("date", new Date()).addString("ququeName", "queue01").toJobParameters();
		jobLauncher.run(job, jobParameters);
		return "SUCCESSED";
	}
}
