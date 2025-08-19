package com.photonstudio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photonstudio.pojo.MasterTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MasterTemplateMapper extends BaseMapper<MasterTemplate> {

    @Select(" select * from zsqy_v2.master_template where id=#{id} ")
    MasterTemplate findBYid(@Param("id")Integer id);

}
