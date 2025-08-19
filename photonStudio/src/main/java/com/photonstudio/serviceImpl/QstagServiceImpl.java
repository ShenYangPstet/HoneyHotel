package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QstagMapper;
import com.photonstudio.mapper.RegdataMapper;
import com.photonstudio.pojo.Qstag;
import com.photonstudio.service.QstagService;
@Service
public class QstagServiceImpl implements QstagService{
	@Autowired
	private QstagMapper qstagMapper;
	@Autowired
	private RegdataMapper regdataMapper;
	@Override
	public PageObject<Qstag> findObject(String dBname, String tagname, Integer itemdrid, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Qstag>list=new ArrayList<>();
		int rowCount=qstagMapper.getRowCount(dBname, tagname, itemdrid);
		int startIndex=(pageCurrent-1)*pageSize;
		list=qstagMapper.findObject(dBname, tagname, itemdrid, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Transactional
	@Override
	public int saveObject(String dBname, Qstag qstag) {
		// TODO Auto-generated method stub
		int row=0;
		int tagCount=qstagMapper.selectCountByTagname(dBname,qstag.getTagname());
		if(tagCount>0) throw new IllegalArgumentException("寄存器名已经存在");
		try {
				row=qstagMapper.insertObject(dBname, qstag);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public int deleteObjectById(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=qstagMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Transactional
	@Override
	public int updateObject(String dBname, Qstag qstag) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			int rowCount=regdataMapper.getDataBase(dBname+"_data");
			String tagnameold=qstagMapper.findTagnameById(dBname, qstag.getTagid());
			if(!tagnameold.equals(qstag.getTagname())&&rowCount>0) {
				regdataMapper.updatetable(dBname+"_data", tagnameold, qstag.getTagname());
			}
			row=qstagMapper.updateObject(dBname, qstag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return row;
	}

	@Override
	public List<Qstag> findQstag(String dBname, String tagname, Integer itemdrid) {
		// TODO Auto-generated method stub
		List<Qstag>list=qstagMapper.findObject(dBname, tagname, itemdrid, null, null);
		return list;
	}
	@Transactional
	@Override
	public void importObjects(String dBname, List<Qstag> list) {
		// TODO Auto-generated method stub
		int rows=0;
		Integer tagid;
		for (Qstag tag : list) {
			String tagname = tag.getTagname().trim().replaceAll("\n","").replaceAll("\r","");
			tag.setTagname(tagname);
			int tagCount=qstagMapper.selectCountByTagname(dBname,tag.getTagname());
			if (tagCount==1) 
			System.out.println("重复寄存器==="+tag.getTagname());
			tagid=tag.getTagid();
			if(tagid==null||tagid==0) {
				if(tagCount==0)qstagMapper.insertObject(dBname, tag);
			}else {
				rows=qstagMapper.findObjectById(dBname,tagid);
				if(rows==1) {
					qstagMapper.updateObject(dBname, tag);
				}else {
					if(tagCount==0)qstagMapper.insertObject(dBname, tag);
				}
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectListForExcelExport(Object queryParams, int page) {
		// TODO Auto-generated method stub
		int startIndex=(page-1)*10000;
		Map<String, String>mapparams=new HashMap<>();
		List<Object>list=new ArrayList<>();
		try {
			mapparams=(HashMap<String, String>)queryParams;
			String dBname=mapparams.get("dBname");
			String tagname=mapparams.get("tagname");
			Integer itemdrid= null;
			if(mapparams.get("itemdrid")!=null&&mapparams.get("itemdrid")!="")
				itemdrid=Integer.valueOf(mapparams.get("itemdrid"));
			list=qstagMapper.selectListForExcelExport(dBname, tagname, itemdrid, startIndex);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int findExportCount(String dBname, String tagname, Integer itemdrid) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=qstagMapper.getRowCount(dBname, tagname, itemdrid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rows;
	}
	@Override
	public int updateByTagName(String dBname,  String msg) {
		// TODO Auto-generated method stub
		List<Qstag>list=new ArrayList<>();
		String[] split = msg.split(",");
		for (String string : split) {
			String[] split2 = string.split(":");
			String tagname = split2[0];
			String value = split2[1];
			if (!StringUtils.isEmpty(tagname) && !StringUtils.isEmpty(value)) {
				Qstag qstag=new Qstag();
				qstag.setTagname(tagname);
				qstag.setTagvalue(value);
				list.add(qstag);
			}
		 
		}
		int rows=0;
		try {
			rows = qstagMapper.updateObjectByTagname(dBname, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public List<Qstag> findTagValueByTagName(String[] tagname) {
		QueryWrapper<Qstag>queryWrapper=new QueryWrapper<>();
		queryWrapper.in("tagname",tagname);
		return qstagMapper.selectList(queryWrapper);
	}

	@Override
	public int fidnudge(String tagname) {
		return qstagMapper.selectList(new LambdaQueryWrapper<Qstag>().eq(Qstag::getTagname,tagname)).size();
	}
}
