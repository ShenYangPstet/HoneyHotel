package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("qs_alarmlog")
@Accessors(chain = true)
public class QsAlarmlog {
	@TableId(type = IdType.AUTO)
	private Integer id;
	@Excel(name = "报警时间", format = "yyyy-MM-dd HH:mm:SS", width = 20)
	private Date time;
	@Excel(name = "设备名", width = 15)
	@TableField(exist = false)
	private String drname;
	private Integer regId;
	@Excel(name = "变量名", width = 24)
	@TableField(exist = false)
	private String title;
	@TableField(exist = false)
	private String content;
	@TableField(exist = false)
	private String regName;
	@Excel(name = "参数值")
	private String alarmvalue;
	@Excel(name = "设备类型名称")
	@TableField(exist = false)
	private String drtypename;
	private String alarmLevel;
	private String alarmstate;
	@Excel(name = "报警级别")
	@TableField(exist = false)
	private String alarmtypename;
	@Excel(name = "报警应答")
	private String alarmanswer;
	@Excel(name = "应答说明", width = 40)
	private String alarmhandle;
	private String username;
	private Date usertime;
	@TableField(exist = false)
	private String alarmexplain;
	@TableField(exist = false)
	private String appexplain;
	@TableField(exist = false)
	private String dBname;
	@TableField(exist = false)
	private Integer drid;
	//说明书地址
	private String instructionsUrl;
}
