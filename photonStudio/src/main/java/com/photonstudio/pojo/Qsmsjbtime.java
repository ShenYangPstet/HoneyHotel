package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Qsmsjbtime {
	
	private Integer jobid;
	private Integer rwid;
	private Integer dzid;
	private String ymd;
	private Date time;

	private String dzname;
}
