package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;
@Data
public class SmsLog {
	private Integer id;
	private Date sendtime;
	private String sendphone;
	private String sendcontent;
	private String sendname;
	private Integer sendstate;
}
