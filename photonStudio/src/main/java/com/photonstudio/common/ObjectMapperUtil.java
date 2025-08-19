package com.photonstudio.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ObjectMapperUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	public static String toJSON(Object object) {
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	public static <T> T toObject(String json,Class<T> targetClass) {
		T t = null;	//定义泛型对象
		try {
			t = mapper.readValue(json,targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;		
	}
	public static <T> List<T> toListObject(String json,
            Class<T> elementClass) {
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
		List<T> list = null;
		try {
			list = mapper.readValue(json, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 *  List<T> listll = mapper.readValue(json, new TypeReference<List<T>>(){});
		 */
		return list;
	}
}
