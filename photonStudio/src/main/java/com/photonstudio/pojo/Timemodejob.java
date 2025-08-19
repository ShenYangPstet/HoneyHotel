package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Timemodejob {
	
	private Integer id;
	private Integer timemodeid;
	private Integer ldTjId;
	private Date time;
	private Integer ldRwId;
	private String timemodeName;
	private String ldTjName;
	private String ldRwName;

}
