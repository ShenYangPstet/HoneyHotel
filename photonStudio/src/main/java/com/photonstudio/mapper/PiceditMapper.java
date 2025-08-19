package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Elements;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.pojo.Picedit;
import com.photonstudio.pojo.Reg;

public interface PiceditMapper {

	List<Picedit> findPiceditByPicid(@Param("dBname")String dBname, @Param("picid")Integer picid,@Param("staticAccessPath")String staticAccessPath, List<Integer> dridlist);

	int insertObject(@Param("dBname")String dBname, Picedit picedit);

	int deletePicEdit(@Param("dBname")String dBname, @Param("id")Integer id);

	int updatePicEdit(@Param("dBname")String dBname, Picedit picedit);

	List<Elemttypemode> findElemttypemodeByElemttypeid(@Param("dBname")String dBname, @Param("elemttypeid")Integer elemttypeid);

	Integer getElemtpiceditid(@Param("dBname")String dBname);

	int insertElements(@Param("dBname")String dBname, Elements elements);

	List<Elements> findElementsListByElepicid(@Param("dBname")String dBname, @Param("elemtpiceditid")Integer elemtpiceditid);

	int updateElements(@Param("dBname")String dBname, Elements elements);

	int deleteElements(@Param("dBname")String dBname, @Param("id")Integer id);

	int selectElements(@Param("dBname")String dBname, @Param("id")Integer id);

	Picedit findPiceditById(@Param("dBname")String dBname, @Param("id")Integer id);

	Drinfo findDrinfoBydrid(@Param("dBname")String dBname, @Param("drid")Integer drid, @Param("staticAccessPath")String staticAccessPath);

	int updatePicEditAndDrinfo(@Param("dBname")String dBname, @Param("id")Integer id, @Param("isshowname")String isshowname, @Param("drshowtype")String drshowtype);

	int updateDrinfoSpid(@Param("dBname")String dBname, @Param("drid")Integer drid, @Param("spid")String spid);

	Drtypeinfo queryDrinfoType(@Param("dBname")String dBname, @Param("drtypeId")Integer drtypeId);

	Drinfo queryDrinfo(@Param("dBname")String dBname, @Param("drid")Integer drid);

	List<Reg> queryReg(@Param("dBname")String dBname, @Param("drid")Integer drid);

	List<Drtypemode> queryDrtypemodePic(@Param("dBname")String dBname, @Param("drtypeId")Integer drtypeId);

	String findImagesByPicid(@Param("dBname")String dBname, @Param("picid")Integer picid);

	List<Reg> findrunstateBydrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	int findalarmstateBydrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	int finderrorstateBydrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	String querytagvalue(@Param("dBname")String dBname, @Param("tagName")String tagName);

	List<Reg> findDrStateByPicid(@Param("dBname")String dBname, @Param("picid")Integer picid);

	List<Drinfo> findDrbyPicid(@Param("dBname")String dBname, @Param("picid")Integer picid);

	List<Picedit> findPiceditElByPicid(@Param("dBname")String dBname, @Param("picid")Integer picid);

	Integer getDrtypeiconId(@Param("dBname")String dBname, @Param("drid")Integer drid);

	List<Picedit> findPiceditByPicidAnddrtypeid(@Param("dBname")String dBname, @Param("picid")Integer picid, @Param("staticAccessPath")String staticAccessPath,
			@Param("drtypeids")String drtypeids);

	List<Drinfo> findDrbyPicidAnddrtypeid(@Param("dBname")String dBname, @Param("picid")Integer picid, @Param("drtypeids")String drtypeids);

	int findDrtypeParentId(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	void updatePicEditAll(@Param("dBname")String dBname, @Param("picedit")Picedit picedit);

	List<Picedit> findAllPiceditByPicid(@Param("dBname")String dBname, @Param("picid")Integer picid);

	List<Elements> queryElementsByElemtpiceditid(@Param("dBname")String dBname, @Param("id")Integer id);

	int findPiceditAngleById(@Param("dBname")String dBname, @Param("id")Integer id);

	void updatePiceditAngle(@Param("dBname")String dBname, @Param("id")Integer id, @Param("angle")Integer angle);

	String findPiceditZindexById(@Param("dBname")String dBname, @Param("id")Integer id);

	void updatePiceditZindex(@Param("dBname")String dBname, @Param("id")Integer id, @Param("zindex")String zindex);

	List<Picedit> findPiceditByPicidAndIds(@Param("dBname")String dBname, @Param("picid")Integer picid, Integer[] ids);

	Elements findElementsByElemtsid(@Param("dBname")String dBname, @Param("elemtsid")Integer elemtsid);

	int updatePicEditLngAndLat(@Param("dBname")String dBname, @Param("id")Integer id, @Param("lng")String lng, @Param("lat")String lat);

	int deletePicEditByDrid(@Param("dBname")String dBname, @Param("drids")Integer... drids);

}
