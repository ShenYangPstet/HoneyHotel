package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Drtypemode;

@Mapper
public interface DrtypeinfoMapper extends BaseMapper<Drtypeinfo> {

	List<Drtypeinfo> findObjectByParentId(@Param("dBname")String dBname,
										  @Param("parentid") Integer parentid,
										  @Param("iscustomType")Integer iscustomType);
	int deleteObjectById(@Param("dBname") String dBname,
						 @Param("drtypeid")Integer drtypeid);
	List<Integer> findChildrenId(@Param("dBname") String dBname,
								 @Param("drtypeid")Integer drtypeid);
	int insertObject(@Param("dBname") String dBname,Drtypeinfo drtypeinfo);
	int updateObject(@Param("dBname") String dBname,Drtypeinfo drtypeinfo);
	List<Drtypeinfo> findAllDrtypeAndDr(@Param("dBname")String dBname,@Param("iscustomType") Integer iscustomType);
	int findCountByParentId(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);
	List<Drinfo> findDrinfoByDrtypeid(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid, @Param("staticAccessPath")String staticAccessPath,@Param("drname") String drname,@Param("drid")Integer drid);
	List<Drtypeinfo> findAllDrtypeJgtr(@Param("dBname")String dBname);
	int getRowCountByiscustomType(@Param("dBname")String dBname,@Param("iscustomType") Integer iscustomType);
	List<Drtypeinfo> findObjectByiscustomType(@Param("dBname")String dBname, @Param("iscustomType")Integer iscustomType, 
										@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize);
	Drtypeinfo findDrtypeinfoByDrid(@Param("dBname")String dBname,@Param("drid") Integer drid);
	Drtypeinfo findObjectByDrtypeid(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);
	double findWorkorderByDrtype(@Param("dBname")String dBname, Integer drtypeid, Date date1, Date date2, Date date3);
	List<Drtypeinfo> findBasisDrtype(@Param("dBname")String dBname);
	List<Drtypemode> findModeList(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);
	void deleteDrtypeinfoById(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);
	Drtypeinfo findDrtypeinfoBytypeid(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);

    int deleteTypeAndModeById(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);


}
