package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.DrCheckLog;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Qsworkorder;

public interface QsworkorderMapper {

	int getRowCount(@Param("dBname") String dBname, @Param("state") Integer state);

	List<Qsworkorder> findObject(@Param("dBname") String dBname, @Param("state") Integer state,
			@Param("startIndex") int startIndex, @Param("pageSize") Integer pageSize);

	List<Qsworkorder> findObjectByNumber(@Param("dBname") String dBname, @Param("number") String number);

	int delete(@Param("dBname") String dBname, @Param("ids") Integer[] id);

	int save(@Param("dBname") String dBname, Qsworkorder qsworkorder);

	int update(@Param("dBname") String dBname, Qsworkorder qsworkorder);

	List<EcharsObject> findEC(@Param("dBname") String dBname);

	List<Qsworkorder> findObjectByIds(@Param("dBname") String dBname, Integer[] ids);

	List<DrCheckLog> finddrcehcklog(@Param("dBname") String dBname, @Param("checkPerson") String checkPerson,
			@Param("checkDate") Date checkDate, @Param("startIndex") int startIndex,
			@Param("pageSize") Integer pageSize);

	int getDrCheckLogCount(@Param("dBname") String dBname, @Param("checkPerson") String checkPerson,
			@Param("checkDate") Date checkDate);

    List<Qsworkorder> findworklist(@Param("dBname")String dBname, @Param("state")int state, @Param("startDate")Date startDate,
								   @Param("endDate")Date endDate,@Param("username")String username,@Param("workuser") String workuser,
								   @Param("approvestate") Integer approvestate);
}
