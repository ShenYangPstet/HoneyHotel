package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qslinkjg {
	@Excel(name = "结果id")
	private Integer jgId;
	@Excel(name = "结果名称")
	private String jgName;
}
