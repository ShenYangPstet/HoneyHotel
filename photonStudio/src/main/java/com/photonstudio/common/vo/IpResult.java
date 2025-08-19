package com.photonstudio.common.vo;

import com.photonstudio.pojo.Iplog;

import lombok.Data;

@Data
public class IpResult {
	private Integer code;
	private Iplog data;
}
