package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.DrtypeElements;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Jgtmode;

public interface JgtModeService {


	int saveJgtmodeElements(String dBname, Integer drtypeid, Integer elemttypeid, Integer jgth, Integer jgtw);

	int saveJgtMode(String dBname, Integer drtypeid);

	List<Jgtmode> findJgtBydrtypeid(String dBname, Integer drtypeid);

	int deleteAll(String dBname, Integer drtypeid);

	int delete(String dBname, Integer [] id,String type);

	int updateJgt(String dBname, String jgtupd);

	int updateJgtmodeElements(String dBname, DrtypeElements drtypeElements);

	List<Drtypemode> findModeBydrtypeid(String dBname, Integer drtypeid);

	List<DrtypeElements> findJgtmodeElements(String dBname, Integer jgtmodeid);

	void copyJgt(String dBname, Integer drtypeid1, Integer drtypeid2);

	void copyJgtByids(String dBname, Integer[] ids, Integer drtypeid1, Integer drtypeid2);

}
