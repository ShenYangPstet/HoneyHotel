package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.DepartmentpicMapper;
import com.photonstudio.pojo.Departmentpic;
import com.photonstudio.pojo.Pic;
import com.photonstudio.service.DepartmentpicService;
@Service
public class DepartmentpicServiceImpl implements DepartmentpicService{
	@Autowired
	private DepartmentpicMapper departmentpicMapper;

	@Override
	public List<Departmentpic> findObkect(String dBname, Integer departmentid) {
		
		List<Departmentpic>list=new ArrayList<>();
		try {
			list=departmentpicMapper.findObject(dBname, departmentid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		
		return list;
	}

	@Override
	public int saveObject(String dBname, Integer departmentid, Integer... picids) {
		int rows=0;
		try {
			rows=departmentpicMapper.insertObject(dBname, departmentid,null, picids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer... ids) {
		int rows=0;
		try {
			rows=departmentpicMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public List<Pic> findPicByDepartmentid(String dBname, Integer departmentid) {
		List<Pic> picList=departmentpicMapper.findPicByDepartmentid(dBname, departmentid);
		System.out.println("picList==>"+picList);
		List<Pic> picListResutlt = new ArrayList<>();
		for(Pic pic : picList)
		{
			if(pic.getParentid() == 0)
			{
				picListResutlt.add(pic);
			}
		}
		for(Pic pic : picListResutlt)
		{
			pic.setChildren(getChild(pic.getPicid(), picList));
		}
		return picListResutlt;
	}
	
	private List<Pic> getChild(Integer picid, List<Pic> picList) {
		// TODO Auto-generated method stub
		List<Pic> childList = new ArrayList<>();
		for(Pic pic : picList)
		{
			if (0 != pic.getParentid()) {
	            if (pic.getParentid().equals(picid)) {
	                childList.add(pic);
	            }
	        }
		}
		for(Pic pic : childList)
		{
			pic.setChildren(getChild(pic.getPicid(), picList));
		}
		return childList;
	}
	}

