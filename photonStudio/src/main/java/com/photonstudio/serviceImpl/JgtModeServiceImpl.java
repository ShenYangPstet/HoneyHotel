package com.photonstudio.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.mapper.JgtModeMapper;
import com.photonstudio.mapper.PiceditMapper;
import com.photonstudio.pojo.DrtypeElements;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.pojo.Jgtmode;
import com.photonstudio.service.JgtModeService;

@Service
public class JgtModeServiceImpl implements JgtModeService{
	
	@Autowired
	private JgtModeMapper jgtModeMapper;
	@Autowired
	private PiceditMapper piceditMapper;


	@Override
	public int saveJgtmodeElements(String dBname, Integer drtypeid, Integer elemttypeid, Integer jgth, Integer jgtw) {
		// TODO Auto-generated method stub
		Jgtmode jgtmode = new Jgtmode();
		jgtmode.setTypeid(drtypeid);
		jgtmode.setElemttypeid(elemttypeid);
		jgtmode.setJgtx("0");
		jgtmode.setJgty("0");
		jgtmode.setZIndex("1");
		jgtmode.setType("2");
		jgtmode.setJgth(jgth);
		jgtmode.setJgtw(jgtw);
		jgtmode.setAngle(0);
		int rows = jgtModeMapper.saveJgtMode(dBname,jgtmode);
		Integer jgtmodeid = jgtModeMapper.getJgtjgtmodeid(dBname);
		List<Elemttypemode> elemttypemodeList = piceditMapper.findElemttypemodeByElemttypeid(dBname,elemttypeid);
		for(Elemttypemode elemttypemode : elemttypemodeList)
		{
			DrtypeElements drtypeElements = new DrtypeElements();
			drtypeElements.setJgtmodeid(jgtmodeid);
			drtypeElements.setElemttypeid(elemttypeid);
			drtypeElements.setElemtELtype(String.valueOf(elemttypemode.getElemtELtype()));
			drtypeElements.setElemtlevel(String.valueOf(elemttypemode.getElemtlevel()));
			drtypeElements.setElemtstyle(elemttypemode.getElemtstyle());
			drtypeElements.setText(elemttypemode.getText());
			drtypeElements.setImgid(elemttypemode.getImgid());
			drtypeElements.setElemtmodename(elemttypemode.getElemtmodename());
			rows = jgtModeMapper.insertDrtypeElements(dBname,drtypeElements);
		}
		return rows;
	}

	@Override
	public int saveJgtMode(String dBname, Integer drtypeid) {
		// TODO Auto-generated method stub
		List<Drtypemode> drtypemodeList = jgtModeMapper.findDrtypemode(dBname,drtypeid);
		System.out.println("drtypemodeList=="+drtypemodeList);
		int rows = 0;
		if(drtypemodeList == null)
		{
			return -1;
		}
		for(Drtypemode drtypemode : drtypemodeList)
		{
			Jgtmode jgtmode = new Jgtmode();
			jgtmode.setTypeid(drtypeid);
			jgtmode.setTypemodelevel(String.valueOf(drtypemode.getRegListShowLevel()));
			jgtmode.setJgtx("0");
			jgtmode.setJgty("0");
			jgtmode.setZIndex("9");
			jgtmode.setType("1");
			jgtmode.setJgth(0);
			jgtmode.setJgtw(0);
			jgtmode.setAngle(0);
			rows = jgtModeMapper.saveJgtMode(dBname,jgtmode);
		}
		return rows;
	}

	@Override
	public List<Jgtmode> findJgtBydrtypeid(String dBname, Integer drtypeid) {
		// TODO Auto-generated method stub
		List<Jgtmode> jgtmodeList = jgtModeMapper.findJgtBydrtypeid(dBname,drtypeid);
		for(Jgtmode jgtmode : jgtmodeList)
		{
			if(jgtmode.getType().equals("1"))
			{
				Integer typemodelevel = Integer.valueOf(jgtmode.getTypemodelevel());
				Drtypemode drtypemode = jgtModeMapper.findDrtypemodeByLevel(dBname, drtypeid,typemodelevel);
				jgtmode.setDrtypemode(drtypemode);
			}
			if(jgtmode.getType().equals("2"))
			{
				Integer jgtmodeid = jgtmode.getId();
				List<DrtypeElements> drtypeElementsList = jgtModeMapper.findDrtypeElements(dBname,jgtmodeid);
				jgtmode.setDrtypeElementsList(drtypeElementsList);
			}
		}
		return jgtmodeList;
	}

	@Override
	public int deleteAll(String dBname, Integer drtypeid) {
		// TODO Auto-generated method stub
		int rows = jgtModeMapper.deleteDrtypeElements(dBname,drtypeid);
		rows = jgtModeMapper.deleteAll(dBname,drtypeid);
		return rows;
	}

	@Override
	public int delete(String dBname, Integer [] id,String type) {
		// TODO Auto-generated method stub
		int rows = jgtModeMapper.delete(dBname,id);
		if(type.equals("2"))
		{
			rows = jgtModeMapper.deleteDrtypeElements2(dBname,id);
		}
		return rows;
	}

	@Override
	public int updateJgt(String dBname, String jgtupd) {
		// TODO Auto-generated method stub
		System.out.println("jgtupd=="+jgtupd);
		String[] jgtupdArray = jgtupd.split(",");
		int rows = 0;
		for(String jgtStr : jgtupdArray)
		{
			String[] jgtStrArray = jgtStr.split("_");
			Integer id = Integer.valueOf(jgtStrArray[0]);
			String jgtx = jgtStrArray[1];
			String jgty = jgtStrArray[2];
			String zIndex = jgtStrArray[3];
			Integer jgth = Integer.valueOf(jgtStrArray[4]);
			Integer jgtw = Integer.valueOf(jgtStrArray[5]);
			Integer angle = Integer.valueOf(jgtStrArray[6]);
			rows = jgtModeMapper.updateJgt(dBname,id,jgtx,jgty,zIndex,jgth,jgtw,angle);
		}
		return rows;
	}

	@Override
	public int updateJgtmodeElements(String dBname, DrtypeElements drtypeElements) {
		// TODO Auto-generated method stub
		int rows = jgtModeMapper.updateJgtmodeElements(dBname,drtypeElements);
		return rows;
	}

	@Override
	public List<Drtypemode> findModeBydrtypeid(String dBname, Integer drtypeid) {
		// TODO Auto-generated method stub
		System.out.println("drtypeid=="+drtypeid);
		return jgtModeMapper.findDrtypemode(dBname,drtypeid);
	}

	@Override
	public List<DrtypeElements> findJgtmodeElements(String dBname, Integer jgtmodeid) {
		// TODO Auto-generated method stub
		return jgtModeMapper.findDrtypeElements(dBname,jgtmodeid);
	}

	@Override
	public void copyJgt(String dBname, Integer drtypeid1, Integer drtypeid2) {
		// TODO Auto-generated method stub
		List<Jgtmode> jgtmodeList = jgtModeMapper.findJgtBydrtypeid(dBname,drtypeid1);
		for(Jgtmode jgtmode : jgtmodeList)
		{
			if(jgtmode.getType().equals("2"))
			{
				Integer id = jgtmode.getId();
				List<DrtypeElements> DrtypeElementsList = jgtModeMapper.findDrtypeElements(dBname,id);
				jgtmode.setTypeid(drtypeid2);
				jgtModeMapper.saveJgtMode(dBname,jgtmode);
				Integer jgtmodeid = jgtModeMapper.getJgtjgtmodeid(dBname); 
				if(DrtypeElementsList.size()>0)
				{
					for(DrtypeElements drtypeElements : DrtypeElementsList)
					{
						drtypeElements.setJgtmodeid(jgtmodeid);
						jgtModeMapper.insertDrtypeElements(dBname,drtypeElements);
					}
				}
			}
		}
	}

	@Override
	public void copyJgtByids(String dBname, Integer[] ids, Integer drtypeid1, Integer drtypeid2) {
		// TODO Auto-generated method stub
		if(ids.length>0)
		{
			for(Integer id : ids)
			{
				Jgtmode jgtmode = jgtModeMapper.findJgtById(dBname,id);
				if(jgtmode != null)
				{
					List<DrtypeElements> DrtypeElementsList = jgtModeMapper.findDrtypeElements(dBname,id);
					if(drtypeid1.intValue() != drtypeid2.intValue())
					{
						jgtmode.setTypeid(drtypeid2);
					}
					jgtModeMapper.saveJgtMode(dBname,jgtmode);
					Integer jgtmodeid = jgtModeMapper.getJgtjgtmodeid(dBname); 
					if(DrtypeElementsList.size()>0)
					{
						for(DrtypeElements drtypeElements : DrtypeElementsList)
						{
							drtypeElements.setJgtmodeid(jgtmodeid);
							jgtModeMapper.insertDrtypeElements(dBname,drtypeElements);
						}
					}
				}
			}
		}
	}

}
