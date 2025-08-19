package com.photonstudio.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.photonstudio.common.enums.SendType;
import com.photonstudio.mapper.AppmanagerMapper;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.dto.TemplateInstruction;
import com.photonstudio.pojo.vo.SendVo;
import com.photonstudio.pojo.vo.TypeRegOption;
import com.photonstudio.pojo.vo.TypeRegOption.RegOption;
import com.photonstudio.service.ConsoleService;
import com.photonstudio.service.DrtypeinfoService;
import com.photonstudio.service.DrtypemodeService;
import com.photonstudio.service.SubinfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final RegMapper regMapper;
    private final RestTemplate restTemplate;
    private final AppmanagerMapper appmanagerMapper;
    private final DrtypemodeService drtypemodeService;
    private final DrtypeinfoService drtypeinfoService;
    private final SubinfoService subinfoService;

    @Override
    public void TemplateInstructionSend(String dBname, Integer appId, JSONObject jsonObject) {
        Appmanager appmanager = appmanagerMapper.findObjectByAppid(appId);
        //下发地址拼装
        String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport() + "/caiji/ConsoleSend";
        List<TemplateInstruction> templateInstructions = jsonObject.getJSONArray("drtypemode").toJavaList(TemplateInstruction.class);
        Map<String, String> templateMap = new HashMap<>();
        List<String> regListShowLevels = new ArrayList<>();
        for (TemplateInstruction templateInstruction : templateInstructions) {
            regListShowLevels.add(String.valueOf(templateInstruction.getRegListShowLevel()));
            templateMap.put(String.valueOf(templateInstruction.getRegListShowLevel()), templateInstruction.getSendValue());
        }
        List<Integer> drIds = jsonObject.getJSONArray("drId").toJavaList(Integer.class);
        LambdaQueryWrapper<Reg> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Reg::getDrId, drIds).in(Reg::getRegListShowLevel, regListShowLevels);
        List<Reg> regList = regMapper.selectList(lambdaQueryWrapper);
        //List<SendValue> sendValues=new ArrayList<>();
        Map<String, String> sendValueMap = new HashMap<>();
        for (Reg reg : regList) {
            if (ObjectUtil.isEmpty(reg.getTagName()) || ObjectUtil.isEmpty(reg.getRegListShowLevel())) continue;
            sendValueMap.put(reg.getTagName(), templateMap.get(reg.getRegListShowLevel()));
        }
        SendVo sendVo = new SendVo(SendType.CONSOLE.sendType, sendValueMap);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(sendVo), headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, formEntity, String.class);
        System.out.println(stringResponseEntity.getBody());
        for (TemplateInstruction templateInstruction : templateInstructions) {
            drtypemodeService.lambdaUpdate()
                    .eq(Drtypemode::getDrtypeid, jsonObject.getInteger("drtypeId"))
                    .eq(Drtypemode::getRegListShowLevel, templateInstruction.getRegListShowLevel())
                    .set(Drtypemode::getTagValue, templateInstruction.getSendValue())
                    .update();
        }
    }

    @Override
    public List<TypeRegOption> getConsoleMode(String database) {
        // 查询所有可写的类型模版，转换成类型模版寄存器对象集合
        List<Drtypemode> typeModeList = drtypemodeService.lambdaQuery().eq(Drtypemode::getRegReadWrite, 2).list();
        List<TypeRegOption.TypeReg> typeRegList = typeModeList.stream()
                .map(TypeRegOption.TypeReg::drTypeMode2TypeReg).collect(Collectors.toList());

        Map<Integer, List<RegOption>> subId2RegOptionMap = typeRegs2SubId2RegOptionMap(typeRegList);

        // 给寄存器对象组装上寄存器选项，并转换成Map<设备类型id,寄存器列表>
        Map<Integer, List<TypeRegOption.TypeReg>> typeId2RegMap = typeRegList.stream()
                .map(typeReg -> typeReg.setOptions(subId2RegOptionMap.get(typeReg.getSubId())))
                .collect(Collectors.groupingBy(TypeRegOption.TypeReg::getTypeId));

        // 查询所有可写的类型模版设备类型，给设备类型组装上寄存器列表
        List<Integer> typeIdList = typeRegList.stream().map(TypeRegOption.TypeReg::getTypeId).distinct().collect(Collectors.toList());
        List<Drtypeinfo> drTypeInfoList = drtypeinfoService.findAllStatusVos(database, typeIdList);
        return drTypeInfoList.stream()
                .map(TypeRegOption::drTypeInfo2TypeTree)
                .map(typeRegOption -> typeRegOption.setTypeRegs(typeId2RegMap.get(typeRegOption.getTypeId())))
                .collect(Collectors.toList());
    }

    /**
     * 获取类型模版寄存器集合对应的所有寄存器子项，转换成Map<寄存器选项id,寄存器选项列表>
     *
     * @param typeRegList 类型模版寄存器集合
     * @return Map<寄存器选项id, 寄存器选项列表>
     */
    private Map<Integer, List<RegOption>> typeRegs2SubId2RegOptionMap(List<TypeRegOption.TypeReg> typeRegList) {
        Set<Integer> subIdList = typeRegList.stream().map(TypeRegOption.TypeReg::getSubId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(subIdList)) {
            return MapUtil.empty();
        } else {
            return subinfoService.lambdaQuery().in(Subinfo::getSubid, subIdList).list().stream()
                    .map(RegOption::subInfo2RegOption)
                    .collect(Collectors.groupingBy(RegOption::getSubId));
        }
    }
}
