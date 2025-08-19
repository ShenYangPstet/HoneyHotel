package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qslinkjginfo {

	private Integer id;
	@Excel(name = "结果ID")
	private Integer jgId;
	@Excel(name = "变量ID")
	private Integer regId;
	@Excel(name = "变量值")
	private String value;
	@Excel(name = "视频ID")
	private String spId;
	@Excel(name = "页面ID")
	private Integer picId;
	@Excel(name = "页面参数")
	private String picParameter;
	@Excel(name = "电话号码")
	private String phone;
	@Excel(name = "短信内容")
	private String message;
	private String reserve;//预留

	private String picname;
	private String regName;
	private String tagName;
	private String jgName;
}
