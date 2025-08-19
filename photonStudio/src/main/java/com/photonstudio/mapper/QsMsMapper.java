package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Qsmsdz;
import com.photonstudio.pojo.Qsmsjb;
import com.photonstudio.pojo.Qsmsjblog;
import com.photonstudio.pojo.Qsmsjbtime;
import com.photonstudio.pojo.Qsmsjg;
import com.photonstudio.pojo.Qsmsrw;

public interface QsMsMapper {

	List<Qsmsrw> findMsRw(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	void insertMsRw(@Param("dBname")String dBname, Qsmsrw qsmsrw);

	void updateMsRw(@Param("dBname")String dBname, Qsmsrw qsmsrw);

	void deleteMsRw(@Param("dBname")String dBname, @Param("id")Integer id);

	List<Qsmsdz> findMsDz(@Param("dBname")String dBname, @Param("rwid")Integer rwid, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	void insertMsDz(@Param("dBname")String dBname, Qsmsdz qsmsdz);

	void updateMsDz(@Param("dBname")String dBname, Qsmsdz qsmsdz);

	void deleteMsDz(@Param("dBname")String dBname, @Param("id")Integer id);

	List<Qsmsjg> findMsJg(@Param("dBname")String dBname, @Param("dzid")Integer dzid, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	void insertMsJg(@Param("dBname")String dBname, Qsmsjg qsmsjg);

	void updateMsJg(@Param("dBname")String dBname, Qsmsjg qsmsjg);

	void deleteMsJg(@Param("dBname")String dBname, @Param("ids")Integer[] id);

	int getCount(@Param("dBname")String dBname, @Param("rwid")Integer rwid);

	int getCountJg(@Param("dBname")String dBname, @Param("dzid")Integer dzid);

	List<Qsmsjg> findQsmsjg(@Param("dBname")String dBname, @Param("dzid")Integer dzid);

	int findRowByJgId(@Param("dBname")String dBname, @Param("id")Integer id);

	int findRowByRwId(@Param("dBname")String dBname, @Param("id")Integer id);

	List<Qsmsrw> findAllMsRw(@Param("dBname")String dBname);

	void insertMsJb(@Param("dBname")String dBname, List<Qsmsjb> jbList);

	void deleteMsJb(@Param("dBname")String dBname, @Param("ymds")String[] ymds);

	List<Qsmsjb> findAllMsJb(@Param("dBname")String dBname);

	List<Qsmsdz> findMsDzByRwid(@Param("dBname")String dBname, @Param("rwid")Integer rwid);

	void insertMsJbTime(@Param("dBname")String dBname, List<Qsmsjbtime> timeList);

	void deleteMsJbTime(@Param("dBname")String dBname, @Param("ymds")String[] ymds);

	List<Qsmsjbtime> findMSjbtimeBYymd(@Param("dBname")String dBname, @Param("ymds")String[] ymds);

	List<Qsmsjbtime> findMSjbtime(@Param("dBname")String dBname);

	List<Qsmsdz> findAllMSdz(@Param("dBname")String dBname);

	List<Qsmsjbtime> findQsMsJbtimeAfNow(@Param("dBname")String dBname);

	void insertQsmsjblog(@Param("dBname")String dBname, Qsmsjblog qsmsjblog);

	int getCountLog(@Param("dBname")String dBname,@Param("startTime") Date startTime,@Param("endTime")Date endTime);

	List<Qsmsjblog> findMsJbLog(@Param("dBname")String dBname,@Param("startTime") Date startTime,@Param("endTime")Date endTime, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	Qsmsdz findMsDzById(@Param("dBname")String dBname, @Param("dzid")Integer dzid);

	List<Qsmsjbtime> findMSjbtimeBYdzid(@Param("dBname")String dBname, @Param("dzid")Integer dzid);

	void updateQsmsjbtime(@Param("dBname")String dBname, List<Qsmsjbtime> qsmsjbtimeList);

}
