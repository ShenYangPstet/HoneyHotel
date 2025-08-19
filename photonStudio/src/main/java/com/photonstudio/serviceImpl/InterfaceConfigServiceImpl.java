package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.InterfaceConfigMapper;
import com.photonstudio.pojo.InterfaceConfig;
import com.photonstudio.service.InterfaceConfigService;
@Service
public class InterfaceConfigServiceImpl implements InterfaceConfigService{
	@Autowired
	private InterfaceConfigMapper interfaceConfigMapper;

	@Override
	public PageObject<InterfaceConfig> findObject( String explain,Integer interfaceType,Integer pageCurrent,Integer pageSize) {
		// TODO Auto-generated method stub
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=interfaceConfigMapper.getRowCount(explain,interfaceType);
		int startIndex=(pageCurrent-1)*pageSize;
		List<InterfaceConfig>list=new ArrayList<>();
		System.out.println("startIndex=="+startIndex+"  pageSize=="+pageSize);
		list=interfaceConfigMapper.findObject(explain,interfaceType,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public List<InterfaceConfig> findAll(String explain,Integer interfaceType) {
		// TODO Auto-generated method stub
		
		return interfaceConfigMapper.findObject(explain,interfaceType,null,null);
	}

	@Override
	public int saveObject(InterfaceConfig interfaceConfig) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=interfaceConfigMapper.insertObject(interfaceConfig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public int deleteObject(Integer... ids) {
		// TODO Auto-generated method stub
		int row=0;
		for (Integer integer : ids) {
			System.out.println("id==="+integer);
		}
		try {
			row=interfaceConfigMapper.deleteObject(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return row;
	}

	@Override
	public int updateObject(InterfaceConfig interfaceConfig) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=interfaceConfigMapper.updateObject(interfaceConfig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return row;
	}
}
