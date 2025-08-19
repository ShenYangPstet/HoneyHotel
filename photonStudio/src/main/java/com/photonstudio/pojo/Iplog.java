package com.photonstudio.pojo;

import lombok.Data;

@Data
public class Iplog {
	private String ip;
	private String country;//国家
	private String region;//省份
	private String city;//市
	private String isp;//运营商
	private String area;
	private String county;
	private String country_id;
	private String area_id;
	private String region_id;
	private String city_id;
	private String county_id;
	private String isp_id;
}
