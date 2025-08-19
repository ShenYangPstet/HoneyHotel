package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qsmsrw {
	
	@Excel(name = "模式ID")
	private Integer id;
	@Excel(name = "设备类型ID")
	private Integer drtypeid;
	@Excel(name = "模式名称")
	private String msname;
	@Excel(name = "颜色")
	private String color;
	@Excel(name = "类型")
	private Integer type;
	
	private String drtypename;

}
