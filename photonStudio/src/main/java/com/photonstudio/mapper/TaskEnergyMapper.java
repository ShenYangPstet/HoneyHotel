package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appenergy;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Energyparm;

public interface TaskEnergyMapper {

	List<Appmanager> findAppManagerAll();

	void insertAppenergy(@Param("appenergy")Appenergy appenergy);

	Energyparm findEnergyparm(@Param("dbname")String dbname);

	List<Appenergy> queryAppenergyData(@Param("appids")Integer[] appids);

}
