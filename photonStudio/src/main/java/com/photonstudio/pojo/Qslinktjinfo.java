package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qslinktjinfo {
	
	private Integer id;
	@Excel(name = "条件ID")
	private Integer tjId;
	@Excel(name = "变量ID")
	private Integer regId;
	@Excel(name = "最小值")
	private String minValue;
	@Excel(name = "最大值")
	private String maxValue;
	@Excel(name = "关系  1是与 0是或")
	private Integer relation;
	
	private String regName;
	private String tagName;
	private String tjName;

}
