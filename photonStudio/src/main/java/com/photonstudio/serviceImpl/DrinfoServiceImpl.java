package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.SessionUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.RegSub;
import com.photonstudio.dataupload.req.DeviceReq;
import com.photonstudio.dataupload.vo.DeviceVO;
import com.photonstudio.mapper.*;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.req.DeviceListDataReq;
import com.photonstudio.pojo.vo.DeviceListDataVO;
import com.photonstudio.service.DrinfoService;
import com.photonstudio.service.PageStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinfoServiceImpl implements DrinfoService {
  @Autowired private DrinfoMapper drinfoMapper;
  @Autowired private DrtypemodeMapper drtypemodeMapper;
  @Autowired private RegMapper regMapper;
  @Autowired private SubinfoMapper subinfoMapper;
  @Autowired private PiceditMapper piceditMapper;
  @Autowired private RegalarminfoMapper regalarminfoMapper;
  @Autowired private DrtypeinfoMapper drtypeinfoMapper;
  @Autowired private BuildinfoMapper buildinfoMapper;

  @Autowired private QstagMapper qstagMapper;

  @Autowired private PageStyleService pageStyleService;

  @Override
  public PageObject<Drinfo> findObject(
      String dBname,
      Integer drtypeid,
      String mdcode,
      Integer userid,
      Integer pageCurrent,
      Integer pageSize,
      String drname) {
    if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
    if (pageSize == null || pageSize < 5) pageSize = 5;
    List<Drinfo> list = new ArrayList<>();
    Appuser appuser = SessionUtil.getAppuser(dBname);
    List<Integer> dridlist = new ArrayList<>();
    if (appuser != null
        && appuser.getUsertype() == 1
        && appuser.getDridlist() != null
        && !appuser.getDridlist().isEmpty()) {
      dridlist = appuser.getDridlist();
    }
    int rowCount = drinfoMapper.getRowCount(dBname, drtypeid, mdcode, userid, dridlist, drname);
    int startIndex = (pageCurrent - 1) * pageSize;
    list =
        drinfoMapper.findObject(
            dBname, drtypeid, mdcode, userid, dridlist, startIndex, pageSize, null, drname);
    return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
  }

  @Transactional
  @Override
  public int saveObject(String dBname, Drinfo drinfo) {
    if (drinfo.getDrtypeid() == null || drinfo.getDrtypeid() < 1)
      throw new IllegalArgumentException("请选择设备类型");
    int rows = 0;
    try {
      rows = drinfoMapper.insertObject(dBname, drinfo);
      List<Drtypemode> list = drtypemodeMapper.findObject(dBname, drinfo.getDrtypeid(), null, null);
      if (list != null && list.size() != 0) {
        for (Drtypemode drtypemode : list) {
          String regname = drinfo.getDrname() + ":" + drtypemode.getRegName();
          // System.out.println("测试regname====="+regname);
          drtypemode.setRegName(regname);
        }
        int rowss = regMapper.insertObjectByDrtypemodes(dBname, drinfo.getDrid(), list);
        // System.out.println("drid===="+drinfo.getDrid());
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ServiceException("保存失败");
    }
    return rows;
  }

  @Override
  public int deleteObjectById(String dBname, Integer... ids) {
    if (ids == null || ids.length == 0) throw new ServiceException("请选择");
    int rows = 0;
    try {
      List<Integer> regIdlist = new ArrayList<>();
      regIdlist = regMapper.findRegIdByDrids(dBname, ids);
      if (regIdlist != null && regIdlist.size() > 0)
        regalarminfoMapper.deleteObjectByRegIdlist(dBname, regIdlist);
      regMapper.deleteObjectByDrids(dBname, ids);
      piceditMapper.deletePicEditByDrid(dBname, ids);
      rows = drinfoMapper.deleteObjectById(dBname, ids);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServiceException("删除失败");
    }
    return rows;
  }

  @Override
  public int updateObject(String dBname, Drinfo drinfo) {
    int rows = 0;
    try {
      rows = drinfoMapper.updateObject(dBname, drinfo);
      drinfoMapper.updateRegname(dBname, drinfo.getDrname(), drinfo.getDrid());
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServiceException("更新失败");
    }
    return rows;
  }

  @Override
  public List<Drinfo> findAll(String dBname, Integer drtypeid, Integer userid, Integer floorId) {
    Appuser appuser = SessionUtil.getAppuser(dBname);
    List<Integer> dridlist = new ArrayList<>();
    if (appuser != null
        && appuser.getUsertype() == 1
        && appuser.getDridlist() != null
        && !appuser.getDridlist().isEmpty()) {
      dridlist = appuser.getDridlist();
    }
    List<Drinfo> list =
        drinfoMapper.findObject(
            dBname, drtypeid, null, userid, dridlist, null, null, floorId, null);
    return list;
  }

  @Override
  public int saveObjects(String dBname, List<Drinfo> list) {

    return 0;
  }

  @Override
  @Transactional
  public void importObjects(String dBname, List<Drinfo> list) {
    int rows = 0;
    for (Drinfo drinfo : list) {
      if (drinfo.getDrid() == null || drinfo.getDrid() < 1) {
        drinfoMapper.insertObject(dBname, drinfo);

      } else {
        rows = drinfoMapper.findObjectById(dBname, drinfo.getDrid());
        if (rows == 1) {
          drinfoMapper.updateObject(dBname, drinfo);
        } else {
          drinfoMapper.insertObject(dBname, drinfo);
        }
      }
    }
  }

  @Override
  public int getRowCount(String dBname, Integer drtypeid, String mdcode) {

    int rowCount = drinfoMapper.getRowCount(dBname, drtypeid, mdcode, null, null, null);
    return rowCount;
  }

  @Override
  public int updateUser(String dBname, Integer userid, Integer... ids) {
    int rows = 0;
    for (Integer drid : ids) {
      int count = drinfoMapper.findCountByUseridDrid(dBname, userid, drid);
      if (count == 0) {

        try {
          rows = drinfoMapper.updateUser(dBname, userid, drid);

        } catch (Exception e) {
          e.printStackTrace();
          throw new ServiceException("更新失败");
        }
        rows++;
      }
    }
    return rows;
  }

  @Override
  public List<Drinfo> findAllIsUser(String dBname, Integer drtypeid) {
    List<Drinfo> list = drinfoMapper.findAllIsUser(dBname, drtypeid);
    return list;
  }

  @Override
  public Drinfo findDrByDrid(String dBname, Integer drid) {
    Drinfo drinfo = new Drinfo();

    try {
      drinfo = drinfoMapper.findDrByDrid(dBname, drid);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ServiceException("查询失败");
    }
    return drinfo;
  }

  @Override
  public int findObjectById(String dBname, Integer drid) {
    // TODO Auto-generated method stub
    int rows = 0;
    try {
      rows = drinfoMapper.findObjectById(dBname, drid);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ServiceException("查询失败");
    }
    return rows;
  }

  @Override
  public List<Drinfo> findObjectByids(String dBname, String drname, Integer[] ids) {
    // TODO Auto-generated method stub
    List<Drinfo> list = null;
    try {
      list = drinfoMapper.findObjectByIds(dBname, drname, ids);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new ServiceException("查询错误");
    }
    return list;
  }

  @Override
  public List<Integer> findDridByUserid(String dBname, Integer userid) {
    List<Integer> list = new ArrayList<>();
    try {
      list = drinfoMapper.findDridByUserid(dBname, userid);
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return list;
  }

  @Override
  public int removeUser(String dBname, Integer userid, Integer... ids) {
    // TODO Auto-generated method stub
    int rows = 0;
    try {
      rows = drinfoMapper.removeUser(dBname, userid, ids);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServiceException("删除失败");
    }
    return rows;
  }

  @Override
  public List<JSONObject> findDrByDrIds(String dBname, String drname, Integer[] drIds) {
    // Long s=System.currentTimeMillis();
    List<Subinfo> subinfos = subinfoMapper.findObject(dBname, null, null, null);
    // System.out.println("阈值集合准备==="+(System.currentTimeMillis()-s));
    Map<Integer, String> buildMap = buildinfoMapper.findAll(dBname).stream()
        .collect(Collectors.toMap(Buildinfo::getBuildid, Buildinfo::getBuildname));
    Map<Integer, List<Subinfo>> subMap = new HashMap<>();
    for (Subinfo subinfo : subinfos) {
      if (subMap.get(subinfo.getSubid()) == null) subMap.put(subinfo.getSubid(), new ArrayList<>());
      subMap.get(subinfo.getSubid()).add(subinfo);
    }
    List<JSONObject> drinfoByIds = drinfoMapper.findJSONobjectByIds(dBname, drname, drIds);
    for (JSONObject drinfoById : drinfoByIds) {
      drinfoById.put("buildName", buildMap.get(drinfoById.getInteger("buildid")));
      List<RegSub> regSubs = regMapper.findRegValueByDrId(drinfoById.getInteger("drid"));
      for (RegSub regSub : regSubs) {
        /*String regValue=regSub.getTagValue();
        if (!StringUtils.isEmpty(regSub.getNewtagvalue())){
        	regValue=regSub.getNewtagvalue();
        }*/
        if (!StringUtils.isEmpty(regSub.getRegSub())) {
          List<Subinfo> subinfosByRegs = subMap.get(Integer.valueOf(regSub.getRegSub()));
          if (subinfosByRegs != null && subinfosByRegs.size() > 0) {
            /*for (Subinfo subinfosByReg : subinfosByRegs) {
            	if (subinfosByReg.getValueType().equals("1")){
            		if (subinfosByReg.getValue().equals(regValue)){
            			regSub.setSubname(subinfosByReg.getSubname());
            			regSub.setText(subinfosByReg.getText());
            			regSub.setUrl(subinfosByReg.getUrl());
            			regSub.setImgurl(subinfosByReg.getImgurl());
            		}
            	}else {
            			Float regValueF=Float.valueOf(regValue);
            		if (subinfosByReg.getAndOr().equals("1")){
            			if (Float.valueOf(subinfosByReg.getValueMin())<=regValueF
            			||Float.valueOf(subinfosByReg.getValueMax())>=regValueF){
            				regSub.setSubname(subinfosByReg.getSubname());
            				regSub.setText(subinfosByReg.getText());
            				regSub.setUrl(subinfosByReg.getUrl());
            				regSub.setImgurl(subinfosByReg.getImgurl());
            			}
            		}else {
            			if (Float.valueOf(subinfosByReg.getValueMin())>regValueF
            					||Float.valueOf(subinfosByReg.getValueMax())<regValueF){
            				regSub.setSubname(subinfosByReg.getSubname());
            				regSub.setText(subinfosByReg.getText());
            				regSub.setUrl(subinfosByReg.getUrl());
            				regSub.setImgurl(subinfosByReg.getImgurl());
            			}
            		}
            	}
            }*/
            regSub.setSubinfoList(subinfosByRegs);
          }
        }
      }
      drinfoById.put("reglist", regSubs);
    }
    return drinfoByIds;
  }

  @Override
  public PageObject<Drinfo> findByListPage(Drinfo drinfo) {
    List<Subinfo> subinfos = subinfoMapper.selectList(null);
    Map<Integer, List<Subinfo>> subMap = new HashMap<>();
    subinfos.forEach(
        subinfo -> {
          if (subMap.get(subinfo.getSubid()) == null) {
            subMap.put(subinfo.getSubid(), new ArrayList<>());
          }
          subMap.get(subinfo.getSubid()).add(subinfo);
        });
    LambdaQueryWrapper<Drinfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper
        .eq(!ObjectUtil.isEmpty(drinfo.getDrtypeid()), Drinfo::getDrtypeid, drinfo.getDrtypeid())
        .like(!ObjectUtil.isEmpty(drinfo.getDrname()), Drinfo::getDrname, drinfo.getDrname());
    Map<String, String> qsTagMap = qstagMapper.selectList(null).stream()
        .collect(Collectors.toMap(Qstag::getTagname, Qstag::getTagvalue));
    PageHelper.startPage(drinfo.getPageSum(), drinfo.getPageSize());
    List<Drinfo> drinfoList = drinfoMapper.selectList(lambdaQueryWrapper);
    drinfoList.forEach(
        drinfos -> {
          int drid = drinfos.getDrid();
          int runStateCnt = 0;
          int alarmStateCnt = 0;
          int errorStateCnt = 0;
          List<Reg> regList = regMapper.selectList(new QueryWrapper<Reg>().eq("dr_Id", drid));
          for (Reg reg : regList) {
            if (reg.getDrId() == drid
                && !StringUtils.isEmpty(reg.getRegDrShowType())
                && reg.getRegDrShowType().equals("1")) {
              if (!ObjectUtil.isEmpty(reg.getTagName())) {
                String  value = qsTagMap.get(reg.getTagName());
                if (ObjectUtil.isNotEmpty(value)) {
                  if (value.equals("1")) {
                    runStateCnt = 1;
                  }
                }
              } else if (!reg.getTagValue().equals("0")) {
                runStateCnt = 1;
              }
            }
            if (reg.getDrId() == drid
                && !StringUtils.isEmpty(reg.getRegDrShowType())
                && reg.getRegDrShowType().equals("2")) {
              if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                alarmStateCnt = 2;
              }
            }
            if (reg.getDrId() == drid
                && !StringUtils.isEmpty(reg.getRegDrShowType())
                && reg.getRegDrShowType().equals("3")) {
              if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                errorStateCnt = 4;
              }
            }
            reg.setRegName(reg.getRegName().split(":")[1]);
            if (!ObjectUtil.isEmpty(reg.getTagName())) {
              String  value = qsTagMap.get(reg.getTagName());
               if (ObjectUtil.isNotEmpty(value)) {
                 reg.setNewtagvalue(value);
               }
            }
            if (!StringUtils.isEmpty(reg.getRegSub())) {
              List<Subinfo> subinfosByRegs = subMap.get(Integer.valueOf(reg.getRegSub()));
              reg.setSubinfoList(subinfosByRegs);
            }
          }
          int state = runStateCnt + alarmStateCnt + errorStateCnt;
          drinfos.setState(state);
          drinfos.setReglist(regList);
        });
    drinfoList.sort(Comparator.comparing(Drinfo::getState)); // 排序
    PageInfo<Drinfo> pageinfo = new PageInfo<>(drinfoList);
    PageObject<Drinfo> pageObject = new PageObject<>();
    pageObject
        .setPageCurrent(pageinfo.getPageNum())
        .setPageSize(pageinfo.getPageSize())
        .setPageCount(pageinfo.getPages())
        .setRecords(pageinfo.getList())
        .setRowCount(Math.toIntExact(pageinfo.getTotal()));
    return pageObject;
  }

  @Override
  public DeviceListDataVO findDeviceList(DeviceListDataReq deviceListDataReq) {
    List<DeviceListDataVO.Header> headerList = new ArrayList<>();
    List<DeviceListDataVO.DeviceRegData> deviceRegDataList = new ArrayList<>();
    List<Drtypemode> drTypeModeList =
        drtypemodeMapper.selectList(
            new LambdaQueryWrapper<Drtypemode>()
                .eq(Drtypemode::getDrtypeid, deviceListDataReq.getDrtypeid())
                .eq(Drtypemode::getRegType, "1")
                .orderByAsc(Drtypemode::getRegListShowLevel));
    drTypeModeList.forEach(
        drTypeMode -> {
          DeviceListDataVO.Header header = new DeviceListDataVO.Header();
          header.setRegName(drTypeMode.getRegName()).setRegUnits(drTypeMode.getRegUnits());
          headerList.add(header);
        });
    List<Drinfo> deviceList =
        drinfoMapper.selectList(
            new LambdaQueryWrapper<Drinfo>()
                .eq(Drinfo::getDrtypeid, deviceListDataReq.getDrtypeid()));
    Map<Integer, List<Reg>> regListMap =
        regMapper
            .selectList(
                new LambdaQueryWrapper<Reg>()
                    .eq(Reg::getRegType, "1")
                    .orderByAsc(Reg::getRegListShowLevel))
            .stream()
            .collect(Collectors.groupingBy(Reg::getDrId));
    Map<Integer, List<Subinfo>> subinfoListMap =
        subinfoMapper.selectList(null).stream().collect(Collectors.groupingBy(Subinfo::getSubid));
    Map<String, String> qsTagMap =
        qstagMapper.selectList(null).stream()
            .collect(Collectors.toMap(Qstag::getTagname, Qstag::getTagvalue));
    Map<Integer, String> deviceTypeMap =
        drtypeinfoMapper.selectList(null).stream()
            .collect(Collectors.toMap(Drtypeinfo::getDrtypeid, Drtypeinfo::getDrtypename));
    deviceList.forEach(
        device -> {
          DeviceListDataVO.DeviceRegData deviceRegDate = new DeviceListDataVO.DeviceRegData();
          deviceRegDate
              .setDeviceId(device.getDrid())
              .setDeviceName(device.getDrname())
              .setDeviceType(deviceTypeMap.get(device.getDrtypeid()))
              .setDeviceTypeId(device.getDrtypeid());
          List<Reg> regList = regListMap.get(device.getDrid());
          List<DeviceListDataVO.DeviceRegData.RegData> regDataList = new ArrayList<>();
          regList.forEach(
              reg -> {
                DeviceListDataVO.DeviceRegData.RegData regData =
                    new DeviceListDataVO.DeviceRegData.RegData();
                regData.setRegReadWrite(Integer.parseInt(reg.getRegReadWrite()));
                if (ObjectUtil.isNotEmpty(reg.getTagName())) {
                  regData.setNewTagValue(qsTagMap.get(reg.getTagName()));
                  regData.setTagName(reg.getTagName());
                }
                regData.setTagValue(reg.getTagValue());
                if (ObjectUtil.isNotEmpty(reg.getRegSub())) {
                  List<Subinfo> subinfoList = subinfoListMap.get(Integer.parseInt(reg.getRegSub()));
                  Map<String, String> SubMap =
                      subinfoList.stream()
                          .collect(Collectors.toMap(Subinfo::getValue, Subinfo::getText));
                  if (ObjectUtil.isNotEmpty(reg.getNewtagvalue())) {
                    reg.setSubname(SubMap.get(reg.getNewtagvalue()));
                  } else {
                    reg.setSubname(SubMap.get(reg.getTagValue()));
                  }
                  regDataList.add(regData);
                }
              });
          deviceRegDate.setRegData(regDataList);
          deviceRegDataList.add(deviceRegDate);
        });
    return new DeviceListDataVO().setDeviceRegDataList(deviceRegDataList).setHeaderList(headerList);
  }



  public PageInfo<DeviceVO> findDevicePage(DeviceReq deviceReq) {
    PageHelper.startPage(deviceReq.getPageNum(), deviceReq.getPageSize());
    List<DeviceVO> list =
        drinfoMapper
            .selectList(
                new LambdaQueryWrapper<Drinfo>()
                    .eq(
                        ObjectUtil.isNotEmpty(deviceReq.getDrtypeid()),
                        Drinfo::getDrtypeid,
                        deviceReq.getDrtypeid()))
            .stream()
            .map(
                drinfo -> {
                  DeviceVO deviceVO = new DeviceVO();
                  deviceVO.setDrid(drinfo.getDrid());
                  deviceVO.setDrname(drinfo.getDrname());
                  deviceVO.setDrtypeid(drinfo.getDrtypeid());
                  deviceVO.setDrManufactureFactory(deviceVO.getDrManufactureStyle());
                  deviceVO.setMdcode(deviceVO.getMdcode());
                  deviceVO.setDrManufactureStyle(deviceVO.getDrManufactureStyle());
                  deviceVO.setDrInstallPhone(deviceVO.getDrInstallPhone());
                  deviceVO.setSpid(drinfo.getSpid());
                  return deviceVO;
                })
            .collect(Collectors.toList());
    return new PageInfo<>(list);
  }
}
