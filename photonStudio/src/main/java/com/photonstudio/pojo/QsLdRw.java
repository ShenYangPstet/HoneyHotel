package com.photonstudio.pojo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class QsLdRw {
	@Excel(name = "任务id")
	private Integer ldRwId;
	@Excel(name = "任务名字")
	private String ldRwName;
	@Excel(name = "联动任务描述",width = 15)
	private String ldRwMiaoshu;
	@Excel(name = "任务类型")
	private Integer rwType;
	@Excel(name = "设备类型Id")
	private Integer drTypeId;
	@Excel(name = "任务开始时间",width = 15)
	private Date starttime;
	@Excel(name = "任务结束时间",width = 15)
	private Date endtime;
	private Integer issuspend;//是否开启任务 0开启 1停止
	private Date suspendtime;
	private String state;//任务运行状态
}
