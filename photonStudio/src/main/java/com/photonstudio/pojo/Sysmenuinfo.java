package com.photonstudio.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sysmenuinfo {
	private Integer id;
	private String menuName;
	private Integer parentId;
	private String menuPath;
	private String menuLevel;
	private Integer picid;
	private String isshow;
	private String menuindex;
	private List<Sysmenuinfo> children;
	
	private Integer picmodeid;
}
