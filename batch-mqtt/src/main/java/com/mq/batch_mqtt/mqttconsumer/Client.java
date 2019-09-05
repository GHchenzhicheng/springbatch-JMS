package com.mq.batch_mqtt.mqttconsumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import com.mq.batch_mqtt.batchconf.MyMessageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
 
/**
 * Created by pengcheng.du on 2018/10/12.
 */
public class Client {
    public static final String SERVER_URL = "tcp://192.168.100.85:1883";
    public static final String TOPIC = "wether";
    public static final String clientid  = "wether";
 
    private static  String userName = "chenzhicheng";
    private static String passWord = "chenzhicheng123";
    private List<String> list;
 
    private ScheduledExecutorService scheduler;
 
    public  List<String> start() throws Exception{
    	list=new ArrayList<>();
    	MqttClient client = new MqttClient(SERVER_URL, clientid, new MemoryPersistence());
    	MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(20);
        options.setKeepAliveInterval(20);
        client.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out.println("主题为:"+topic);
				String str=new String(message.getPayload());
				list.add(str);
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				System.out.println("消息接收完成");
				
			}
			
			@Override
			public void connectionLost(Throwable cause) {
				 System.out.println("连接断开，可以做重连");
				
			}
		});
        client.connect(options);
        int[] Qos = {1};
        String[] topic1 = {TOPIC};
        client.subscribe(topic1,Qos);
//        IMqttMessageListener[] listener= {new MyMessageListener()};
//        client.subscribe(topic1, Qos, listener);
		return list;
        
    }
 
}
