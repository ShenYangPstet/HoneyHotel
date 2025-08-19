package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("alarmtype")
public class Alarmtype {
	@TableId(type = IdType.AUTO)
	private Integer id;
	@TableField("alarmtypename")
	private String alarmtypename;
	@TableField("alarmtypelevel")
	private String alarmtypelevel;
	@TableField("alarmtypeExpain")
	private String alarmtypeExpain;

}
