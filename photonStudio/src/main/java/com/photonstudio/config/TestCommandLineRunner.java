package com.photonstudio.config;

import com.photonstudio.mapper.SysmenuMapper;
import com.photonstudio.pojo.FileUploadProperteis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Order(1)
public class TestCommandLineRunner implements CommandLineRunner{
			@Autowired
	private SysmenuMapper sysmenuMapper;
			@Autowired
	private FileUploadProperteis fileUploadProperteis;		

	@Override
	public void run(String... args) throws Exception {
		System.out.println("初始化执行==========");
	sysmenuMapper.createDatabase("zsqy_v2");
		//sysmenuMapper.createTable();
		//appmanagerMapper.createtable();
	File file = new File(fileUploadProperteis.getUploadFolder());
	if(!file.exists()) {
		file.mkdirs();
	}
		
	}
	
	
}
