package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Elements;
import com.photonstudio.pojo.Picedit;

public interface PiceditService {

	List<Picedit> findPiceditByPicid(String dBname, Integer picid, Integer drtypeid);

	int saveObject(String dBname, Picedit picedit);

	int deleteObject(String dBname, Integer[] ids);

	int updatePicEdit(String dBname, String piceditupd);

	List<Elements> findElementsListByElepicid(String dBname, Integer elemtpiceditid);

	int updateElements(String dBname, Elements elements);

	Picedit findPiceditById(String dBname, Integer id);

	int savePicEditDr(String dBname, List<Picedit> piceditList);

	Drinfo findDrinfoBydrid(String dBname, Integer drid);

	int updatePicEditAndDrinfo(String dBname, Integer id, Integer drid, String isshowname, String drshowtype, String spid);

	List<Drtypeinfo> queryDrinfoReg(String dBname,Integer picid, Integer drtypeid);

	String findImagesByPicid(String dBname, Integer picid);

	List<Drinfo> findDrStateByPicid(String dBname, Integer picid);

	List<Drinfo> findDrinfoValue(String dBname, Integer drtypeid, String drinfoArry);

	List<Picedit> findPiceditElByPicid(String dBname, Integer picid);

	void importObjects(String dBname, List<Picedit> list);

	void copyPicedit(String dBname, Integer picid1, Integer picedit2);

	List<Picedit> findAllPiceditByPicid(String dBname, Integer picid);

	void piceditAngle(String dBname, Integer id, Integer type);

	void piceditZindex(String dBname, Integer id, Integer type);

	void copyPiceditByids(String dBname,Integer[] ids,Integer picid1,Integer picid2);

	int updatePicEditLngAndLat(String dBname, Integer id, String lng, String lat);

}
