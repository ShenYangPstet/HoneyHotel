package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Appusergroup {
	private Integer id;
	private Integer userid;
	private Integer appid;
	private Integer appuserground;
	private String appName;
	private String ipaddr;
	private String appport;
	private String username;
	private String phonenum;
	private	String email;
	private String appexplain;
	private Integer drCount;
	private String usergroupname;
	private String region;
	private String apptypename;
	private String city;
	private String longitude;
	private String latitude;
	private String appIntroduction;
	private List<Sysmenuinfo> menuList;
	private Appinfo appinfo;
	private Integer appSumday;
	private Integer drmalfunctionSum;
	private Integer control;//控制下发权限 0无权限下发1有权限下发
	private Integer regShowLevel;//查看变量权限等级 1低权限 2中权限 3高权限
	private String skin;//主题皮肤
}
