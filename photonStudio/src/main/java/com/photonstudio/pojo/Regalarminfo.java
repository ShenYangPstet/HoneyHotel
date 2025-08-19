package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Regalarminfo {
	@Excel(name = "条件id")
	private Integer id;
	@Excel(name = "变量id")
	private Integer regId;
	@Excel(name = "变量名称")
	private String regname;
	@Excel (name = "条件值低")
	private String valueMin;
	@Excel (name = "条件值高")
	private String valueMax;
	@Excel(name = "条件或与")
	private String andOr;
	@Excel(name = "报警级别")
	private String alarmLevel;
	@Excel(name = "报警类型")
	private String alarmtype;
	@Excel(name = "报警描述")
	private String alarmexplain;
	@Excel(name = "说明书id")
	private String instructionsId;

}
