package com.photonstudio.mapper;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.SmsLog;

public interface SmsLogMapper {

	void insertObject(@Param("dBname")String dBname, SmsLog smsLog);
	
}
