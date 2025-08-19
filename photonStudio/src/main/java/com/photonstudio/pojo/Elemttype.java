package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Elemttype {
	private Integer elemttypeid;
	private String elemttypename;
	private Integer typeid;
	private Integer imgid;
	
	private String imgurl;
	
	private List<Elemttypemode> elemttypemodeList;
}
