package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.DepartmentMapper;
import com.photonstudio.pojo.Department;
import com.photonstudio.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> findObjectByFuid(String dBname, Integer fuid) {
	List<Department>list=new ArrayList<>();
	try {
		list=departmentMapper.findObject(dBname,fuid,null,null);
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServiceException("查询失败");
	}
		return list;
	}

	@Override
	public List<Department> findAll(String dBname) {
		List<Department> departmentsList = departmentMapper.findObject(dBname, null,null,null);
		List<Department> departmentsListResult = new ArrayList<>();
		for(Department department : departmentsList)
		{
			if(department.getFuid() == 0)
			{
				departmentsListResult.add(department);
			}
		}
		for(Department department : departmentsListResult)
		{
			department.setChildren(getChild(department.getDepartmentid(),departmentsList));
		}
		
		return departmentsListResult;
		
	}

	private List<Department> getChild(Integer departmentid, List<Department> departmentsList) {
		List<Department> childList = new ArrayList<>();
		for(Department department : departmentsList)
		{
			
			if(0!= department.getFuid())
			{
				if(department.getFuid() == departmentid)
				{
					childList.add(department);
				}
			}
		}
		for(Department department : childList)
		{
			department.setChildren(getChild(department.getDepartmentid(),departmentsList));
		}
		return childList;
		
	}

	@Override
	public int insertObject(String dBname, Department department) {
		int rows=0;
		try {
			rows=departmentMapper.insertObject(dBname, department);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer departmentid) {
		//1.对参数进行校验
		if(departmentid==null)
		throw new IllegalArgumentException("请先选择");
		//删除子菜单
		try {
			findChildren(dBname,departmentid);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServiceException("子目录删除失败");
		}
		int rows=0;
		try{//通过dao访问数据库服务器可能会有异常，此异常也可不再此位置处理
		 rows=departmentMapper.deleteObjectById(dBname, departmentid);
		}catch(Throwable e){
		 e.printStackTrace();
		 
		 throw new ServiceException("删除失败");
		}
		return rows;
		
	}

	private void findChildren(String dBname, Integer departmentid) {
		List<Integer>list =new ArrayList<>();
		list=departmentMapper.findChildrenId(dBname,departmentid);
		  if(list==null||list.size()<1) {
		  return;
		 }
		for (Integer ChildrenId : list) {
			findChildren(dBname,ChildrenId);
			departmentMapper.deleteObjectById(dBname, ChildrenId);
		}
	}

	@Override
	public int updateObject(String dBname, Department department) {
		if(department.getFuid()==department.getDepartmentid())
			throw new IllegalArgumentException("不能隶属于自己");
		int rows=0;
		try {
			rows=departmentMapper.updateObject(dBname, department);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public PageObject<Department> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Department>list=new ArrayList<>();
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=departmentMapper.getRowCount(dBname);
		list=departmentMapper.findObject(dBname,null,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
}
