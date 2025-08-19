package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.mapper.DrinfoMapper;
import com.photonstudio.mapper.DrtypeinfoMapper;
import com.photonstudio.mapper.PageStyleMapper;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.FileUploadProperteis;
import com.photonstudio.pojo.PageStyle;
import com.photonstudio.pojo.Reg;
import com.photonstudio.service.PageStyleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 页面样式表service实现类
 *
 * @author bingo
 * @date 2022/5/7 15:14
 */
@Service
public class PageStyleServiceImpl extends ServiceImpl<PageStyleMapper, PageStyle> implements PageStyleService {
    @Autowired
    private DrinfoMapper drinfoMapper;
    @Autowired
    private RegMapper regMapper;
    @Autowired
    private FileUploadProperteis fileUploadProperteis;

    @Autowired
    private DrtypeinfoMapper drtypeinfoMapper;

    @Autowired
    private PageStyleService pageStyleService;

    @Override
    public PageStyle getByPicId(Integer picId) {
        return lambdaQuery().eq(PageStyle::getPicId, picId).one();
    }

    @Override
    public void deleteByPicId(Integer picId) {
        lambdaUpdate().eq(PageStyle::getPicId, picId).remove();
    }

    @Override
    public void insertOrUpdate(PageStyle pageStyle) {
        PageStyle dbPageStyle = getByPicId(pageStyle.getPicId());
        if (dbPageStyle == null) {
            save(pageStyle);
        } else {
            lambdaUpdate().eq(PageStyle::getPicId, pageStyle.getPicId()).update(pageStyle);
        }
    }

    @Override
    public List<Drinfo> getdrStartByPicId(Integer picId) {
        List<Integer> drIdList = getDrIdListByType(picId, null);
        if (drIdList == null || drIdList.isEmpty()) return null;
        List<Drinfo> drinfoList = drinfoMapper.selectBatchIds(drIdList);
        List<Reg> regList = regMapper.findRegValueBydrids(drIdList);
        for (Drinfo drinfo : drinfoList) {
            int drid = drinfo.getDrid();
            int runStateCnt = 0;
            int alarmStateCnt = 0;
            int errorStateCnt = 0;
            for (Reg reg : regList) {
//				String newvalue=piceditMapper.querytagvalue(dBname,reg.getTagName());
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("1")) {
                    if (null != reg.getTagName() && !reg.getTagName().equals("")) {
                        String value = reg.getNewtagvalue();
                        if (value != null && value.equals("1")) {
                            runStateCnt = 1;
                        }
                    } else {
                        if (reg.getTagValue().equals("1")) {
                            runStateCnt = 1;
                        }
                    }
                }
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("2")) {
                    if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                        alarmStateCnt = 2;
                    }
                }
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("3")) {
                    if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                        errorStateCnt = 4;
                    }
                }
            }
            int state = runStateCnt + alarmStateCnt + errorStateCnt;
            drinfo.setState(state);
        }
        return drinfoList;
    }

    /**
     * 通过页面ID获取页面设备ID集合
     *
     * @param picId
     * @return
     */
    @Override
    public List<Integer> getDrIdListByType(Integer picId, Integer drtypeId) {
        PageStyle pageStyle = lambdaQuery().eq(PageStyle::getPicId, picId).one();
        List<Integer> drIdList = new ArrayList<>();
        if (pageStyle == null || ObjectUtils.isEmpty(pageStyle)) return drIdList;
        List<JSONObject> list = JSONArray.parseArray(pageStyle.getCanvasData(), JSONObject.class);
        for (JSONObject jsonObject : list) {
            if (jsonObject.getString("component").equals("Device")) {
                if (drtypeId == null || drtypeId < 1) {
                    if (!ObjectUtil.isEmpty(jsonObject.getJSONObject("drInfo"))) {
                        Integer drid = jsonObject.getJSONObject("drInfo").getInteger("drid");
                        drIdList.add(drid);
                    }
                } else {
                    if (jsonObject.getJSONObject("drInfo").getInteger("drtypeid").equals(drtypeId))
                        drIdList.add(jsonObject.getJSONObject("drInfo").getInteger("drid"));
                }
            }
        }
        return drIdList;
    }


    @Override
    public List<JSONObject> getIconTypeByPicId(Integer picId) {
        List<Integer> drIdList = getDrIdListByType(picId, null);
        if (drIdList == null || drIdList.isEmpty()) return null;
        return drinfoMapper.getIconTypeByDrIds(fileUploadProperteis.getStaticAccessPath(), drIdList);
    }

    @Override
    public void updatePageStyleByDeviceId(String database, Integer deviceId) {
        List<Drinfo> devices = drtypeinfoMapper.findDrinfoByDrtypeid(database, null, fileUploadProperteis.getStaticAccessPath(), null, deviceId);
        if (CollectionUtils.isEmpty(devices)) {
            return;
        }
        Drinfo device = devices.get(0);
        List<PageStyle> pageStyleList = lambdaQuery().list();
        pageStyleList.forEach(pageStyle -> {
            JSONArray jsonArray = JSON.parseArray(pageStyle.getCanvasData());
            JSONArray objects = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString("component").equals("Device")) {
                    JSONObject drinfo = object.getJSONObject("drInfo");
                    if (Objects.nonNull(drinfo)) {
                        Integer drid = drinfo.getInteger("drid");
                        if (device.getDrid().equals(drid)) {
                            object.replace("drInfo", device);
                        }
                    }
                }
                objects.add(object);
            }
            pageStyle.setCanvasData(JSON.toJSONString(objects));
        });
        updateBatchById(pageStyleList);
    }

    @Override
    public void updatePageStyleAllDeviceInfo(String database) {
        List<Drinfo> devices = drtypeinfoMapper.findDrinfoByDrtypeid(database, null, fileUploadProperteis.getStaticAccessPath(), null, null);
        Map<Integer, Drinfo> idToDevice = devices.stream().collect(Collectors.toMap(Drinfo::getDrid, Function.identity()));
        List<PageStyle> pageStyleList = lambdaQuery().list();
        pageStyleList.forEach(pageStyle -> {
            JSONArray jsonArray = JSON.parseArray(pageStyle.getCanvasData());
            JSONArray objects = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString("component").equals("Device")) {
                    JSONObject drinfo = object.getJSONObject("drInfo");
                    if (Objects.nonNull(drinfo)) {
                        Integer drid = drinfo.getInteger("drid");
                        Drinfo device = idToDevice.get(drid);
                        if (Objects.nonNull(device)) {
                            object.replace("drInfo", device);
                        } else {
                            object.remove("drInfo");
                        }
                    }
                }
                objects.add(object);
            }
            pageStyle.setCanvasData(JSON.toJSONString(objects));
        });
        updateBatchById(pageStyleList);
    }

    @Override
    public void deletePageStle(String drid) {
        List<PageStyle> pageStyleList = lambdaQuery().list();
        pageStyleList.forEach(pageStyle -> {
            JSONArray jsonArray = JSON.parseArray(pageStyle.getCanvasData());
            JSONArray objects = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (object.getString("component").equals("Device")) {
                    JSONObject drinfo = object.getJSONObject("drInfo");
                    if (!StringUtils.isEmpty(drinfo)) {
                        if (drid.equals(drinfo.getString("drid")) && !StringUtils.isEmpty(drid)) {
                            object.remove("drInfo");
                            Integer id = pageStyle.getId();
                            pageStyle.setCanvasData(JSON.toJSONString(jsonArray));
                            lambdaUpdate().eq(PageStyle::getId, id).update(pageStyle);
                        }
                    }
                }
                objects.add(object);
            }
        });

    }

}
