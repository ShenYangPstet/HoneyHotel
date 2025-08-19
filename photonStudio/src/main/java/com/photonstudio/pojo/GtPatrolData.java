package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class GtPatrolData {
	private Integer ID;
	private Date Patrol_Time;
	private String User_Number;
	private String User_Name;
	private String Device_Number;
	private String Device_Name;
	private String Place_Number;
	private String Place_Name;
	private String DepartMent_Number;
	private String Dflag;
	private Date Create_Time;
	private Integer Info_ID;
	
}
