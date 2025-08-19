package com.photonstudio.common;

import java.util.HashMap;
import java.util.Map;

public class SpinfoMapUtil {
	//key为spinfoId value为 tagvalue的值
	private static Map<Integer, ProcessBuilder> spinfoMap= new HashMap<>();
	
	public static void put(Integer key,ProcessBuilder value) {
		spinfoMap.put(key, value);
	}
	public static ProcessBuilder get(Integer key) {
		return spinfoMap.get(key);
	}
	public static void remove(Integer key) {
		spinfoMap.remove(key);
	}
	public static void removeAll() {
		spinfoMap.clear();
	}
}
