package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Buildinfo {
	@Excel(name = "建筑Id")
	private Integer buildid;
	@Excel(name = "建筑名称")
	private String buildname;
	@Excel(name = "建筑描述")
	private String buildExplain;
	@Excel(name = "项目Id")
	private Integer appid;
	@Excel(name = "项目名称")
	private String appname;
}
