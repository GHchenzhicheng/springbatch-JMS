//package com.mq.batch_mqtt.batchconf;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.adapter.AbstractMethodInvokingDelegator;
//import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.mq.batch_mqtt.mqttconsumer.Client;
//
//public class MyItemAdapter extends AbstractItemCountingItemStreamItemReader {
//	private Client client=new Client();
//	private ArrayList<String> list;
//	int size;
//	public MyItemAdapter() {
//		setName("myItemAdapter01");
//	}
//	
//	@Override
//	protected String doRead() throws Exception {
//		if(size<=0) {
//			return null;
//		}
//		String string = list.get(size-1);
//		size--;
//		return string;
//		
//	}
//
//	@Override
//	protected void doOpen() throws Exception {
//		list = client.start();
//		size = list.size();
//	}
//
//	@Override
//	protected void doClose() throws Exception {
//		setCurrentItemCount(0);
//	}
//
//
//}
