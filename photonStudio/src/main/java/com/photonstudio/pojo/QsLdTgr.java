package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
@Data
public class QsLdTgr {
	@Excel(name = "联动条件Id")
	private Integer ldTjId;
	@Excel(name = "联动任务Id")
	private Integer ldRwId;
	@Excel(name = "联动条件名字",width = 15)
	private String ldTjName;
	@Excel(name = "变量Id")
	private Integer regId;
	@Excel(name = "变量最大值")
	private String valueMin;
	@Excel(name = "变量最小值")
	private String valueMax;
	@Excel(name = "条件")
	private String valueAndOr;
	@Excel(name = "时间")
	private String time;
	private String ldRwName;
	private String regName;
	@Excel(name = "条件类型,1变量条件2时间条件")
	private Integer tjType;
	private Integer state;
}
