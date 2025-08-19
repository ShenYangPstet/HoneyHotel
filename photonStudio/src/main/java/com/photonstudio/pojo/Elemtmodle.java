package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Elemtmodle {
	
	private Integer modleId;
	private String modleName;
	private Integer modleLevel;
	
	private List<Elemttype> elemttypeList;

}
