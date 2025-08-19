package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.vo.RegSub;
import com.photonstudio.mapper.DrinfoMapper;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.mapper.StructureDiagramStyleMapper;
import com.photonstudio.mapper.SubinfoMapper;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.StructureDiagramStyle;
import com.photonstudio.pojo.Subinfo;
import com.photonstudio.service.StructureDiagramStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 结构图样式表service实现类
 *
 * @author hanxi
 * @date 2022/5/7 15:25
 */
@Service
@RequiredArgsConstructor
public class StructureDiagramStyleServiceImpl extends ServiceImpl<StructureDiagramStyleMapper, StructureDiagramStyle> implements StructureDiagramStyleService {
    private final DrinfoMapper drinfoMapper;
    private final RegMapper regMapper;
    private final SubinfoMapper subinfoMapper;

    @Override
    public StructureDiagramStyle getByDrtypeId(Integer drtypeId) {
        return lambdaQuery().eq(StructureDiagramStyle::getDrtypeId, drtypeId).one();
    }

    @Override
    public void insertOrUpdate(StructureDiagramStyle structureDiagramStyle) {
        StructureDiagramStyle dbStructureDiagramStyle = getByDrtypeId(structureDiagramStyle.getDrtypeId());
        if (dbStructureDiagramStyle == null) {
            save(structureDiagramStyle);
        } else {
            lambdaUpdate().eq(StructureDiagramStyle::getDrtypeId, structureDiagramStyle.getDrtypeId())
                    .update(structureDiagramStyle);
        }
    }

    @Override
    public void deleteByPicId(Integer drtypeId) {
        lambdaUpdate().eq(StructureDiagramStyle::getDrtypeId, drtypeId).remove();
    }

    @Override
    public List<JSONObject> findJgtSubinfoByDrId(Integer drId) {
        List<JSONObject> JgtSubinfoList = new ArrayList<>();
        Map<Integer, List<Subinfo>> subMap = new HashMap<>();
        List<Subinfo> subinfos = subinfoMapper.findAll();
        for (Subinfo subinfo : subinfos) {
            if (subMap.get(subinfo.getSubid()) == null) subMap.put(subinfo.getSubid(), new ArrayList<>());
            subMap.get(subinfo.getSubid()).add(subinfo);
        }
        Drinfo drinfo = drinfoMapper.selectById(drId);
        StructureDiagramStyle structureDiagramStyle = lambdaQuery()
                .eq(StructureDiagramStyle::getDrtypeId, drinfo.getDrtypeid()).one();
        List<JSONObject> list = JSONArray.parseArray(structureDiagramStyle.getCanvasData(), JSONObject.class);
        //根据设备ID查询结构图设备变量真实值
        List<RegSub> regSubList = regMapper.findRegValueByDrId(drId);
        //循环结构图元素
        for (JSONObject jsonObject : list) {
            //找到不为设备的元素
            if (!jsonObject.getString("component").equals("Device")) {
                JSONObject dynamic = null;
                try {
                    dynamic = jsonObject.getJSONObject("dynamic");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //判断是否绑定了变量
                if (ObjectUtil.isNotNull(dynamic)) {
                    if (ObjectUtil.isNotNull(dynamic.getInteger("subid")) &&
                            ObjectUtil.isNotNull(dynamic.getInteger("regListShowLevel"))) {
                        for (RegSub regSub : regSubList) {
                            if (Integer.valueOf(regSub.getRegListShowLevel()).equals(dynamic.getInteger("regListShowLevel"))) {
                                String regValue = regSub.getTagValue();
                                if (regSub.getNewtagvalue() != null && regSub.getNewtagvalue() != "") {
                                    regValue = regSub.getNewtagvalue();
                                }
                                List<Subinfo> subinfoList = subMap.get(dynamic.getInteger("subid"));
                                for (Subinfo subinfosByJgt : subinfoList) {
                                    if (subinfosByJgt.getValueType().equals("1")) {
                                        if (subinfosByJgt.getValue().equals(regValue)) {
                                            JSONObject jgtSubinfo = new JSONObject();
                                            jgtSubinfo.put("id", jsonObject.getString("id"));
                                            jgtSubinfo.put("subinfo", subinfosByJgt);
                                            JgtSubinfoList.add(jgtSubinfo);
                                        }
                                    } else {
                                        Float regValueF = Float.valueOf(regValue);
                                        if (subinfosByJgt.getAndOr().equals("1")) {
                                            if (Float.valueOf(subinfosByJgt.getValueMin()) <= regValueF
                                                    || Float.valueOf(subinfosByJgt.getValueMax()) >= regValueF) {
                                                JSONObject jgtSubinfo = new JSONObject();
                                                jgtSubinfo.put("id", jsonObject.getString("id"));
                                                jgtSubinfo.put("subinfo", subinfosByJgt);
                                                JgtSubinfoList.add(jgtSubinfo);
                                            }
                                        } else {
                                            if (Float.valueOf(subinfosByJgt.getValueMin()) > regValueF
                                                    || Float.valueOf(subinfosByJgt.getValueMax()) < regValueF) {
                                                JSONObject jgtSubinfo = new JSONObject();
                                                jgtSubinfo.put("id", jsonObject.getString("id"));
                                                jgtSubinfo.put("subinfo", subinfosByJgt);
                                                JgtSubinfoList.add(jgtSubinfo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return JgtSubinfoList;
    }

    @Override
    public Object getByDrId(Integer drId) {
        Map<Integer, List<Subinfo>> subMap = new HashMap<>();
        List<Subinfo> subinfos = subinfoMapper.findAll();
        for (Subinfo subinfo : subinfos) {
            if (subMap.get(subinfo.getSubid()) == null) subMap.put(subinfo.getSubid(), new ArrayList<>());
            subMap.get(subinfo.getSubid()).add(subinfo);
        }
        Drinfo drinfo = drinfoMapper.selectById(drId);
        //System.out.println("设备Id=="+drId+"===设备=="+drinfo);
        StructureDiagramStyle structureDiagramStyle = lambdaQuery()
                .eq(StructureDiagramStyle::getDrtypeId, drinfo.getDrtypeid()).one();
        if(ObjectUtil.isEmpty(structureDiagramStyle))return null;
        List<JSONObject> list = JSONArray.parseArray(structureDiagramStyle.getCanvasData(), JSONObject.class);
        //循环结构图元素
        //根据设备ID查询结构图设备变量真实值
        List<RegSub> regSubList = regMapper.findRegValueByDrId(drId);
        for (JSONObject jsonObject : list) {
            //找到设备模板的元素
            if (jsonObject.getString("component").equals("Devicetype")) {
                JSONObject drtypeModeInfo = null;
                try {
                    drtypeModeInfo = jsonObject.getJSONObject("drtypeInfo");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ObjectUtil.isNotNull(drtypeModeInfo)) {
                    for (RegSub regSub : regSubList) {
                        if (Integer.valueOf(regSub.getRegListShowLevel()).equals(drtypeModeInfo.getInteger("regListShowLevel"))) {
                            String regValue = regSub.getTagValue();
                            if (!ObjectUtil.isEmpty(regSub.getNewtagvalue())) {
                                regValue = regSub.getNewtagvalue();
                            }
                            drtypeModeInfo.put("tagValue", regValue);
                            drtypeModeInfo.put("regId", regSub.getRegId());
                            drtypeModeInfo.put("tagName", regSub.getTagName());
                            Integer regSubId=null;
                            try {
                                regSubId=Integer.valueOf(drtypeModeInfo.getString("regSub"));
                            }catch (Exception e){

                            }
                            if (regSubId!=null) {
                                List<Subinfo> subinfoList = subMap.get(Integer.valueOf(drtypeModeInfo.getString("regSub")));
                                if (!ObjectUtil.isEmpty(subinfoList)) drtypeModeInfo.put("subinfoList", subinfoList);
                            }
                            //System.out.println("插入模板对象==" + drtypeModeInfo);
                            jsonObject.put("drtypeInfo", drtypeModeInfo);
                        }
                    }
                }
            } else {
                JSONObject dynamic = null;
                try {
                    dynamic = jsonObject.getJSONObject("dynamic");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //判断是否绑定了变量
                if (ObjectUtil.isNotNull(dynamic)) {
                    if (ObjectUtil.isNotNull(dynamic.getInteger("subid")) &&
                            ObjectUtil.isNotNull(dynamic.getInteger("regListShowLevel"))) {
                        for (RegSub regSub : regSubList) {
                            if (Integer.valueOf(regSub.getRegListShowLevel())
                                    .equals(dynamic.getInteger("regListShowLevel"))) {
                                String regValue = regSub.getTagValue();
                                if (regSub.getNewtagvalue() != null && regSub.getNewtagvalue() != "") {
                                    regValue = regSub.getNewtagvalue();
                                }
                                List<Subinfo> subinfoList = subMap.get(dynamic.getInteger("subid"));
                                for (Subinfo subinfosByJgt : subinfoList) {
                                    if (subinfosByJgt.getValueType().equals("1")) {
                                        if (subinfosByJgt.getValue().equals(regValue)) {
                                            if (jsonObject.getString("component").equals("Picture") && !ObjectUtil.isEmpty(subinfosByJgt.getImgurl())) {
                                                jsonObject.put("propValue", subinfosByJgt.getImgurl());
                                            } else if (jsonObject.getString("component").equals("v-text") && !ObjectUtil.isEmpty(subinfosByJgt.getText())) {
                                                jsonObject.put("propValue", subinfosByJgt.getText());
                                            }
                                        }
                                    } else {
                                        Float regValueF = Float.valueOf(regValue);
                                        if (subinfosByJgt.getAndOr().equals("1")) {
                                            if (Float.valueOf(subinfosByJgt.getValueMin()) <= regValueF
                                                    || Float.valueOf(subinfosByJgt.getValueMax()) >= regValueF) {
                                                if (jsonObject.getString("component").equals("Picture") && !ObjectUtil.isEmpty(subinfosByJgt.getImgurl())) {
                                                    jsonObject.put("propValue", subinfosByJgt.getImgurl());
                                                } else if (jsonObject.getString("component").equals("v-text") && !ObjectUtil.isEmpty(subinfosByJgt.getText())) {
                                                    jsonObject.put("propValue", subinfosByJgt.getText());
                                                }
                                            }
                                        } else {
                                            if (Float.valueOf(subinfosByJgt.getValueMin()) > regValueF
                                                    || Float.valueOf(subinfosByJgt.getValueMax()) < regValueF) {
                                                if (jsonObject.getString("component").equals("Picture") && !ObjectUtil.isEmpty(subinfosByJgt.getImgurl())) {
                                                    jsonObject.put("propValue", subinfosByJgt.getImgurl());
                                                } else if (jsonObject.getString("component").equals("v-text") && !ObjectUtil.isEmpty(subinfosByJgt.getText())) {
                                                    jsonObject.put("propValue", subinfosByJgt.getText());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        structureDiagramStyle.setCanvasData(JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
        return structureDiagramStyle;
    }

    @Override
    public JSONObject getRealTimeData(JSONObject jsonObject) {
        Integer drId=jsonObject.getInteger("drId");
        List<JSONObject> elementList = jsonObject.getJSONArray("drTypeInfoList").toJavaList(JSONObject.class);
        //根据设备ID查询结构图设备变量真实值
        List<RegSub> regSubList = regMapper.findRegValueByDrId(drId);
        List<Subinfo> subinfoList=subinfoMapper.findAll();
        Map<String,RegSub> regMap=regSubList.stream().collect(Collectors.toMap(RegSub::getRegListShowLevel, Function.identity(),(ke1,ke2)->ke1));
        Map<Integer,List<Subinfo>> subMap=subinfoList.stream().collect(Collectors.groupingBy(Subinfo::getSubid));
        for (JSONObject elementObj : elementList) {
            if(elementObj==null||elementObj.isEmpty())continue;
            RegSub regSub=regMap.get(String.valueOf(elementObj.getInteger("regListShowLevel")));
            if (ObjectUtil.isNotEmpty(regSub)) {
                String regValue = regSub.getTagValue();
                if (!ObjectUtil.isEmpty(regSub.getNewtagvalue())) {
                    regValue = regSub.getNewtagvalue();
                }
                elementObj.put("tagValue", regValue);
                elementObj.put("tagName", regSub.getTagName());
                Integer regSubId=null;
                try {
                    regSubId=Integer.valueOf(elementObj.getString("regSub"));
                }catch (Exception e){

                }
                if (regSubId!=null) {
                    List<Subinfo> regSubinfoList = subMap.get(regSubId);
                    if (!ObjectUtil.isEmpty(regSubinfoList)) elementObj.put("subinfoList", regSubinfoList);
                }
                //System.out.println("插入模板对象==" + drtypeModeInfo);
            }
        }
        jsonObject.put("drTypeInfoList",elementList);
        return jsonObject;
    }
}
