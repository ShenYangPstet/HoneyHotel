package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.DrtypeElements;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Jgtmode;

public interface JgtModeMapper {

	List<Drtypemode> findDrtypemode(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	int saveJgtMode(@Param("dBname")String dBname, Jgtmode jgtmode);

	Integer getJgtjgtmodeid(@Param("dBname")String dBname);

	int insertDrtypeElements(@Param("dBname")String dBname, DrtypeElements drtypeElements);

	List<Jgtmode> findJgtBydrtypeid(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	Drtypemode findDrtypemodeByLevel(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid, @Param("typemodelevel")Integer typemodelevel);

	List<DrtypeElements> findDrtypeElements(@Param("dBname")String dBname, @Param("jgtmodeid")Integer jgtmodeid);

	int deleteAll(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	int delete(@Param("dBname")String dBname, @Param("id")Integer[] id);

	int deleteDrtypeElements(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

	int deleteDrtypeElements2(@Param("dBname")String dBname, @Param("id")Integer [] id);

	int updateJgt(@Param("dBname")String dBname, @Param("id")Integer id, @Param("jgtx")String jgtx, @Param("jgty")String jgty, @Param("zIndex")String zIndex, @Param("jgth")Integer jgth, @Param("jgtw")Integer jgtw, @Param("angle")Integer angle);

	int updateJgtmodeElements(@Param("dBname")String dBname, DrtypeElements drtypeElements);

	Jgtmode findJgtById(@Param("dBname")String dBname, @Param("id")Integer id);

}
