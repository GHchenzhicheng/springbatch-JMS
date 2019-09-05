package com.mq.batch_mqtt.batchconf;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMessageListener implements IMqttMessageListener{

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(new String(message.getPayload())+"!!!!!!!!!!!!!!");
		
	}

}
