package com.amqp.batch_rabbitmq_xml.rabbitconfig;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class ObjectToXML {
	public static String toXml(List<User> list) {
		XStream xs=new XStream();
		User user = list.get(0);
		xs.alias("user", user.getClass());
		xs.alias("list", list.getClass());
		return xs.toXML(list);
	}
	
}
