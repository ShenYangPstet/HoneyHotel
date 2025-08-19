package com.photonstudio.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photonstudio.common.vo.RegSub;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Qstag;
import com.photonstudio.pojo.Reg;

public interface RegMapper extends BaseMapper<Reg> {

	int getRowCount(@Param(value="dBname")String dBname, @Param(value="drId")Integer drId);

	List<Reg> findObject(@Param(value="dBname")String dBname, @Param(value="drId")Integer drId,@Param(value="startIndex")Integer startIndex, @Param(value="pageSize")Integer pageSize);

	int insertObject(@Param(value="dBname")String dBname, Reg reg);

	int deleteObjectById(@Param(value="dBname")String dBname, Integer[] ids);

	int updateObject(@Param(value="dBname")String dBname, Reg reg);

	int findObjectById(@Param(value="dBname")String dBname, @Param(value="reg_id")Integer reg_id);

	List<Qstag> findQstag(@Param(value="dBname")String dBname);
	
	List<String> findTagnameByDrid(@Param(value="dBname")String dBname,@Param("drid") Integer drid);
	
	List<Reg> findRegByDrid(@Param(value="dBname")String dBname,@Param("drid")Integer drid,
															@Param("isenergy")String isenergy);

	List<Reg> findRegByListShowLevel(@Param("dBname")String dBname,@Param("drid") Integer drid,String... regListShowLevels);

	String findQstagByName(@Param("dBname")String dBname,@Param("tagname") String tagname);

	List<Reg> findRegById(@Param("dBname")String dBname, Integer[] ids);

	int insertObjectByDrtypemodes(@Param("dBname")String dBname,@Param("drid") Integer drid, List<Drtypemode> drtypemodes);

	int deleteObjectByDrids(@Param("dBname")String dBname, Integer[] drids);

	int getCountByRegDrShowType(@Param("dBname")String dBname,@Param("childidlist")List<Integer> childidlist,@Param("regDrShowType") String regDrShowType,@Param("tagAlarmState")  String tagAlarmState);

	int updateQstagByName(@Param("dBname")String dBname,@Param("tagName")String tagName,@Param("tagTime") String tagTime);

	Reg findRegByid(@Param("dBname")String dBname,@Param("regId") Integer regId);

	String queryTagValue(@Param("dBname")String dBname, @Param("tagName")String tagName);

	Reg findRegByType(@Param("dBname")String dBname,@Param("drid") Integer drid, String regDrShowType);
	
	List<Reg> findRegByShowType(@Param("dBname")String dBname, String regDrShowType);

	List<Reg> findAllByDrtypeid(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);

	int updateRegValue(@Param("dBname")String dBname, @Param("regId")Integer regId, @Param("tagvaule")String tagvaule);

	Integer getCountByShowTypeAndState(@Param("dBname")String dBname, @Param("showType")String showType, @Param("state")String state);

	List<Integer> findRegIdByDrids(@Param("dBname")String dBname, Integer[] drids);

	List<Object> selectListForExcelExport(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid,@Param("startIndex") int startIndex);

	int getExportRowCount(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);

	int getRowCountByRegName(@Param("dBname")String dBname, @Param("regName")String regName, String rw);

	List<Reg> findRegByRegName(@Param("dBname")String dBname, @Param("regName")String regName, String rw, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	int getRowCountByRW(@Param("dBname")String dBname);

	List<Reg> findRegByRW(@Param("dBname")String dBname, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	List<Reg> findShowTypeAllByDrtypeid(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);

	List<Reg> findRegBydrid(@Param("dBname")String dBname, Integer[] ids);

    //List<RegSub> findRegValueByDrid(@Param("dBname")String dBname, @Param("drId") Integer drId);

	List<RegSub> findImageRegSub(@Param("dBname")String dBname, List<Integer> regIds);

	List<Reg> findRegValueBydrids(List<Integer> drIdList);

	List<RegSub> findRegValueByDrId(Integer drId);

    List<Reg> findRegByNameDrids(@Param("dBname")String dBname,@Param("regName") String regName, Integer[] drIds);
}
