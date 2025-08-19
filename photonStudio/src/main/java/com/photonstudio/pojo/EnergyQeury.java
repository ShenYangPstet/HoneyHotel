package com.photonstudio.pojo;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class EnergyQeury {
	private int energyid;//配置能耗id
	private String energytypename;
	private String resultType;//0--总能耗 1 --当日能耗
	private String dateStr;
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Map<String,String> energyQeuryMap;
	private  Map<String,String> energyQeuryMap2;
	private Float MomSum;//总值;
	private String dateStr2;//第2个时间
	private String company;//单位
}
