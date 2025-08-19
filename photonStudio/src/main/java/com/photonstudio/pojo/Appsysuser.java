package com.photonstudio.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class Appsysuser {
	private Integer id;
	private String username;
	private String password;
	private String phonenum;
	private String email;
	private String licensetype;
	private Date licensetime;
	private Integer role;
	private List<Appusergroup> appusergroup;
	private String xmsqtime;//项目授权时间
	private Integer permission;//APP角色1用户端0维修端
	private String skinColor;
}
