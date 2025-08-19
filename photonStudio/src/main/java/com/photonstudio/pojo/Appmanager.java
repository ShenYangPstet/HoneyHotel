package com.photonstudio.pojo;

import lombok.Data;

@Data
public class Appmanager {
	private Integer appid;
	private String appName;
	private String ipaddr;
	private String appport;
	private Integer apptypeid;
	private String appimg;
	private String appexplain;
	private String appstate;
	private String state;
	private String apptypename;
	private String region;//省份
	private String city;//城市
	private String areaNumber;//行政区划编码
	private String longitude;//经度
	private String latitude;//纬度
	private String appIntroduction;//项目介绍
	

}
