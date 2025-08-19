package com.photonstudio.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Instructions;

public interface InstructionsService {

	PageObject<Instructions> findObject(String dBname, Integer instructionsTypeid, Integer pageCurrent,
			Integer pageSize);

	List<Instructions> findAllByInstructionsTypeid(String dBname, Integer instructionsTypeid);

	int saveObject(String dBname, Instructions instructions, MultipartFile file);

	int deleteObjectById(String dBname, Integer id);

	int updateObject(String dBname, MultipartFile file, Instructions instructions);
	
}
