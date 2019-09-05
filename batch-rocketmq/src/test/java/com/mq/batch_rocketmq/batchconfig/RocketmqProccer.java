package com.mq.batch_rocketmq.batchconfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RocketmqProccer implements ItemProcessor{

	@Override
	public Object process(Object item) throws Exception {
		System.out.println(item+"????");
		/*
		 * if(StringUtils.isBlank(item.toString())) { return null; } Map<String, Object>
		 * parse = (Map<String, Object>) JSONObject.parse(item.toString());
		 * Set<Entry<String, Object>> entrySet = parse.entrySet(); Record record=new
		 * Record(); LinkedHashMap<String, Object> map=new LinkedHashMap<>();
		 * for(Entry<String, Object> entry:entrySet) { map.put(entry.getKey(),
		 * entry.getValue()); } record.setMap(map);
		 */
		return item;
	}

	

}
