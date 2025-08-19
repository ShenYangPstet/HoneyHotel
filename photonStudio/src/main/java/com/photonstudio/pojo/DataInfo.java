package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class DataInfo {
	private Integer id;
	private Date time;
	private String tagname;
	private String tagvalue;

}
