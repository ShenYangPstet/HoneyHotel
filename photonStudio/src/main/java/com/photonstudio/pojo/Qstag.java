package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("qstag")
public class Qstag {
	@TableId(type = IdType.AUTO)
	@Excel(name = "序号")
	private Integer tagid;
	@Excel(name = "寄存器名称")
	@TableField("tagname")
	private String tagname;
	@Excel(name = "寄存器值")
	@TableField("tagvalue")
	private String tagvalue;
	@Excel(name = "转存历史间隔")
	@TableField("savetime")
	private String savetime;
	@Excel(name = "通讯设备地址")
	@TableField("itemdrid")
	private Integer itemdrid;
	@Excel(name = "通讯变量寄存器地址")
	@TableField("itemid")
	private String itemid;
	@Excel(name = "寄存器类型")
	@TableField("itemtype")
	private String itemtype;
	@Excel(name = "设备地址")
	@TableField("itemdradd")
	private String itemdradd;
	@Excel(name = "变量读写类型")
	@TableField("itemreadtype")
	private Integer itemreadtype;
	@TableField(exist = false)
	private String drname;
	@TableField(exist = false)
	private String itemname;
	@Excel(name = "系数")
	@TableField("itemxs")
	private String itemxs;
	@Excel(name = "值类型")
	@TableField("valuetype")
	private Integer valuetype;
}
