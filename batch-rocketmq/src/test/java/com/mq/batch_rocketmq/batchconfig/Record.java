package com.mq.batch_rocketmq.batchconfig;

import java.util.LinkedHashMap;

public class Record {
	private LinkedHashMap<String, Object> map;

	public LinkedHashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(LinkedHashMap<String, Object> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Record [map=" + map + "]";
	}
}
