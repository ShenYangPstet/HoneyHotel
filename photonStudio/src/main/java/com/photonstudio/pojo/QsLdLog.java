package com.photonstudio.pojo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
@Data
public class QsLdLog {
	private Integer id;
	private Integer ldTjId;
	private Integer ldRwId;
	private Integer ldJgId;
	@Excel(name = "任务名称")
	private String ldRwName;
	@Excel(name = "条件名称")
	private String ldTjName;
	@Excel(name = "结果名称")
	private String ldJgName;
	@Excel(name = "结果类型")
	private Integer type;
	@Excel(name = "是否处理")
	private Integer state;
	@Excel(name = "联动时间",width = 20,format = "yyyy-MM-dd HH:mm:SS")
	private Date time;
}
