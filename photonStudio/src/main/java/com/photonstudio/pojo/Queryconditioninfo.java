package com.photonstudio.pojo;

import lombok.Data;

@Data
public class Queryconditioninfo {
	private Integer id;
	private String filedName;
	private String filedDbName;
	private String fiedtype;
	private String fiedSql;
	private Integer conditionId;
	private String conditionName;
}
