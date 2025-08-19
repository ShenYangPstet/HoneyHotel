package com.photonstudio.pojo;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Appuser {
	private Integer userid;
	private String username;
	private String password;
	private String phonenum;
	private String email;
	private Integer usergroupid;
	private String usergroupname;
	private Integer departmentid;
	private String departmentname;
	private Integer usertype;
	private Integer regShowLevel;//变量权限 123
	private Integer isJB;
	private Integer permission;//手机app 权限（1用户端 0维修端）
	private List<Integer> dridlist;
}
