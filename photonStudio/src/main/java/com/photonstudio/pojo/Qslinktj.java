package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qslinktj {
	@Excel(name = "条件id")
	private Integer tjId;
	@Excel(name = "条件名称")
	private String tjName;

}
