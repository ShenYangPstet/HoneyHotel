package com.photonstudio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Reg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrtypemodeMapper extends BaseMapper<Drtypemode> {

    int getRowCount(@Param("dBname") String dBname, Integer drtypeid);

    List<Drtypemode> findObject(@Param("dBname") String dBname,
                                @Param("drtypeid") Integer drtypeid,
                                @Param("startIndex") Integer startIndex,
                                @Param("pageSize") Integer pageSize);

    int insertObject(@Param("dBname") String dBname, Drtypemode drtypemode);

    int updateObject(@Param("dBname") String dBname, Drtypemode drtypemode);

    int deleteObjectById(@Param("dBname") String dBname, Integer... ids);

    int findRowCountById(@Param("dBname") String dBname, Integer typemodeid);

    int updateRegObject(@Param("dBname") String dBname, Drtypemode drtypemode);

    int queryCntDrinfo(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid);

    void insertReg(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid, @Param("regListShowLevel") Integer regListShowLevel);

    Drtypemode queryDrtypemodeByid(@Param("dBname") String dBname, @Param("id") Integer id);

    void deleteJgtMode(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid, @Param("regListShowLevel") String regListShowLevel);

    void deleteDrtypemodeById(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid);

    void deleteReg(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid, @Param("regListShowLevel") String regListShowLevel);

    List<Drtypemode> findAllWriteByDrtypeid(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid);

    List<Reg> findAllRegByLevel(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid, @Param("regShowlevel") String regShowlevel);

    void updateTypemodeValue(@Param("dBname") String dBname, @Param("drtypeid") Integer drtypeid, @Param("regShowlevel") Integer regShowlevel, @Param("value") String value);

}
