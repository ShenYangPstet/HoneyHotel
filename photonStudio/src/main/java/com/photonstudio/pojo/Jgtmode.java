package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Jgtmode {
	private Integer id;
	private Integer typeid;
	private String typemodelevel;
	private Integer elemttypeid;
	private String jgtx;
	private String jgty;
	private String zIndex;
	private String type;
	private Integer jgth;
	private Integer jgtw;
	private Integer angle;
	
	private Drtypemode drtypemode; 
	private List<DrtypeElements> drtypeElementsList;

}
