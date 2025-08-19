package com.photonstudio.common;

import java.util.HashMap;
import java.util.Map;

public class QsLdTjMapUtil {
	//key为LdTjId value为 tagvalue的值
	private static Map<String, String> qsLdTjMap= new HashMap<String, String>();
	
	public static void put(String key,String value) {
		qsLdTjMap.put(key, value);
	}
	public static String get(String key) {
		return qsLdTjMap.get(key);
	}
	public static void remove(String key) {
		qsLdTjMap.remove(key);
	}
}
