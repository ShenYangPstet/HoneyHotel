package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class DrCheckLog {
	private Integer id;
	private Date time;
	private Integer drId;
	private String drName;
	private Integer drState;
	private String username;
	private String checkImg;
}
