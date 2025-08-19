package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Spinfo {
	@Excel(name = "id")
	private Integer id;
	@Excel(name = "通道号")
	private String spId;
	@Excel(name = "视频地址",width = 30)
	private String spDllPath;
	@Excel(name = "视频ip",width = 20)
	private String spIp;
	@Excel(name = "视频端口")
	private String spPort;
	//@Excel(name = "相机")
	private String spCamera;
	@Excel(name = "用户")
	private String spUser;
	@Excel(name = "密码")
	private String spPassword;
	@Excel(name = "状态")
	private String spPtztype;
	@Excel(name = "监控")
	private String monitor;
	@Excel(name = "摄像头类型")
	private Integer spType;//摄像头类型1 大华 2海康等等
}
