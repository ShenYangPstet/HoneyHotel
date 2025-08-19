package com.photonstudio.pojo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Qsworkorder {
	@Excel(name = "id")
	private Integer id;
	@Excel(name = "设备类型id")
	private Integer drtypeid;
	@Excel(name = "设备类型名称")
	private String drtypename;
	@Excel(name = "设备id")
	private Integer drid;
	@Excel(name = "设备名称",width = 20)
	private String drname;
	@Excel(name = "下单时间",format = "yyyy-MM-dd HH:mm:SS",width = 20)
	private Date worktime;
	@Excel(name = "下单人")
	private String workuser;
	@Excel(name = "工单级别")
	private Integer worklevel;
	@Excel(name = "接单人")
	private String executeuser;
	@Excel(name = "处理时间",format = "yyyy-MM-dd HH:mm:SS",width = 20)
	private Date executetime;
	@Excel(name = "完成时间",format = "yyyy-MM-dd HH:mm:SS",width = 20)
	private Date finishtime;
	@Excel(name = "工单状态")
	private Integer state;
	@Excel(name = "工单描述",width = 50)
	private String workexplain;
	
	

}
