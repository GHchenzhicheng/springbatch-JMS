package com.mq.batch_rocketmq.batchconfig;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class RocketMQConfig {
	
	private List<String> list;
	public  List<String> getMessage(){
		list=new ArrayList<>();
		DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("groupId");
		consumer.setNamesrvAddr("192.168.100.85:9876");
		consumer.setConsumeThreadMin(20);
		consumer.setConsumeThreadMax(64);
//		设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费 如果非第一次启动，那么按照上次消费的位置继续消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//		设置一次消费消息的条数，默认为1条
		consumer.setConsumeMessageBatchMaxSize(1);
		consumer.setInstanceName(Long.toString(System.currentTimeMillis()));
	consumer.registerMessageListener(new MessageListenerConcurrently() {
			
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				if(CollectionUtils.isEmpty(msgs)){
					System.out.println("接收到的消息为空，不做任何处理");
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				
				for(MessageExt msg:msgs) {
					String topic = null;
					String body = null;
					try {
						topic = msg.getTopic();
						body=new String(msg.getBody(), "UTF-8");
						list.add(body);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				
				
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		try {
			consumer.subscribe("Demo_topic01", "*");
			consumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		return list;
	}
}
