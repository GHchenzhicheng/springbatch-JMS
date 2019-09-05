package com.amqp.batch_rabbitmq_xml.rabbitconfig;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.batch.item.ItemProcessor;



public class RabbitProccer implements ItemProcessor{

	@Override
	public Object process(Object item) throws Exception {
		SAXReader saxReader=new SAXReader();
		List<Record> recordList=null;
		Document doc=null;
		try {
			doc = saxReader.read(new ByteArrayInputStream(item.toString().getBytes("UTF-8")));
			 Element rootElement = doc.getRootElement();
			 Iterator<Element> it = rootElement.elementIterator();
			 recordList=new ArrayList<Record>();
			 while(it.hasNext()) {
				 Record record=new Record();
				 LinkedHashMap<String, Object> xmlMap=new LinkedHashMap<>();
				 Element parentElement = it.next();
				 List<Attribute> innerattributes = parentElement.attributes();
				 if(innerattributes.size()!=0) {
					 for(Attribute attribute:innerattributes) {
						 xmlMap.put(attribute.getName(), attribute.getValue());
					 }
				 }
				 Iterator<Element> elementIterator = parentElement.elementIterator();
				 while(elementIterator.hasNext()) {
					 Element childElement = elementIterator.next();
					 xmlMap.put(childElement.getName(), childElement.getText());
				 }
				 record.setMap(xmlMap);
				 recordList.add(record);
			 }
		} catch (Exception e) {
			System.out.println("解析xml出现错误");
			e.printStackTrace();
		}
		
		return recordList;
		
	}

	

}
