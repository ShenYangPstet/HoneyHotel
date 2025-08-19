package com.photonstudio.pojo;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Pic {
	@Excel(name = "页面id")
	private Integer picid;
	@Excel(name = "页面名称")
	private String picname;
	@Excel(name = "父级ID")
	private Integer parentid;
	private Integer buildid;
	@Excel(name = "页面模板id")
	private Integer picmodeid;
	@Excel(name = "图片ID")
	private Integer imgId;
	@Excel(name = "页面类型")
	private Integer pictype;
	private Integer departmentid;
	private String buildname;
	private String imgname;
	private String imgurl;
	@Excel(name = "层级")
	private Integer prioritylv;
	private String color;
	private String rootPath;
	@Excel(name = "上下关系")
	private String hyponymy;//上下关系
	private List<Pic> children;
}
