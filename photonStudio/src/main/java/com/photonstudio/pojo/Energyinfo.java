package com.photonstudio.pojo;

import lombok.Data;

@Data
public class Energyinfo {
	private Integer id;
	private Integer energytypeid;
	private String energytypename;
	private String energytypeExplain;
	private String tagnames;
	private Integer isapptotal;
	private String company;//单位
}
