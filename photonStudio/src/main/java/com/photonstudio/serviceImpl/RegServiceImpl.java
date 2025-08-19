package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.RegSub;
import com.photonstudio.dataupload.req.RegReq;
import com.photonstudio.dataupload.vo.RegVO;
import com.photonstudio.mapper.QstagMapper;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.mapper.RegalarminfoMapper;
import com.photonstudio.mapper.SubinfoMapper;
import com.photonstudio.pojo.*;
import com.photonstudio.service.RegService;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class RegServiceImpl implements RegService{
	
	@Autowired
	private RegMapper regMapper;
	@Autowired
	private RegalarminfoMapper regalarminfoMapper;
	@Autowired
	private SubinfoMapper subinfoMapper;
	@Autowired
	private QstagMapper qstagMapper;
	@Override
	public PageObject<Reg> findObject(String dBname, Integer drId, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Reg> list=new ArrayList<>();
		int rowCount= regMapper.getRowCount(dBname,drId);
		int startIndex=(pageCurrent-1)*pageSize;
		list=regMapper.findObject(dBname,drId,startIndex,pageSize);
		if(list!=null&&list.size()>0) {
			for (Reg reg : list) {
				List<Regalarminfo> alarmlist=regalarminfoMapper.findObject(dBname, reg.getRegId(), null, null);
				reg.setRegalarminfoslist(alarmlist);
			}
		}
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	
	@Override
	public PageObject<Reg> findRegByRegName(String dBname, String regName,String rw, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Reg> list=new ArrayList<>();
		
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=0;
		rowCount= regMapper.getRowCountByRegName(dBname,regName,rw);
		list=regMapper.findRegByRegName(dBname,regName,rw,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}



	@Override
	public int saveObject(String dBname, Reg reg) {
		// TODO Auto-generated method stub
		int rows=0;
		String tagName=reg.getTagName();
		String tagTime=reg.getTagTime();
		try {
			if(!StringUtils.isEmpty(tagName)&&!StringUtils.isEmpty(tagTime)) {
				regMapper.updateQstagByName(dBname,tagName,tagTime);
			}
			rows=regMapper.insertObject(dBname,reg);
			if(reg.getRegalarminfoslist()!=null&&reg.getRegalarminfoslist().size()>0)
			regalarminfoMapper.insertObjectByList(dBname,reg.getRegalarminfoslist(),reg.getRegId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			regalarminfoMapper.deleteObjectByRegIds(dBname, ids);
			rows=regMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Reg reg) {
		// TODO Auto-generated method stub
		int rows=0;
		String tagName=reg.getTagName();
		String tagTime=reg.getTagTime();
		try {
			if(!StringUtils.isEmpty(tagName)&&!StringUtils.isEmpty(tagTime)) {
				regMapper.updateQstagByName(dBname,tagName,tagTime);
			}
			regalarminfoMapper.deleteObjectByRegIds(dBname, reg.getRegId());
			if(reg.getRegalarminfoslist()!=null&&reg.getRegalarminfoslist().size()>0)
				regalarminfoMapper.insertObjectByList(dBname,reg.getRegalarminfoslist(),reg.getRegId());
			rows=regMapper.updateObject(dBname,reg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Reg> findAllByDrid(String dBname, Integer dr_Id,String isenergy) {
		// TODO Auto-generated method stub
		List<Reg> regList = regMapper.findRegByDrid(dBname, dr_Id, isenergy);
		if(null != regList)
		{
			for(Reg reg : regList)
			{
				String[] regNameSplit= reg.getRegName().split(":", -1);
				if(regNameSplit.length==2){
					reg.setRegName(regNameSplit[1]);
				}else {
					reg.setRegName(regNameSplit[0]);
				}
				if( null != reg.getTagName() && !reg.getTagName().equals(""))
				{
					String tagname = reg.getTagName().trim().replaceAll("\n","").replaceAll("\r","");
					String tagValue = regMapper.queryTagValue(dBname,tagname);
					reg.setTagValue(tagValue);
				}
				if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
				{
					reg.setTagValue(getMathValue(dBname,reg.getRegmath()));
				}
			}
		}
		return regList;
	}
	
	public String getMathValue(String dBname,String regmath)
	{
		String mathValue = "";
		String [] regmathArry = regmath.split("@",-1);
		if(regmathArry.length>0)
		{
			for(int i=0;i<regmathArry.length;i++)
			{
				if(i%2!=0)
				{
					//System.out.println(regmathArry[i]);
					Integer regId = Integer.valueOf(regmathArry[i]);
					String value = findTagValueByRegId(dBname,regId);
					regmath=regmath.replace("@"+regId+"@", value);
				}
			}
		}
		ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
		Object temp = null;
		try {
			temp = jse.eval(regmath);
			mathValue=temp.toString();
			if(mathValue.equals("true"))
			{
				mathValue="1";
			}
			else if(mathValue.equals("false"))
			{
				mathValue="0";
			}
			else
			{
				DecimalFormat decimalFormat=new DecimalFormat("0.00");
				mathValue=decimalFormat.format(temp);
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mathValue;
	}

	@Override
	public void importObjects(String dBname, List<Reg> list) {
		// TODO Auto-generated method stub
		int rows=0;
		Integer reg_id;
		for (Reg reg : list) {
			reg_id=reg.getRegId();
			String tagname=null;
			if(!StringUtils.isEmpty(reg.getTagName()))
			tagname=reg.getTagName().trim().replaceAll("\n","").replaceAll("\r","");
			reg.setTagName(tagname);
			if(reg_id==null||reg_id==0) {
				regMapper.insertObject(dBname, reg);
			}else {
				rows=regMapper.findObjectById(dBname,reg_id);
				if(rows==1) {
					regMapper.updateObject(dBname, reg);
				}else {
					regMapper.insertObject(dBname, reg);
				}
			}
		}
	}

	@Override
	public List<Qstag> findQstag(String dBname) {
		// TODO Auto-generated method stub
		return regMapper.findQstag(dBname);
	}

	@Override
	public List<String> findTagname(String dBname, Integer drid) {
		List<String> name=new ArrayList<>();
		try {
			name = regMapper.findTagnameByDrid(dBname,drid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("获取tagname失败");
		}
		return name;
	}

	@Override
	public List<Reg> findRegByDrid(String dBname, Integer drid, String isenergy) {
		List<Reg> list=new ArrayList<>();
		try {
			list=regMapper.findRegByDrid(dBname, drid, isenergy);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public List<Reg> findRegByListShowLevel(String dBname, Integer drid, String...regListShowLevels) {
		if(drid==null)throw new IllegalArgumentException("请先选择设备");
		if(regListShowLevels==null||regListShowLevels.length==0)
		throw new IllegalArgumentException("请先选择显示等级");
		List<Reg> list=new ArrayList<>();
		try {
			list=regMapper.findRegByListShowLevel(dBname,drid,regListShowLevels);
			for (Reg reg : list) {
				String tagname=reg.getTagName();
				if(!StringUtils.isEmpty(tagname)) {
					String value=regMapper.findQstagByName(dBname,tagname);
					reg.setTagValue(value);
				}
				if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
				{
					reg.setTagValue(getMathValue(dBname,reg.getRegmath()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public List<Reg> findRegById(String dBname, Integer[] ids) {
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请先选择");
		List<Reg>list=new ArrayList<>();
		try {
			list=regMapper.findRegById(dBname, ids);
			/*for (Reg reg : list) {
				String tagname=reg.getTagName();
				if(!StringUtils.isEmpty(tagname)) {
					String value=regMapper.findQstagByName(dBname,tagname);
					reg.setTagValue(value);
				}
				if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
				{
					reg.setTagValue(getMathValue(dBname,reg.getRegmath()));
				}
			}*/
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public int deleteObjectByDrids(String dBname, Integer[] drids) {
		int rows=0;
		try {
			List<Integer>regIdlist=new ArrayList<>();
			regIdlist=regMapper.findRegIdByDrids(dBname,drids);
			regalarminfoMapper.deleteObjectByRegIdlist(dBname, regIdlist);
			rows=regMapper.deleteObjectByDrids(dBname,drids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除变量失败");
		}
		return rows;
	}

	@Override
	public List<Reg> findAllByDrtypeid(String dBname, Integer drtypeid,String tagName) {
		List<Reg>list=new ArrayList<>();
		if(drtypeid!=null) {
			
			list=regMapper.findAllByDrtypeid(dBname,drtypeid);
		}else {
			list= regMapper.findObject(dBname,null,  null, null);
		}
	
		return list;
	}

	@Override
	public List<Qstag> exportQstag(String dBname) {
		// TODO Auto-generated method stub
		return regMapper.findQstag(dBname);
	}

	@Override
	public String findTagValueByRegId(String dBname, Integer regId) {
		// TODO Auto-generated method stub
		Reg reg=regMapper.findRegByid(dBname, regId);
		String tagvalue="";
		try {
			if(reg!=null && StringUtils.isEmpty(reg.getTagName()))
			{
				tagvalue=reg.getTagValue();
			}
			if(reg!=null&&!StringUtils.isEmpty(reg.getTagName())) {
				
				 tagvalue=regMapper.queryTagValue(dBname, reg.getTagName());
				if(StringUtils.isEmpty(tagvalue))
				{
					tagvalue=reg.getTagValue();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return tagvalue;
		}
		 return tagvalue;
	}

	@Override
	public int updateRegValue(String dBname, Integer regId, String tagvaule) {
		// TODO Auto-generated method stub
		int rows = 0;
		try
		{
			rows = regMapper.updateRegValue(dBname, regId, tagvaule);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return rows;
		}
		return rows;
	}

	@Override
	public Integer getCountByShowTypeAndState(String dBname, String showType, String state) {
		// TODO Auto-generated method stub
		int rows = 0;
		try
		{
			rows = regMapper.getCountByShowTypeAndState(dBname, showType, state);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return rows;
		}
		return rows;
	}

	@Override
	public List<Object> selectListForExcelExport(Object queryParams, int page) {
		// TODO Auto-generated method stub
		int startIndex=(page-1)*10000;
		EcharsObject	echarsObject=	(EcharsObject)queryParams;
		List<Object> list=new ArrayList<>();
		try {
			String dBname=echarsObject.getName();
			Integer drtypeid=null;
			if (echarsObject.getValue()!=null&&echarsObject.getValue()=="") drtypeid=Integer.valueOf(echarsObject.getValue());
			list=regMapper.selectListForExcelExport(dBname,drtypeid,startIndex);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getExportRowCount(String dBname, Integer drtypeid, String tagName) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=regMapper.getExportRowCount(dBname,drtypeid );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return rows;
	}

	@Override
	public int getRegCount(String dBname, Integer drid) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=regMapper.getRowCount(dBname, drid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return row;
	}
	@Override
	public List<Reg> findRegBydrid(String dBname, Integer[] ids) {
		List<Reg>list=new ArrayList<>();
		try {
			list=regMapper.findRegBydrid(dBname, ids);
			for(Reg reg : list){
				reg.setRegName(reg.getRegName().split(":",-1)[1]);
				if( null != reg.getTagName() && !reg.getTagName().equals(""))
				{
					String tagname = reg.getTagName().trim().replaceAll("\n","").replaceAll("\r","");
					String tagValue = regMapper.queryTagValue(dBname,tagname);
					reg.setTagValue(tagValue);
				}
				if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
				{
					reg.setTagValue(getMathValue(dBname,reg.getRegmath()));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<EcharsObject> findRegEChars(String dBname, Integer[] ids) {
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请先选择");
		List<Reg>regList=regMapper.findRegById(dBname,ids);
		List<EcharsObject>list=new ArrayList<>();
		for (Reg reg : regList) {
			EcharsObject echarsObject=new EcharsObject();
			echarsObject.setName(reg.getRegName());
			if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
			{
				echarsObject.setValue(getMathValue(dBname,reg.getRegmath()));
			}else if( null != reg.getTagName() && !reg.getTagName().equals(""))
			{
				String tagname = reg.getTagName().trim().replaceAll("\n","").replaceAll("\r","");
				String tagValue = regMapper.queryTagValue(dBname,tagname);
				echarsObject.setValue(tagValue);
			}else {
				echarsObject.setValue(reg.getTagValue());
			}
			list.add(echarsObject);
		}
		return list;
	}

	@Override
	public List<JSONObject> findImageRegSub(String dBname, List<JSONObject> list) {
		List<Subinfo> subinfos = subinfoMapper.findObject(dBname, null, null, null);
		Map<Integer,List<Subinfo>> subMap=new HashMap<>();
		List<Integer>regIds = new ArrayList<>();
		for (Subinfo subinfo : subinfos) {
			if (subMap.get(subinfo.getSubid())==null)subMap.put(subinfo.getSubid(), new ArrayList<>());
			subMap.get(subinfo.getSubid()).add(subinfo);
		}
		for (JSONObject jsonObject : list) {
			regIds.add(jsonObject.getInteger("regId"));
		}
		List<RegSub> regSubs=regMapper.findImageRegSub(dBname,regIds);
		for (JSONObject jsonObject : list) {
			for (RegSub regSub : regSubs) {
				if (jsonObject.getInteger("regId").equals( regSub.getRegId())){
					jsonObject.put("reg",regSub);
					jsonObject.put("subinfo",subMap.get(jsonObject.getInteger("subId")));
				}
			}
			}
		return list;
	}

	@Override
	public List<Reg> findRegByNameDrids(String dBname, String regName, Integer[] drIds) {

		return  regMapper.findRegByNameDrids(dBname, regName, drIds);
	}

	@Override
	public Map<String, String> findTagValueByNames(String dBname, List<String> newList) {
		LambdaQueryWrapper<Qstag> queryWrapper =new LambdaQueryWrapper<>();
		queryWrapper.in(Qstag::getTagname,newList);
		List<Qstag> qstags = qstagMapper.selectList(queryWrapper);
		Map<String,String> map= new HashMap<>();
		for (Qstag qstag : qstags) {
			map.put(qstag.getTagname(),qstag.getTagvalue());
		}
		return map;
	}

	public PageInfo<RegVO> findDeviceRegInfoPage(RegReq regReq) {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		String database = request.getHeader("database");
		PageHelper.startPage(regReq.getPageNum(), regReq.getPageSize());
		List<RegVO> regList = regMapper.findDrids(database,regReq.getDrId(),null,regReq.getDrtypeid());
		List<RegVO> RegVOList = new ArrayList<>();
		Map<Integer,List<Subinfo>> subinfoListMap = subinfoMapper.selectList(null).stream().collect(
				Collectors.groupingBy(Subinfo::getSubid));
		Map<String, String> qsTagMap =
				qstagMapper.selectList(null).stream()
						.collect(Collectors.toMap(Qstag::getTagname, Qstag::getTagvalue));
		if (ObjectUtil.isNotEmpty(regList)) {
			for (RegVO reg : regList) {

				if (ObjectUtil.isNotEmpty(reg.getTagName())) {
					reg.setNewtagvalue(qsTagMap.get(reg.getTagName()));
				}
				if (ObjectUtil.isNotEmpty(reg.getRegSub())) {
					List<Subinfo> subinfoList = subinfoListMap.get(Integer.parseInt(reg.getRegSub()));
					Map<String,String> SubMap = subinfoList.stream().collect(Collectors.toMap(Subinfo::getValue,Subinfo::getText));
					if (ObjectUtil.isNotEmpty(reg.getNewtagvalue())){
						reg.setSubname(SubMap.get(reg.getNewtagvalue()));
					}else {
						reg.setSubname(SubMap.get(reg.getTagValue()));
					}
				}

			}
		}
		return new PageInfo<>(regList);
	}
}
