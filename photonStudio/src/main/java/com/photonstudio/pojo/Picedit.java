package com.photonstudio.pojo;

import java.util.ArrayList;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Picedit {
	@Excel(name = "主键Id")
	private Integer id;
	@Excel(name = "页面Id")
	private Integer picid;
	@Excel(name = "设备Id")
	private Integer drid;
	@Excel(name = "设备icon显示Id")
	private String drshowtype;
	@Excel(name = "设备显示图标")
	private String showicon;
	@Excel(name = "显示设备名称")
	private String isshowname;
	@Excel(name = "元素类型Id")
	private Integer elemttypeid;
	@Excel(name = "X坐标")
	private String picx;
	@Excel(name = "Y坐标")
	private String picy;
	@Excel(name = "类型Id(1-设备,2-元素)")
	private String type;
	@Excel(name = "层级")
	private String zindex;
	@Excel(name = "高度")
	private Integer pich;
	@Excel(name = "宽度")
	private Integer picw;
	@Excel(name = "角度")
	private Integer angle;
	@Excel(name = "经度")
	private String lng;
	@Excel(name = "纬度")
	private String lat;
	
	private String icontype;
	private Integer state;
	private String drname;
	private String spid;
	private String mdcode;
	private Integer drtypeid;
	private String drtypename;
	
	private List<Elements> elements = new ArrayList<Elements>();
	private Drinfo drinfo;
	

}
