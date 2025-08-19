package com.photonstudio.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Department {
	private Integer departmentid;
	private String departmentname;
	private Integer fuid;
	private String departmentExplain;
	private String fudepartmentname;
	private List<Department> children;
}
