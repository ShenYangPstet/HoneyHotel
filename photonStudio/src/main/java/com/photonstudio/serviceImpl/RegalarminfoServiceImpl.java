package com.photonstudio.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.RegalarminfoMapper;
import com.photonstudio.pojo.Regalarminfo;
import com.photonstudio.service.RegalarminfoService;
@Service
public class RegalarminfoServiceImpl implements RegalarminfoService{
	@Autowired
	private RegalarminfoMapper regalarminfoMapper;

	@Override
	public PageObject<Regalarminfo> findObject(String dBname, Integer regId, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=regalarminfoMapper.getRowCount(dBname,regId);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Regalarminfo>list=regalarminfoMapper.findObject(dBname,regId,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public List<Regalarminfo> findAllByRegId(String dBname, Integer regId) {
		// TODO Auto-generated method stub
		
		return regalarminfoMapper.findObject(dBname,regId,null,null);
	}

	@Override
	public int saveObject(String dBname, Regalarminfo regalarminfo) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=regalarminfoMapper.insertObject(dBname,regalarminfo);
		} catch (Exception e) {	
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public int deleteObjectByIds(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=regalarminfoMapper.deleteObjectByIds(dBname,ids);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectByRegId(String dBname, Integer regId) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=regalarminfoMapper.deleteObjectByRegIds(dBname,regId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Regalarminfo regalarminfo) {
		// TODO Auto-generated method stub
		int row=0;
		try {
		row = regalarminfoMapper.updateObject(dBname,regalarminfo);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		return row;
	}

	@Override
	public void importObjects(String dBname, List<Regalarminfo> list) {
		// TODO Auto-generated method stub
		int rows=0;
		Integer id;
		for (Regalarminfo regalarminfo : list) {
			id=regalarminfo.getId();
			if(id==null||id==0) {
				regalarminfoMapper.insertObject(dBname, regalarminfo);
			}else {
				rows=regalarminfoMapper.findCountById(dBname,id);
				if(rows==1) {
					regalarminfoMapper.updateObject(dBname, regalarminfo);
				}else {
					regalarminfoMapper.insertObject(dBname, regalarminfo);
				}
			}
		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> selectListForExcelExport(Object queryParams, int page) {
		// TODO Auto-generated method stub
		Map<String, String> params=new HashMap<>();
		params=(HashMap<String, String>)queryParams;
		String dBname=params.get("dBname");
		int startIndex=(page-1)*10000;
		List list=regalarminfoMapper.findObject(dBname,null,startIndex,10000);
		return list;
	}

	@Override
	public int getRowCount(String dBname, Integer regId) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=regalarminfoMapper.getRowCount(dBname, regId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rows;
	}
	

}
