package com.photonstudio.pojo;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//当查到的字段为null时不返回给前台
public class Reg {

	@Excel(name = "变量id")
	private Integer regId;
	@Excel(name = "变量名称")
	@TableField("reg_Name")
	private String regName;
	@Excel(name = "设备id")
	@TableField("dr_Id")
	private Integer drId;
	@Excel(name = "变量类型")
	@TableField("reg_Type")
	private String regType;
	@Excel(name = "单位")
	@TableField("reg_Units")
	private String regUnits;
	@Excel(name = "读写属性")
	@TableField("reg_ReadWrite")
	private String regReadWrite;
	@Excel(name = "列表顺序")
	@TableField("reg_ListShowLevel")
	private String regListShowLevel;
	@Excel(name = "寄存器阈值")
	@TableField("reg_sub")
	private String regSub;
	@Excel(name = "变量显示类型")
	@TableField("reg_DrShowType")
	private String regDrShowType;
	@Excel(name = "设备显示阈值")
	@TableField("reg_Drsub")
	private String regDrsub;
	@Excel(name = "寄存器名")
	@TableField("tag_name")
	private String tagName;
	@Excel(name = "寄存器值")
	@TableField("tag_value")
	private String tagValue;
	@Excel(name = "显示历史")
	@TableField("Ishistory")
	private String ishistory;
	@Excel(name = "显示能耗")
	@TableField("Isenergy")
	private String isenergy;
	@Excel(name = "是否报警")
	@TableField("Isalarm")
	private String isalarm;
	@Excel(name = "报警类型")
	@TableField("alarmtype")
	private String alarmtype;
	@Excel(name = "条件低")
	@TableField("value_min")
	private String valueMin;
	@Excel(name = "条件高")
	@TableField("value_max")
	private String valueMax;
	@Excel(name = "条件与或")
	@TableField("and_or")
	private String andOr;
	@Excel(name = "报警级别")
	@TableField("alarm_level")
	private String alarmLevel;
	@Excel(name = "报警状态")
	@TableField("tag_alarm_state")
	private String tagAlarmState;
	@Excel(name = "次数")
	@TableField("tag_time")
	private String tagTime;
	@Excel(name = "算法")
	@TableField("regmath")
	private String regmath;



	@TableField(exist = false)
	private String subname;
	@TableField(exist = false)
	private String text;
	@TableField(exist = false)
	private String qstagvalue;//qstag值
	private String regcolor;
	@TableField(exist = false)
	private List<Regalarminfo> regalarminfoslist;
	@TableField(exist = false)
	private String drname;
	@TableField(exist = false)
	private String alarmtypelevel;
	@TableField(exist = false)
	private String drtypename;
	@TableField(exist = false)
	private String newtagvalue;//qstag值
	private Integer islistshow;//变量显示等级1低2中3高
	@TableField(exist = false)
	private List<Subinfo> subinfoList;//变量对应阈值
}
