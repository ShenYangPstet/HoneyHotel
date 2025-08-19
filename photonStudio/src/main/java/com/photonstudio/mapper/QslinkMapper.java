package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import com.photonstudio.pojo.*;
import org.apache.ibatis.annotations.Param;

public interface QslinkMapper {

	List<Qslinktj> findLinkTj(@Param("dBname") String dBname);

	void updateLinkTj(@Param("dBname")String dBname, Qslinktj qslinktj);

	void deleteLinkTj(@Param("dBname")String dBname, @Param("id")Integer id);

	List<Qslinktjinfo> findLinkTjinfo(@Param("dBname")String dBname, @Param("tjId")Integer tjId);

	void insertLinkTj(@Param("dBname")String dBname, Qslinktj qslinktj);

	void updateLinkTjinfo(@Param("dBname")String dBname, Qslinktjinfo qslinktjinfo);

	void insertLinkTjinfo(@Param("dBname")String dBname, List<Qslinktjinfo> list);

	void deleteLinkTjinfo(@Param("dBname")String dBname, @Param("ids")Integer[] ids);

	List<Qslinkjg> findLinkJg(@Param("dBname")String dBname);

	void insertLinkJg(@Param("dBname")String dBname, Qslinkjg qslinkjg);

	void updateLinkJg(@Param("dBname")String dBname, Qslinkjg qslinkjg);

	void deleteLinkJg(@Param("dBname")String dBname, @Param("id")Integer id);

	int getCountJginfo(@Param("dBname")String dBname, @Param("jgId")Integer jgId);

	List<Qslinkjginfo> findLinkJginfo(@Param("dBname")String dBname, @Param("jgId")Integer jgId, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	void insertLinkJginfo(@Param("dBname")String dBname, List<Qslinkjginfo> list);

	void updateLinkJginfo(@Param("dBname")String dBname, Qslinkjginfo qslinkjginfo);

	void deleteLinkJginfo(@Param("dBname")String dBname, @Param("ids")Integer[] ids);

	int getCountLinkRw(@Param("dBname")String dBname, @Param("tjId")Integer tjId, @Param("jgId")Integer jgId);

	List<Qslinkrw> findLinkRw(@Param("dBname")String dBname, @Param("tjId")Integer tjId, @Param("jgId")Integer jgId, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	void insertLinkRw(String dBnameRw, Qslinkrw qslinkrw);

	void updateLinkRw(@Param("dBname")String dBname, Qslinkrw qslinkrw);

	void deleteLinkRw(@Param("dBname")String dBname, @Param("ids")Integer[] ids);

	Qslinkrw findLinkRwByRwid(@Param("dBname")String dBname, @Param("rwId")Integer rwId);

	String findRegDbValue(@Param("dBname")String dBname, @Param("tagname")String tagname);

	List<Qslinkjginfo> findLinkJginfoByJgid(@Param("dBname")String dBname, @Param("jgId")Integer jgId);

	int getCountTjinfo(@Param("dBname")String dBname, @Param("tjId")Integer tjId);

	List<Qslinktjinfo> findLinkTjinfoPage(@Param("dBname")String dBname, @Param("tjId")Integer tjId, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	List<Qslinkrw> findQslinkrwList(@Param("dBname")String dBname);

	List<Qslinkrw> exportRw(String dBname, Integer tjId, Integer jgId);

	int saveLog(@Param("dBname")String dBname, QsLinkLog qsLinkLog);

	List<QsLinkLog> findLogPage(@Param("dBname")String dBname, @Param("time")Date time, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize, @Param("jgName")String jgName);

	int getLogCount(@Param("dBname")String dBname,@Param("time")Date time,@Param("jgName")String jgName,@Param("startTime") Date startTime,@Param("endTime") Date endTime);

	List<QsLinkLog> findByPage(QsLinkLog qsLinkLog);


	List<Qslinktj> findBytjid(@Param("dBname")String dBname,@Param("tjid")Integer [] tjid);

    int countByQslinktj(String dBname, Integer tjid);

	List<Qslinkjg> findByid(String dBname, Integer[] ids);

	int countByJG(String dBname, Integer jgid);
}
