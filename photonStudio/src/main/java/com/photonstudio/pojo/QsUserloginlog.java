package com.photonstudio.pojo;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QsUserloginlog {
	private Integer id;
	private Integer userid;
	private Date time;
	private String username;
	private String state;
	private String ip;

}
