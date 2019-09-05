package com.mq.batch_activemq.activeconfig;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

public class ProducerConfig {
	
	public static void send(ActivePojo active) {
		ActiveMQConnectionFactory factory=null;
		Connection connection =null;
		Session session =null;
		try {
			factory=new ActiveMQConnectionFactory(active.getUserName(), active.getPassword(), active.getConUrl());
			connection = factory.createConnection();
			connection.start();
			session= connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
			if("queue".equals(active.getModel())) {
				Queue queue = session.createQueue(active.getDestion());
				MessageProducer producer = session.createProducer(queue);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				sendMessage(session,producer);
				session.commit();
			}else if ("topic".equals(active.getModel())) {
				Topic topic = session.createTopic(active.getDestion());
				MessageProducer producer = session.createProducer(topic);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				sendMessage(session,producer);
				session.commit();
			}
			
		} catch (Exception e) {
			System.out.println("生产者发送消息失败");
		}finally {
			if(session!=null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

	public static void sendMessage(Session session, MessageProducer producer) {
		
		for(int i=0;i<10;i++) {
			try {
				TextMessage createTextMessage = session.createTextMessage("hello ActiveMQ $$$$$$"+i);
				producer.send(createTextMessage);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
