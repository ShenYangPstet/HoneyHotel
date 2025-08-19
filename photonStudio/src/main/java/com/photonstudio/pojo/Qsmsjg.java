package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qsmsjg {
	
	@Excel(name = "结果ID")
	private Integer id;
	@Excel(name = "动作ID")
	private Integer dzid;
	@Excel(name = "变量ID")
	private Integer regid;
	@Excel(name = "变量值")
	private String value;
	
	private String dzname;
	private String regName;
	private String tagName;

}
