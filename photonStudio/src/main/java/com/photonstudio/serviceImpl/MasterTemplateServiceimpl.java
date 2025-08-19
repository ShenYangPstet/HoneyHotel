package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.photonstudio.common.vo.MasterTemplateVo;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.ParameterVo;
import com.photonstudio.mapper.AppsysuserMapper;
import com.photonstudio.mapper.MasterTemplateMapper;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.MasterTemplate;
import com.photonstudio.pojo.Usertoken;
import com.photonstudio.pojo.vo.ParaVo;
import com.photonstudio.service.MasterTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.List;

@Service
public class MasterTemplateServiceimpl extends ServiceImpl<MasterTemplateMapper, MasterTemplate> implements MasterTemplateService {

    @Resource
    private MasterTemplateMapper masterTemplateMapper;
    @Resource
    private AppsysuserMapper appsysuserMapper;


    @Transient
    @Override
    public int saveObject(MasterTemplateVo templateVO, HttpServletRequest request) {

        //获取用户信息
        String tokenh = request.getHeader("ZSQY_TEST");
        Usertoken usertoken = appsysuserMapper.findUsertokenByToken(tokenh);
        Appsysuser user = appsysuserMapper.findAppsysuserByUsername(usertoken.getUsername());
        MasterTemplate masterTemplate = new MasterTemplate()
                .setPageName(templateVO.getPageName())
                .setTemplateType(templateVO.getTemplateType())
                .setImgurl(templateVO.getImgurl())
                .setRemark(templateVO.getRemark())
                .setRelease(templateVO.getRelease())
                .setCanvasStyleData(templateVO.getCanvasStyleData())
                .setComponentData(templateVO.getComponentData())
                .setStatus(templateVO.getStatus())
                .setTitle(templateVO.getTitle())
                .setTheme(templateVO.getTheme())
                .setAddUser(user.getUsername());
        return masterTemplateMapper.insert(masterTemplate);
    }

    @Transient
    @Override
    public int saveObjectMode(Integer id) {
        MasterTemplate masterTemplate = masterTemplateMapper.findBYid(id);
        Integer count = Math.toIntExact(lambdaQuery().eq(MasterTemplate::getRelease, "1").eq(MasterTemplate::getStatus, "1").count());
        if (count >= 1) {
            return 0;
        } else {
            return masterTemplateMapper.insert(masterTemplate);
        }
    }

    @Override
    public PageObject<MasterTemplate> findPage(ParameterVo parameter) {
        LambdaQueryWrapper<MasterTemplate> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(!ObjectUtil.isEmpty(parameter.getUsername()), MasterTemplate::getAddUser, parameter.getUsername())
                .like(!ObjectUtil.isEmpty(parameter.getPageName()), MasterTemplate::getPageName, parameter.getPageName())
                .eq(!ObjectUtil.isEmpty(parameter.getStatus()), MasterTemplate::getStatus, parameter.getStatus()).
                eq(!ObjectUtil.isEmpty(parameter.getRelease()), MasterTemplate::getRelease, parameter.getRelease())
                .orderByDesc(MasterTemplate::getAddTime);
        lambdaQueryWrapper.in(MasterTemplate::getStatus, "0", "1");
        PageHelper.startPage(parameter.getPageSum(), parameter.getPageSize());
        List<MasterTemplate> list = masterTemplateMapper.selectList(lambdaQueryWrapper);
        PageInfo<MasterTemplate> pageinfo = new PageInfo<>(list);
        PageObject<MasterTemplate> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageinfo.getPageNum()).setPageSize(pageinfo.getPageSize()).
                setPageCount(pageinfo.getPages()).setRecords(pageinfo.getList()).setRowCount(Math.toIntExact(pageinfo.getTotal()));
        return pageObject;
    }

    @Override
    public int updateObject(MasterTemplateVo templateVO, HttpServletRequest request) {
        String tokenh = request.getHeader("ZSQY_TEST");
        Usertoken usertoken = appsysuserMapper.findUsertokenByToken(tokenh);
        if (ObjectUtil.isEmpty(usertoken.getUsername())) {
            throw new RuntimeException("违规操作~,获取不到用户信息!!!!");
        }
        Appsysuser user = appsysuserMapper.findAppsysuserByUsername(usertoken.getUsername());
        MasterTemplate masterTemplate = new MasterTemplate()
                .setPageName(templateVO.getPageName())
                .setTemplateType(templateVO.getTemplateType())
                .setImgurl(templateVO.getImgurl())
                .setRemark(templateVO.getRemark())
                .setRelease(templateVO.getRelease())
                .setCanvasStyleData(templateVO.getCanvasStyleData())
                .setComponentData(templateVO.getComponentData())
                .setStatus(templateVO.getStatus())
                .setTitle(templateVO.getTitle())
                .setTheme(templateVO.getTheme())
                .setId(templateVO.getId())
                .setUpdateUser(user.getUsername());
        if (!request.getHeader("database").equals("zsqy_v2") && templateVO.getRelease() == 1) {
            Integer count = Math.toIntExact(lambdaQuery().eq(MasterTemplate::getRelease, 1).eq(MasterTemplate::getStatus, 1).count());
            if (count >= 1) {
                return 0;
            }
//            return  masterTemplateMapper.updateById(masterTemplate);
        }
        return masterTemplateMapper.updateById(masterTemplate);
    }

    @Transient
    @Override
    public void deleteObject(ParaVo paraV) {
        MasterTemplate masterTemplate = new MasterTemplate().setStatus(paraV.getStatus()).setId(paraV.getId());
        masterTemplateMapper.updateById(masterTemplate);
    }
}
