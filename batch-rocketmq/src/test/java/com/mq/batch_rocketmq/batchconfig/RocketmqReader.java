package com.mq.batch_rocketmq.batchconfig;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class RocketmqReader<T> implements ItemReader{
	
	private RocketMQConfig conf;
	private static int i=0;
	public RocketmqReader(RocketMQConfig conf) {
		this.conf=conf;
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(i>0) {
			return null;
		}
		System.out.println(i);
		
		List<String> message = this.conf.getMessage();
		System.out.println(message);
		i++;
//		防止第一次有可能接受的消息为空
		if(message.size()==0) {
			i=0;
		}
		return (T) message;
	
	}

}
