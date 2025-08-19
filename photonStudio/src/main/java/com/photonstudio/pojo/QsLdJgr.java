package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class QsLdJgr {
	@Excel(name = "联动结果id",width = 15)
	private Integer ldJgId;
	@Excel(name = "联动任务id",width = 15)
	private Integer ldRwId;
	@Excel(name = "任务名称")
	private String ldRwName;
	@Excel(name = "任务描述",width = 30)
	private String ldRwMiaoshu;
	@Excel(name = "联动结果名称")
	private String ldJgName;
	@Excel(name = "联动类型")
	private Integer ldLxId;
	@Excel(name = "联动参数id")
	private Integer regId;
	@Excel(name = "联动参数名称",width = 20)
	private String regName;
	@Excel(name = "参数值")
	private String tagValue;
	@Excel(name = "视频ID")
	private Integer spId;
	@Excel(name = "页面id")
	private Integer picId;
	@Excel(name = "页面名称")
	private String picname;
	@Excel(name = "页面参数")
	private String picParameter;
	private String phone;
	private String message;
	private String reserve;
}
