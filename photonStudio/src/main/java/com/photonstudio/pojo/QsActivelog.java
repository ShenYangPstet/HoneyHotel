package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class QsActivelog {
	private Integer activelogId;
	private Date time;
	private String userName;
	private Integer drTypeid;
	private String drName;
	private String regName;
	private String regNewvalue;
	private String drTypeName;
}
