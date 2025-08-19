package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Subinfo {
	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer subid;
	private String valueType;	//1数值型  2条件型
	private String value;
	private String valueMin;
	private String valueMax;
	private String andOr; //1条件与 2条件或
	private String text;
	private String url;
	@TableField(exist = false)
	private String subname;
	@TableField(exist = false)
	private String imgurl;
}
