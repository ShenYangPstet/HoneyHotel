package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Appinfo {
	private Integer appid;
	private String appname;
	private Integer applogotype;
	private String applogotext;
	private String applogoimg;
	private String apppic;
	private Integer appman;
	private String apparea;
	private Date appdate;
	private String appreserve1;
	private String appreserve2;
}
