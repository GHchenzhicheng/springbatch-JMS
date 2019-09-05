package com.mq.batch_activemq.activeconfig;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

public class Consumerconfig {
	private static List<String> list = new ArrayList<String>();

	public static List<String> recived(ActivePojo active) {
		ActiveMQConnectionFactory factory=null;
		Connection connection =null;
		Session session=null;
		try {
			factory=new ActiveMQConnectionFactory(active.getUserName(), active.getPassword(), active.getConUrl());
			connection = factory.createConnection();
			connection.start();
			session= connection.createSession(false, ActiveMQSession.AUTO_ACKNOWLEDGE);
			if("queue".equals(active.getModel())) {
				Queue queue = session.createQueue(active.getDestion());
				MessageConsumer consumer = session.createConsumer(queue);
//				consumer.setMessageListener(new MessageListener() {
//					
//					@Override
//					public void onMessage(Message message) {
//						try {
//							String text = ((TextMessage)message).getText();
//							list.add(text);
//						} catch (JMSException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//					}
//				});
				
				  while(true) { 
					  TextMessage receive = (TextMessage) consumer.receive(30000);
					// System.out.println(receive.getText()); 
					  list.add(receive.getText());
				  }
				  
				 
			}else if("topic".equals(active.getModel())){
				Topic topic = session.createTopic(active.getDestion());
				MessageConsumer consumer = session.createConsumer(topic);
//				while(true) {
//					consumer.setMessageListener(new MessageListener() {
//						
//						@Override
//						public void onMessage(Message message) {
//							try {
//								String text = ((TextMessage)message).getText();
//								System.out.println(text);
//								list.add(text);
//							} catch (JMSException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//						}
//					});
//				}
				while(true) {
					TextMessage receive = (TextMessage) consumer.receive(30000);
//					System.out.println(receive.getText());
					list.add(receive.getText());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
