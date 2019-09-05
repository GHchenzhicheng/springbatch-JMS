package com.mq.batch_activemq.batchconfig;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.mq.batch_activemq.activeconfig.ActivePojo;
import com.mq.batch_activemq.activeconfig.Consumerconfig;

public class activereader implements ItemReader<List<String>>{
	private ActivePojo active;
	private static int i=0;
	public activereader(ActivePojo active) {
		this.active=active;
		this.active.setUserName("admin");
		this.active.setPassword("admin");
		this.active.setConUrl("tcp://192.168.100.84:61616");
		this.active.setModel("topic");
		this.active.setDestion("topic03");
	}
	
	@Override
	public List<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		if(i>0) {
			return null;
		}
		List<String> recived = Consumerconfig.recived(this.active);
		i++;
		if(recived.size()==0) {
			i=0;
		}
		return recived;
	}

}
