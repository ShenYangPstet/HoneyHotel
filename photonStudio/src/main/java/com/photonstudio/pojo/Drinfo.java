package com.photonstudio.pojo;

import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//当查到的字段为null时不返回给前台
@TableName("drinfo")
@Accessors(chain = true)
public class Drinfo {
	@TableId(type = IdType.AUTO)
	@Excel(name = "id")
	private Integer drid;
	@Excel(name ="设备名",width = 20,isImportField = "true")
	private String drname;
	@Excel(name = "设备类型")
	private Integer drtypeid;
	@Excel(name = "所属的建筑id")
	private Integer buildid;
	@Excel(name = "视频链接的id")
	private String spid;
	@Excel(name = "设备型号")
	@TableField("dr_ManufactureStyle")
	private String drManufactureStyle;
	@Excel(name="设备生产厂家")
	@TableField("dr_ManufactureFactory")
	private String drManufactureFactory;
	@Excel(name="生产厂家电话")
	@TableField("dr_Factoryphone")
	private String drFactoryphone;
	@Excel(name = "安装时间",format = "yyyy-MM-dd",width = 20)
	@TableField("dr_InstallTime")
	private Date drInstallTime;
	@Excel(name="安装厂家")
	@TableField("dr_InstallFactory")
	private String drInstallFactory;
	@Excel(name="安装厂家电话",width = 20)
	@TableField("dr_InstallPhone")
	private String drInstallPhone;
	@Excel(name = "资产类别")
	private Integer assetsTypeid;
	@Excel(name = "使用状态")
	@TableField("dr_UseState")
	private Integer drUseState;
	@Excel(name = "仓库id")
	private Integer storeroom;
	@Excel(name = "使用说明")
	@TableField("dr_UseExplain")
	private String drUseExplain;
	@Excel(name = "操作手册id")
	private Integer instructionsid;
	@Excel(name = "设备编号")
	private String mdcode;
	@Excel(name="部门id")
	private Integer departmentid;
	@TableField("typeYT")
	@Excel(name="类型用途")
	private String typeYT;
	@TableField(exist = false)
	private Integer userid;
	@TableField(exist = false)
	private String buildname;
	@TableField(exist = false)
	private String drtypename;
	@TableField(exist = false)
	private String assetstypename;
	@TableField(exist = false)
	private String roomname;
	@TableField(exist = false)
	private String iconname;
	@TableField(exist = false)
	private String icontype;
	@TableField(exist = false)
	private Integer state;
	@TableField(exist = false)
	private List<Reg> reglist;
	@TableField(exist = false)
	private List<Drtypemode> drtypemodeList;


	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@TableField(exist = false)
	@NotNull(message = "页数不能为空")
	private Integer pageSum;


	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@TableField(exist = false)
	@NotNull(message = "条数不能为空")
	private Integer pageSize;

}
