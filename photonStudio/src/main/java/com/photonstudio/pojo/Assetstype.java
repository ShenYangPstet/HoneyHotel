package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Assetstype {
	@Excel(name = "id")
	private Integer id;
	@Excel(name="类型名称",width = 20)
	private String assetstypename;
	@Excel(name = "类型描述",width = 50)
	private String explain;
}
