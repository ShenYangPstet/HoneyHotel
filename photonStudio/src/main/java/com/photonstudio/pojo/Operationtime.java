package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Operationtime {
	private Integer id;
	private Integer drid;
	private Date drtime;
	private String explain;
	private String drname;
	private Integer userId;
	private String username;
	private Integer status;
}
