package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Qsmsjblog {
	
	private Integer id;
	private Integer dzid;
	private String dzname;
	private String regname;
	private String value;
	private Integer state;
	private Date sendtime;
	private Date finshtime;

}
