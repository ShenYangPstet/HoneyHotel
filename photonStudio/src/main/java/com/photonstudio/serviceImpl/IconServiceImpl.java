package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.mapper.IconMapper;
import com.photonstudio.pojo.Icon;
import com.photonstudio.service.IconService;
@Service
public class IconServiceImpl implements IconService{
	@Autowired
	private IconMapper iconMapper;
	@Override
	public List<Icon> findObject() {
		List<Icon>list =new ArrayList<>();
		list=iconMapper.findObject();
		return list;
	}

}
