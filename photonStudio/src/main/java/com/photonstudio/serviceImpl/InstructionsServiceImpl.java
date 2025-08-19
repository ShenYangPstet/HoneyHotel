package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.FileUtil;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.InstructionsMapper;
import com.photonstudio.pojo.Instructions;
import com.photonstudio.service.InstructionsService;
@Service
public class InstructionsServiceImpl implements InstructionsService{
	@Autowired
	private InstructionsMapper instructionsMapper;
	@Override
	public PageObject<Instructions> findObject(String dBname, Integer instructionsTypeid, 
			Integer pageCurrent,Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Instructions>list=new ArrayList<>();
		int rowCount=instructionsMapper.getRowCount(dBname,instructionsTypeid);
		int startIndex=(pageCurrent-1)*pageSize;
		list=instructionsMapper.findObject(dBname,instructionsTypeid,
								startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public List<Instructions> findAllByInstructionsTypeid(String dBname,Integer instructionsTypeid) {
		List<Instructions> list = new ArrayList<>();
		try {
			list=instructionsMapper.findObject(dBname, instructionsTypeid, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败稍后尝试");
		}
		return list;
	}
	@Override
	public int saveObject(String dBname, Instructions instructions,
								MultipartFile file) {
		String url=FileUtil.uploadFile(dBname, file,instructions.getInstructionsName() ,"instructions");
		instructions.setFilepath(url);
		int rows=0;
		try {
			rows=instructionsMapper.insertObject(dBname,instructions);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObjectById(String dBname, Integer id) {
		if(id==null||id<1)throw new IllegalArgumentException("请选择");
		Instructions instructions=instructionsMapper.findObjectById(dBname,id);
		String filepath=instructions.getFilepath();
		boolean fag= FileUtil.deleteObject(dBname, filepath);
		if (fag==false) {
			return 0;
		}
		int rows=0;
		try {
			rows=instructionsMapper.deleteObjectById(dBname,id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除记录失败");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, MultipartFile file,Instructions instructions) {
		int rows=0;
		if(file==null) {
			try {
				rows=instructionsMapper.updateObject(dBname, instructions);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("信息更新失败");
			}
			return rows;
		}
		FileUtil.deleteObject(dBname, instructions.getFilepath());
		String url=FileUtil.uploadFile(dBname, file, instructions.getInstructionsName(),"instructions");
		if(url==null)throw new ServiceException("文件更新失败");
		instructions.setFilepath(url);
		try {
			rows=instructionsMapper.updateObject(dBname, instructions);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("信息更新失败");
		}
		return rows;
		
	}
	
}
