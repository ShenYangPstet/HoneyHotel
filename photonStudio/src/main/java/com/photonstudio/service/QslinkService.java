package com.photonstudio.service;

import java.util.Date;
import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.*;

public interface QslinkService {

	List<Qslinktj> findLinkTj(String dBname);

	void updateLinkTj(String dBname, Qslinktj qslinktj);

	void deleteLinkTj(String dBname, Integer id);

	PageObject<Qslinktjinfo> findLinkTjinfo(String dBname, Integer tjId, Integer pageCurrent, Integer pageSize);

	void insertLinkTj(String dBname, Qslinktj qslinktj);

	void updateLinkTjinfo(String dBname, Qslinktjinfo qslinktjinfo);

	void insertLinkTjinfo(String dBname, Integer tjId, Integer[] regIds, String minValue, String maxValue,
			Integer relation);

	void deleteLinkTjinfo(String dBname, Integer[] ids);

	List<Qslinkjg> findLinkJg(String dBname);

	void insertLinkJg(String dBname, Qslinkjg qslinkjg);

	void updateLinkJg(String dBname, Qslinkjg qslinkjg);

	void deleteLinkJg(String dBname, Integer id);

	PageObject<Qslinkjginfo> findLinkJginfo(String dBname, Integer jgId, Integer pageCurrent, Integer pageSize);

	//void insertLinkJginfo(String dBname, Integer jgId, Integer[] regIds, String value);

	void updateLinkJginfo(String dBname, Qslinkjginfo qslinkjginfo);

	void deleteLinkJginfo(String dBname, Integer[] ids);

	PageObject<Qslinkrw> findLinkRw(String dBname, Integer tjId, Integer jgId, Integer pageCurrent, Integer pageSize);

	void insertLinkRw(String dBname,Integer appid, Qslinkrw qslinkrw);

	void updateLinkRw(String dBname, Qslinkrw qslinkrw);

	void deleteLinkRw(String dBname, Integer[] ids);

	List<Qslinktjinfo> exportTjinfo(String dBname, Integer tjId);

	void insertLinkTjinfoImport(String dBname, Qslinktjinfo qslinktjinfo);

	List<Qslinkrw> findQslinkrwList(String dBname);

	List<Qslinkjginfo> exportJginfo(String dBname, Integer jgId);

	void insertLinkJginfoImport(String dBname, Qslinkjginfo qslinkjginfo);

	List<Qslinkrw> exportRw(String dBname, Integer tjId, Integer jgId);

	void insertLinkRwImport(String dBname, List<Qslinkrw> list);

	void insertLinkJginfo(String dBname, Qslinkjginfo qslinkjginfo, Integer[] regIds);

    PageObject<QsLinkLog> findLog(String dBname, Date time, Integer pageCurrent, Integer pageSize,String jgName);

	PageObject<QsLinkLog> findByPage(QsLinkLog dto);

	List<Qslinktj> findBytjId(String dBname,Integer [] id);


	void insertimportQslinktj(String dBname,Qslinktj qslinktj);

	int countByQslinktj(String dBname,Integer tjid);

	List<Qslinkjg> findByid(String dBname,Integer [] ids);

	int countByJG(String dBname,Integer jgid);
}
