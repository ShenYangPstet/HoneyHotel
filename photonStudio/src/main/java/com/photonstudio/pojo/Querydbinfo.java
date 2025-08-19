package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Querydbinfo {
	private Integer id;
	private Integer dbtype;//1mysql//2access//3sqlserver//4本地验证sqlserver
	private Integer conditionId;//条件ID
	private Integer resultId;//结果ID
	private String conUrl;//数据库IP
	private String conUserName;//用户名
	private String conPassword;//密码
	private String dbname;//数据库名
	private String dbsql;//
	private String httpUrl;//服务地址
	private String comments;//查询说明
	private List<String> param;
	private String conditionName;//
	private String resultName;//条件名称
	private String orderfield;//排序字段
	private List<Queryconditioninfo> conditioninfolist;
	private List<Queryresultinfo> resultinfolist;

}
