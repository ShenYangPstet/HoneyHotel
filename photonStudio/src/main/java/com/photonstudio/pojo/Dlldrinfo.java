package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Dlldrinfo {
	@Excel(name = "序号")
	private Integer drid;
	@Excel(name = "寄存器设备名字")
	private String drname;
	@Excel(name = "通讯变量寄存器地址")
	private Integer drllid;
	@Excel(name = "通讯设备驱动类型")
	private Integer type;
	@Excel(name = "通讯设备ip端口")
	private String ipport;
	@Excel(name = "通讯设备com口")
	private String com;
	@Excel(name = "波特率")
	private String baudRate;
	@Excel(name = "数据位")
	private int dataBits;
	@Excel(name = "停止位")
	private String stopBits;
	@Excel(name = "校验位")
	private String parity;
	@Excel(name = "采集周期")
	private int cycle;
	@Excel(name = "设备名称")
	private String drllname;
	@Excel(name = "MacID")
	private int macID;
}
