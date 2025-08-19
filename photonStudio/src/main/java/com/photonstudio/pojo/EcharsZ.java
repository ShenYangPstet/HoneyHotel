package com.photonstudio.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class EcharsZ {//柱状图数据
	private String name;	//名称
	private String resultType;//说明
	private String dateStr;//查询的日期
	private Map<String,String> QeuryMap;//数据
}
