package com.photonstudio.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.common.SessionUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.DrtypeinfoMapper;
import com.photonstudio.mapper.PiceditMapper;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Elements;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.pojo.FileUploadProperteis;
import com.photonstudio.pojo.Picedit;
import com.photonstudio.pojo.Reg;
import com.photonstudio.service.PiceditService;

@Service
public class PiceditServiceImpl implements PiceditService{
	
	@Autowired
	private PiceditMapper piceditMapper;
	@Autowired
	private DrtypeinfoMapper drtypeinfoMapper;
	@Autowired
	private FileUploadProperteis fileUploadProperteis;
	@Autowired
	private RegMapper regMapper;

	@Override
	public List<Picedit> findPiceditByPicid(String dBname, Integer picid,Integer drtypeid) {
		// TODO Auto-generated method stub
		Appuser appuser = SessionUtil.getAppuser(dBname);
		List<Integer>dridlist=new ArrayList<>();
		if(appuser!=null&&appuser.getUsertype()==1&&appuser.getDridlist()!=null&&!appuser.getDridlist().isEmpty()) {
			dridlist=appuser.getDridlist();
		}
		String staticAccessPath =fileUploadProperteis.getStaticAccessPath();
		List<Picedit> piceidtList=null;
		if(drtypeid == null)
		{
			piceidtList = piceditMapper.findPiceditByPicid(dBname,picid,staticAccessPath,dridlist);
		}
		else
		{
			String drtypeidtmps="";
			String drtypeids=getDrtypeids(dBname,drtypeid,drtypeidtmps);
			drtypeids=drtypeids+")";
			//System.out.println("drtypeids==="+drtypeids);
			piceidtList = piceditMapper.findPiceditByPicidAnddrtypeid(dBname,picid,staticAccessPath,drtypeids);
		}
		return piceidtList;
	}

	private String getDrtypeids(String dBname,Integer drtypeid,String drtypeidtmps) {
		// TODO Auto-generated method stub
		List<Integer>list =new ArrayList<>();
		list=drtypeinfoMapper.findChildrenId(dBname,drtypeid);
		if(list.size()<1)
		{
			if(drtypeidtmps.equals(""))
			{
				drtypeidtmps="("+drtypeid;
			}
			else
			{
				drtypeidtmps=drtypeidtmps+","+drtypeid;
			}
		}
		for(Integer id : list)
		{
			drtypeidtmps=getDrtypeids(dBname,id,drtypeidtmps);
		}
		return drtypeidtmps;
	}

	@Override
	public int saveObject(String dBname, Picedit picedit) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = piceditMapper.insertObject(dBname, picedit);
			Integer elemtpiceditid = piceditMapper.getElemtpiceditid(dBname);
			//System.out.println("id=="+elemtpiceditid);
			if("2".equals(picedit.getType()))//插入Elements表
			{
				Integer elemttypeid = picedit.getElemttypeid();
				System.out.println("dBname=="+dBname+"|elemttypeid=="+elemttypeid);
				List<Elemttypemode> elemttypemodeList = piceditMapper.findElemttypemodeByElemttypeid(dBname,elemttypeid);
				for(Elemttypemode elemttypemode : elemttypemodeList)
				{
					Elements elements = new Elements();
					elements.setElemtpiceditid(elemtpiceditid);
					elements.setElemttypeid(elemttypeid);
					elements.setElemtELtype(String.valueOf(elemttypemode.getElemtELtype()));
					elements.setElemtlevel(String.valueOf(elemttypemode.getElemtlevel()));
					elements.setElemtstyle(elemttypemode.getElemtstyle());
					elements.setText(elemttypemode.getText());
					elements.setImgid(elemttypemode.getImgid());
					elements.setElemtmodename(elemttypemode.getElemtmodename());
					elements.setColour("#f74e36,#f78836");
					rows = piceditMapper.insertElements(dBname, elements);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows = 0;
		int count = 0;
		for(Integer id : ids)
		{
			try {
				rows = piceditMapper.deletePicEdit(dBname, id);
				System.out.println("rows=="+rows);
				count = piceditMapper.selectElements(dBname, id);
				if(count>0)
				{
					rows = piceditMapper.deleteElements(dBname, id);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("失败");
			}
		}
		return rows;
	}

	@Override
	public int updatePicEdit(String dBname, String piceditupd) {
		// TODO Auto-generated method stub
		String[] piceditupdArray = piceditupd.split(",");
		int rows = 0;
		for(String piceditStr : piceditupdArray)
		{
			String[] piceditStrArray = piceditStr.split("_");
			Picedit picedit = new Picedit();
			picedit.setId(Integer.valueOf(piceditStrArray[0]));
			picedit.setPicx(piceditStrArray[1]);
			picedit.setPicy(piceditStrArray[2]);
			picedit.setZindex(piceditStrArray[3]);
			picedit.setPich(Integer.valueOf(piceditStrArray[4]));
			picedit.setPicw(Integer.valueOf(piceditStrArray[5]));
			picedit.setAngle(Integer.valueOf(piceditStrArray[6]));
			try {
				rows = piceditMapper.updatePicEdit(dBname, picedit);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("失败");
			}
			
		}
		return rows;
	}

	@Override
	public List<Elements> findElementsListByElepicid(String dBname, Integer elemtpiceditid) {
		// TODO Auto-generated method stub
		List<Elements> elementsList = piceditMapper.findElementsListByElepicid(dBname,elemtpiceditid);
		return elementsList;
	}

	@Override
	public int updateElements(String dBname, Elements elements) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = piceditMapper.updateElements(dBname, elements);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}

	@Override
	public Picedit findPiceditById(String dBname, Integer id) {
		// TODO Auto-generated method stub
		Picedit piceidtt = piceditMapper.findPiceditById(dBname,id);
		return piceidtt;
	}

	@Override
	public int savePicEditDr(String dBname, List<Picedit> piceditList) {
		int rows = 0;
		for(Picedit picedit : piceditList)
		{
			Integer iconId =  piceditMapper.getDrtypeiconId(dBname,picedit.getDrid());
			if(null != iconId)
			{
				picedit.setDrshowtype(String.valueOf(iconId));
			}
			rows = piceditMapper.insertObject(dBname, picedit);
		}
		// TODO Auto-generated method stub
		return rows;
	}

	@Override
	public Drinfo findDrinfoBydrid(String dBname, Integer drid) {
		// TODO Auto-generated method stub
		String staticAccessPath =fileUploadProperteis.getStaticAccessPath();
		Drinfo drinfo = piceditMapper.findDrinfoBydrid(dBname,drid,staticAccessPath);
//		List<Reg> regList = piceditMapper.findrunstateBydrid(dBname,drid);
//		int runStateCnt=0;
//		int alarmStateCnt = 0;
//		int errorStateCnt = 0;
//		for(Reg reg : regList)
//		{
//			if(reg.getRegDrShowType().equals("1"))
//			{
//				if(null != reg.getTagName() && !reg.getTagName().equals(""))
//				{
//					String value = piceditMapper.querytagvalue(dBname,reg.getTagName());
//					if(value.equals("1"))
//					{
//						runStateCnt=1;
//					}
//				}
//				else
//				{
//					if(reg.getTagValue().equals("1"))
//					{
//						runStateCnt=1;
//					}
//				}
//			}
//			if(reg.getRegDrShowType().equals("2"))
//			{
//				if(null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1"))
//				{
//					alarmStateCnt=2;
//				}
//			}
//			if(reg.getRegDrShowType().equals("3"))
//			{
//				if(null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("2"))
//				{
//					errorStateCnt=4;
//				}
//			}
//		}
//		int state = runStateCnt+alarmStateCnt+errorStateCnt;
		drinfo.setState(1);
		return drinfo;
	}
	
	@Override
	public List<Drinfo> findDrStateByPicid(String dBname, Integer picid) {
		// TODO Auto-generated method stub
		List<Reg> regList = piceditMapper.findDrStateByPicid(dBname,picid);
		List<Drinfo> drinfoList = piceditMapper.findDrbyPicid(dBname,picid);
		for(Drinfo drinfo : drinfoList)
		{
			int drid = drinfo.getDrid();
			int runStateCnt=0;
			int alarmStateCnt = 0;
			int errorStateCnt = 0;
			for(Reg reg : regList)
			{
//				String newvalue=piceditMapper.querytagvalue(dBname,reg.getTagName());
				if(reg.getDrId() == drid && reg.getRegDrShowType().equals("1"))
				{
					if(null != reg.getTagName() && !reg.getTagName().equals(""))
					{
						String value = piceditMapper.querytagvalue(dBname,reg.getTagName());
						if(value!=null&&value.equals("1"))
						{
							runStateCnt=1;
						}
					}
					else
					{
						if(reg.getTagValue().equals("1"))
						{
							runStateCnt=1;
						}
					}
				}
				if(reg.getDrId() == drid && reg.getRegDrShowType().equals("2"))
				{
					if(null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1"))
					{
						alarmStateCnt=2;
					}
				}
				if(reg.getDrId() == drid && reg.getRegDrShowType().equals("3"))
				{
					if(null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1"))
					{
						errorStateCnt=4;
					}
				}
			}
			int state = runStateCnt+alarmStateCnt+errorStateCnt;
			drinfo.setState(state);
		}
		return drinfoList;
	}

	@Override
	public int updatePicEditAndDrinfo(String dBname, Integer id, Integer drid, String isshowname, String drshowtype, String spid) {
		// TODO Auto-generated method stub
		int rows = 0;
		rows= piceditMapper.updatePicEditAndDrinfo(dBname,id,isshowname,drshowtype);
		rows= piceditMapper.updateDrinfoSpid(dBname,drid,spid);
		return rows;
	}

//	@Override
//	public List<Drtypeinfo> queryDrinfoReg(String dBname,String drtypeAndDrid) {
//		// TODO Auto-generated method stub
//		List<Drtypeinfo> drtypeinfoList = new ArrayList<Drtypeinfo>();
//		String[] drtypeAndDridArry = drtypeAndDrid.split(",",-1);
//		for(String drtypeAndDridStr : drtypeAndDridArry)
//		{
//			Drtypeinfo drtypeinfo = new Drtypeinfo();
//			List<Drinfo> drinfoList = new ArrayList<Drinfo>();
//			String[] dridArry = drtypeAndDridStr.split("_",-1);
//			Integer drtypeId = Integer.valueOf(dridArry[0]);
//			drtypeinfo = piceditMapper.queryDrinfoType(dBname,drtypeId);
//			List<Drtypemode> drtypemodeList = piceditMapper.queryDrtypemodePic(dBname,drtypeId);
//			int drinfoRunSum =0;
//			int drinfoAlarmSum = 0;
//			for(int i=1;i<dridArry.length;i++)
//			{
//				Integer drid = Integer.valueOf(dridArry[i]);
//				Drinfo drinfo = piceditMapper.queryDrinfo(dBname,drid);
//				List<Reg> regList = piceditMapper.queryReg(dBname,drid);
//				int flag =0;
//				int sum=0;
//				for(Reg reg: regList)
//				{
//					if(reg.getRegDrShowType()!=null&&reg.getTagValue()!=null &&reg.getRegDrShowType().equals("1")&& flag == 0)
//					{
//						if(null != reg.getTagName() && !reg.getTagName().equals(""))
//						{
//							String value = reg.getNewtagvalue();
//							if(value.equals("1"))
//							{
//								flag = 1;
//								drinfoRunSum = drinfoRunSum+1;
//							}
//						}
//						else
//						{
//							if(reg.getTagValue().equals("1"))
//							{
//								flag = 1;
//								drinfoRunSum = drinfoRunSum+1;
//							}
//						}
//					}
//					if(reg.getRegDrShowType()!=null&&reg.getTagAlarmState()!=null&&reg.getRegDrShowType().equals("2") && reg.getTagAlarmState().equals("1")&&sum==0)
//					{
//						drinfoAlarmSum = drinfoAlarmSum+1;
//						sum=1;
//					}
//				}
//				for(Drtypemode drtypemode : drtypemodeList)
//				{
//					for(Reg reg: regList)
//					{
//						int regShowLevel = 0;
//						if(reg.getRegListShowLevel() != null && !reg.getRegListShowLevel().equals(""))
//						{
//							regShowLevel = Integer.valueOf(reg.getRegListShowLevel());
//						}
//						if(drtypemode.getRegListShowLevel() == regShowLevel)
//						{
//							drtypemode.setRegReadWrite(reg.getRegReadWrite());
//							if(null != reg.getTagName() && !reg.getTagName().equals(""))
//							{
//								drtypemode.setTagValue(reg.getNewtagvalue());
//							}
//							else
//							{
//								drtypemode.setTagValue(reg.getTagValue());
//							}
//						}
//					}
//				}
//				drinfo.setDrtypemodeList(drtypemodeList);
//				drinfoList.add(drinfo);
//			}
//			drtypeinfo.setDrinfList(drinfoList);
//			drtypeinfo.setDrinfoSum(drinfoList.size());
//			drtypeinfo.setDrinfoRunSum(drinfoRunSum);
//			drtypeinfo.setDrinfoAlarmSum(drinfoAlarmSum);
//			drtypeinfoList.add(drtypeinfo);
//		}
//		return drtypeinfoList;
//	}
	
	@Override
	public List<Drinfo> findDrinfoValue(String dBname, Integer drtypeid, String drinfoArry) {
		// TODO Auto-generated method stub
		List<Drinfo> drinfoList = new ArrayList<Drinfo>();
		String[] dridArry = drinfoArry.split("_",-1);
		for(int i=0;i<dridArry.length;i++)
		{
			List<Drtypemode> drtypemodeList = piceditMapper.queryDrtypemodePic(dBname,drtypeid);
			Integer drid = Integer.valueOf(dridArry[i]);
			Drinfo drinfo = piceditMapper.queryDrinfo(dBname,drid);
			List<Reg> regList = piceditMapper.queryReg(dBname,drid);
			for(Drtypemode drtypemode : drtypemodeList)
			{
				for(Reg reg: regList)
				{
					int regShowLevel = 0;
					if(reg.getRegmath()!=null && !reg.getRegmath().equals("") && !reg.getRegmath().equals("null"))
					{
						reg.setTagValue(getMathValue(dBname,reg.getRegmath()));
						reg.setNewtagvalue(getMathValue(dBname,reg.getRegmath()));
					}
					if(reg.getRegListShowLevel() != null && !reg.getRegListShowLevel().equals(""))
					{
						regShowLevel = Integer.valueOf(reg.getRegListShowLevel());
					}
					if(drtypemode.getRegListShowLevel() == regShowLevel)
					{
						drtypemode.setRegReadWrite(reg.getRegReadWrite());
						if(null != reg.getTagName() && !reg.getTagName().equals(""))
						{
							drtypemode.setTagValue(reg.getNewtagvalue());
							drtypemode.setTagName(reg.getTagName());
						}
						else
						{
							drtypemode.setTagValue(reg.getTagValue());
						}
					}
				}
			}
			drinfo.setDrtypemodeList(drtypemodeList);
			drinfoList.add(drinfo);
		}
		return drinfoList;
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
					System.out.println(regmathArry[i]);
					Integer regId = Integer.valueOf(regmathArry[i]);
					String value = findTagValueByRegId(dBname,regId).replaceAll("^(0+)", "");
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
				DecimalFormat decimalFormat=new DecimalFormat(".00");
				mathValue=decimalFormat.format(temp);
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mathValue;
	}
	
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
	public List<Drtypeinfo> queryDrinfoReg(String dBname,Integer picid,Integer drtypeidIn) {
		List<Drtypeinfo> drtypeinfoList = new ArrayList<Drtypeinfo>();
		List<Drinfo> drinfoList=new ArrayList<Drinfo>();
		if(drtypeidIn == null)
		{
			drinfoList = piceditMapper.findDrbyPicid(dBname, picid);
		}
		else
		{
			String drtypeidtmps="";
			String drtypeids=getDrtypeids(dBname,drtypeidIn,drtypeidtmps);
			drtypeids=drtypeids+")";
			drinfoList = piceditMapper.findDrbyPicidAnddrtypeid(dBname, picid,drtypeids);
		}
		Map<Integer,List<Drinfo>> drtypeMap = new HashMap<Integer,List<Drinfo>>();
		for(Drinfo drinfo : drinfoList)
		{
			List<Drinfo> drinfoListTmp = drtypeMap.get(drinfo.getDrtypeid());
			if(drinfoListTmp == null)
			{
				List<Drinfo> drinfoListNew = new ArrayList<Drinfo>();
				drinfoListNew.add(drinfo);
				drtypeMap.put(drinfo.getDrtypeid(), drinfoListNew);
			}
			else
			{
				drtypeMap.get(drinfo.getDrtypeid()).add(drinfo);
			}
		}
		for(Integer drtypeid : drtypeMap.keySet())
		{
			List<Drinfo> drinfoList2 = drtypeMap.get(drtypeid);
			Drtypeinfo drtypeinfo = piceditMapper.queryDrinfoType(dBname,drtypeid);
//			List<Drtypemode> drtypemodeList = piceditMapper.queryDrtypemodePic(dBname,drtypeid);
			int drinfoRunSum =0;
			int drinfoAlarmSum = 0;
			for(Drinfo drinfo : drinfoList2)
			{
				List<Reg> regList = piceditMapper.queryReg(dBname,drinfo.getDrid());
				int flag =0;
				int sum=0;
				for(Reg reg: regList)
				{
					if(reg.getRegDrShowType()!=null&&reg.getTagValue()!=null &&reg.getRegDrShowType().equals("1")&& flag == 0)
					{
						if(null != reg.getTagName() && !reg.getTagName().equals("")&&!StringUtils.isEmpty(reg.getNewtagvalue()))
						{
							String value = reg.getNewtagvalue();
							if(value.equals("1"))
							{
								flag = 1;
								drinfoRunSum = drinfoRunSum+1;
							}
						}
						else
						{
							if(reg.getTagValue().equals("1"))
							{
								flag = 1;
								drinfoRunSum = drinfoRunSum+1;
							}
						}
					}
					if(reg.getRegDrShowType()!=null&&reg.getTagAlarmState()!=null&&reg.getRegDrShowType().equals("2") && reg.getTagAlarmState().equals("1")&&sum==0)
					{
						drinfoAlarmSum = drinfoAlarmSum+1;
						sum=1;
					}
				}
//				for(Drtypemode drtypemode : drtypemodeList)
//				{
//					for(Reg reg: regList)
//					{
//						int regShowLevel = 0;
//						if(reg.getRegListShowLevel() != null && !reg.getRegListShowLevel().equals(""))
//						{
//							regShowLevel = Integer.valueOf(reg.getRegListShowLevel());
//						}
//						if(drtypemode.getRegListShowLevel() == regShowLevel)
//						{
//							drtypemode.setRegReadWrite(reg.getRegReadWrite());
//							if(null != reg.getTagName() && !reg.getTagName().equals(""))
//							{
//								drtypemode.setTagValue(reg.getNewtagvalue());
//							}
//							else
//							{
//								drtypemode.setTagValue(reg.getTagValue());
//							}
//						}
//					}
//				}
//				drinfo.setDrtypemodeList(drtypemodeList);
			}
			drtypeinfo.setDrinfList(drinfoList2);
			drtypeinfo.setDrinfoSum(drinfoList2.size());
			drtypeinfo.setDrinfoRunSum(drinfoRunSum);
			drtypeinfo.setDrinfoAlarmSum(drinfoAlarmSum);
			drtypeinfoList.add(drtypeinfo);
			
		}
		return drtypeinfoList;
	}

	@Override
	public String findImagesByPicid(String dBname, Integer picid) {
		// TODO Auto-generated method stub
		return piceditMapper.findImagesByPicid(dBname,picid);
	}

	@Override
	public List<Picedit> findPiceditElByPicid(String dBname, Integer picid) {
		// TODO Auto-generated method stub
		return piceditMapper.findPiceditElByPicid(dBname,picid);
	}

	@Override
	public void importObjects(String dBname, List<Picedit> list) {
		// TODO Auto-generated method stub
		Integer id;
		for(Picedit picedit : list)
		{
			id=picedit.getId();
			if(id == null || id == 0)
			{
				saveObject(dBname,picedit);
			}
			else
			{
				Picedit piceidtt = piceditMapper.findPiceditById(dBname,id);
				if(null == piceidtt)
				{
					saveObject(dBname,picedit);
				}
				else
				{
					piceditMapper.updatePicEditAll(dBname, picedit);
				}
			}
		}
	}

	@Override
	public void copyPicedit(String dBname, Integer picid1, Integer picid2) {
		// TODO Auto-generated method stub
		List<Picedit> Piceditlist = piceditMapper.findAllPiceditByPicid(dBname,picid1);
		if(Piceditlist.size()>0)
		{
			for(Picedit picedit : Piceditlist)
			{
				Integer id = picedit.getId();
				List<Elements> Elementslist = piceditMapper.queryElementsByElemtpiceditid(dBname,id);
				picedit.setPicid(picid2);
				piceditMapper.insertObject(dBname, picedit);
				Integer elemtpiceditid = piceditMapper.getElemtpiceditid(dBname);
				if(Elementslist.size()>0)
				{
					for(Elements elements : Elementslist)
					{
						elements.setElemtpiceditid(elemtpiceditid);
						piceditMapper.insertElements(dBname, elements);
					}
				}
			}
		}
	}
	
	@Override
	public void copyPiceditByids(String dBname,Integer[] ids,Integer picid1,Integer picid2) {
		// TODO Auto-generated method stub
		if(ids.length>0)
		{
			for(Integer id : ids)
			{
				Picedit picedit = piceditMapper.findPiceditById(dBname,id);
				if(picedit != null)
				{
					List<Elements> Elementslist = piceditMapper.queryElementsByElemtpiceditid(dBname,id);
					if(picid1.intValue() != picid2.intValue())
					{
						picedit.setPicid(picid2);
					}
					piceditMapper.insertObject(dBname, picedit);
					Integer elemtpiceditid = piceditMapper.getElemtpiceditid(dBname);
					if(Elementslist.size()>0)
					{
						for(Elements elements : Elementslist)
						{
							elements.setElemtpiceditid(elemtpiceditid);
							piceditMapper.insertElements(dBname, elements);
						}
					}
				}
			}
		}
	}

	@Override
	public List<Picedit> findAllPiceditByPicid(String dBname, Integer picid) {
		// TODO Auto-generated method stub
		return piceditMapper.findAllPiceditByPicid(dBname,picid);
	}

	@Override
	public void piceditAngle(String dBname, Integer id, Integer type) {
		// TODO Auto-generated method stub
		int angle = piceditMapper.findPiceditAngleById(dBname,id);
		if(1 == type)
		{
			angle=angle+45;
			if(angle>=360)
			{
				angle=angle-360;
			}
		}
		else
		{
			angle=angle-45;
			if(angle<0)
			{
				angle=angle+360;
			}
		}
		piceditMapper.updatePiceditAngle(dBname,id,angle);
	}

	@Override
	public void piceditZindex(String dBname, Integer id, Integer type) {
		// TODO Auto-generated method stub
		String zindex = piceditMapper.findPiceditZindexById(dBname,id);
		Integer z = Integer.valueOf(zindex);
		if(1 == type)
		{
			z=z+1;
		}
		else
		{
			z=z-1;
		}
		zindex=z.toString();
		piceditMapper.updatePiceditZindex(dBname,id,zindex);
	}

	@Override
	public int updatePicEditLngAndLat(String dBname, Integer id, String lng, String lat) {
		// TODO Auto-generated method stub
		int rows = 0;
		rows = piceditMapper.updatePicEditLngAndLat(dBname,id,lng,lat);
		System.out.println("rows=="+rows);
		return rows;
	}

}
