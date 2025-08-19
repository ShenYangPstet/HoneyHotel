package com.photonstudio.pojo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Regdata {
	@Excel(name = "id")
	private Integer id;
	@Excel(name = "时间",width = 20,format = "yyyy-MM-dd HH:mm:ss")
	private Date time;
	@Excel(name = "寄存器名",width = 20)
	private String tagname;
	@Excel(name = "值")
	private String tagvalue;
	private String savetime;

}
