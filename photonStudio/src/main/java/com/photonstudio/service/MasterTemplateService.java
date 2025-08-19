package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.MasterTemplateVo;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.ParameterVo;
import com.photonstudio.pojo.MasterTemplate;
import com.photonstudio.pojo.vo.ParaVo;

import javax.servlet.http.HttpServletRequest;

public interface MasterTemplateService extends IService<MasterTemplate> {

    //新增模板
    int saveObject(MasterTemplateVo templateVO, HttpServletRequest request);

    //将模板更新到项目库里面
    int saveObjectMode(Integer id);

    PageObject<MasterTemplate> findPage(ParameterVo parameter);

   int updateObject(MasterTemplateVo templateVO, HttpServletRequest request);

   void deleteObject(ParaVo paraVo);

}
