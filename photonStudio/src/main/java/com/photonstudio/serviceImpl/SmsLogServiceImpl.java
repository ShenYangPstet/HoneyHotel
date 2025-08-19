package com.photonstudio.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.photonstudio.mapper.SmsLogMapper;
import com.photonstudio.service.SmsLogService;

public class SmsLogServiceImpl implements SmsLogService{
	@Autowired
	private SmsLogMapper smsLogMapper;
}
