package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qslinkrw {

	private Integer rwId;
	@Excel(name = "任务名称")
	private String rwName;
	@Excel(name = "条件ID")
	private Integer tjId;
	@Excel(name = "结果ID")
	private Integer jgId;
	
	private String tjName;
	private String jgName;
}
