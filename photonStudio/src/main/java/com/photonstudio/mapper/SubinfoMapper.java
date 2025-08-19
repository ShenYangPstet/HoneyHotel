package com.photonstudio.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Subinfo;
import org.apache.ibatis.annotations.Select;

public interface SubinfoMapper extends BaseMapper<Subinfo> {

	int getRowCount(@Param("dBname")String dBname,@Param("subid") Integer subid);

	List<Subinfo> findObject(@Param("dBname")String dBname,
							 @Param("subid")Integer subid, 
							 @Param("startIndex")Integer startIndex, 
							 @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Subinfo subinfo);

	int updateObject(@Param("dBname")String dBname, Subinfo subinfo);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);
	@Select("select a.*,b.subname,c.imgurl from subinfo a left join sub b on a.subid=b.subid LEFT JOIN images c ON a.url = c.imgid")
	List<Subinfo> findAll();
}
