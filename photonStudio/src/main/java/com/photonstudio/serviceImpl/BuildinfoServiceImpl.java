package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.BuildinfoMapper;
import com.photonstudio.pojo.Buildinfo;
import com.photonstudio.service.BuildinfoService;
@Service
public class BuildinfoServiceImpl implements BuildinfoService{
	@Autowired
	private BuildinfoMapper buildinfoMapper;
	@Override
	public PageObject<Buildinfo> findObjectByName(String buildname, String appname,
		Integer	pageCurrent,Integer pageSize) {
		List<Buildinfo> list =new ArrayList<>();
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		        //基于条件查询总记录数并进行验证
				int rowCount=buildinfoMapper.getRowCount(appname,buildname);
				
				//依据条件查询当前页要显示的记录
				int startIndex=(pageCurrent-1)*pageSize;		
		list=buildinfoMapper.findObjectByName(buildname,appname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
		/*
		 * PageObject<Buildinfo> pageObject=new PageObject<>(); //对查询结果进行封装并返回。
		 * pageObject.setRowCount(rowCount); pageObject.setRecords(list);
		 * pageObject.setPageSize(pageSize); pageObject.setPageCurrent(pageCurrent);
		 * pageObject.setPageCount((rowCount-1)/pageSize+1); return pageObject;
		 */
		
		
	}
	@Override
	public int saveObject(String dBname, Buildinfo buildinfo) {
		int rows=0;
		try {
			rows=buildinfoMapper.insertObject(dBname,buildinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
		
		
	}
	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请先选择");
			//执行删除操作
			int rows=0;
			try{
			 rows=buildinfoMapper.deleteObjectById(dBname,ids);
			}catch(Throwable e){
			 e.printStackTrace();
			 
			 throw new ServiceException(e);
			}
			//验证删除结果
			if(rows==0)
			throw new ServiceException("记录可能已经不存在");
			//返回结果
			return rows;
	}
	@Override
	public int updateObject(String dBname, Buildinfo buildinfo) {
		int rows=0;
		try {
			rows=buildinfoMapper.updateObject(dBname,buildinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<Buildinfo> findAll(String dBname) {
		
		return buildinfoMapper.findAll(dBname);
	}
	@Override
	public void importObjects(String dBname, List<Buildinfo> list) {
		// TODO Auto-generated method stub
		Integer buildid;
		int rows=0;
		for(Buildinfo buildinfo : list)
		{
			buildid = buildinfo.getBuildid();
			if(buildid == null || buildid == 0)
			{
				buildinfoMapper.insertObject(dBname,buildinfo);
			}
			else
			{
				rows=buildinfoMapper.findObjectByBuildid(dBname,buildid);
				if(rows<1)
				{
					buildinfoMapper.insertObject(dBname,buildinfo);
				}
				else 
				{
					buildinfoMapper.updateObject(dBname,buildinfo);
				}
			}
			
		}
	}
	

}
