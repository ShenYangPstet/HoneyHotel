package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.DepartmentdrMapper;
import com.photonstudio.pojo.Departmentdr;
import com.photonstudio.service.DepartmentdrService;
@Service
public class DepartmentdrServiceImpl implements DepartmentdrService{
	@Autowired
	private DepartmentdrMapper departmentdrMapper;
	@Override
	public List<Departmentdr> findObject(String dBname, Integer departmentid) {
		List<Departmentdr>list=new ArrayList<>();
		try {
			list=departmentdrMapper.findObject(dBname,departmentid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
		
	}

	@Override
	public int saveObject(String dBname, Integer departmentid, Integer... drids) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=departmentdrMapper.insertObject(dBname,departmentid,drids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer... ids) {
		int rows=0;
		try {
			rows=departmentdrMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

}
