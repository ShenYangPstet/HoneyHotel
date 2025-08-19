package com.photonstudio.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photonstudio.pojo.Drinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrinfoMapper extends BaseMapper<Drinfo> {

	int getRowCount(@Param("dBname")String dBname,
					@Param("drtypeid") Integer drtypeid, 
					@Param("mdcode") String mdcode,
					@Param("userid")Integer userid, List<Integer> dridlist,
					@Param("drname") String drname);

	List<Drinfo> findObject(@Param("dBname")String dBname, 
							@Param("drtypeid")Integer drtypeid, 
							@Param("mdcode")String mdcode, 
							@Param("userid")Integer userid,
							List<Integer> dridlist, @Param("startIndex")Integer startIndex, 
							@Param("pageSize")Integer pageSize,
							@Param("floorId") Integer floorId,@Param("drname") String drname);

	int insertObject(@Param("dBname")String dBname, Drinfo drinfo);

	int deleteObjectById(@Param("dBname")String dBname,Integer...ids);

	int updateObject(@Param("dBname")String dBname, Drinfo drinfo);

	List<Drinfo> findAll(@Param("dBname")String dBname);

	int findObjectById(@Param("dBname")String dBname,@Param("drid") Integer drid);

	int updateUser(@Param("dBname")String dBname, @Param("userid")Integer userid, Integer... ids);

	List<Drinfo> findAllIsUser(@Param("dBname")String dBname,@Param("drtypeid") Integer drtypeid);

	Drinfo findDrByDrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	String querySpid(@Param("dBname")String dBname, @Param("rdid")String rdid);

	List<Drinfo> findObjectByIds(@Param("dBname") String dBname,@Param("drname") String drname, Integer[] ids);

	int updateRegname(@Param("dBname")String dBname, @Param("drname")String drname,@Param("drid") Integer drid);

	List<Integer> findDridByUserid(@Param("dBname")String dBname, @Param("userid")Integer userid);

	int removeUser(@Param("dBname")String dBname,@Param("userid") Integer userid, Integer[] ids);

	int findCountByUseridDrid(@Param("dBname")String dBname,@Param("userid") Integer userid,@Param("drid") Integer drid);


    List<JSONObject> findJSONobjectByIds(@Param("dBname")String dBname,@Param("drname")String drname, Integer[] drIds);

    List<JSONObject> getIconTypeByDrIds(@Param("staticAccessPath")String staticAccessPath, List<Integer> drIdList);
}
