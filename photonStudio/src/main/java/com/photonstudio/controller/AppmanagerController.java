package com.photonstudio.controller;

import com.photonstudio.common.CheckAuthorizeCode;
import com.photonstudio.common.vo.SysResult;

import com.photonstudio.pojo.Appmanager;
import com.photonstudio.service.AppmanagerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zsqy/manager")
public class AppmanagerController {
  @Value("${key.keydefault}")
  private String key;

  @Value("${key.path}")
  private String licensePath;

  @Autowired private AppmanagerService appmanagerService;


  @RequestMapping("/Check")
  public SysResult findCheckappmanager(String appName) {
    boolean flag = appmanagerService.findCheckappmanager(appName);
    return SysResult.oK(flag);
  }

  @RequestMapping("/findObject")
  public SysResult findObjectByApptypeid(
      Integer apptypeid,
      Integer userid,
      String region,
      String city,
      Integer pageCurrent,
      Integer pageSize,
      String state) {
    return SysResult.oK(
        appmanagerService.findObjectByApptypeid(
            apptypeid, userid, region, city, pageCurrent, pageSize, state));
  }

  @RequestMapping("/findAllByAppids")
  public SysResult findObjectByAppids(Integer... appids) {
    return SysResult.oK(appmanagerService.findAllByAppids(appids));
  }

  @RequestMapping("/findAll")
  public SysResult findAllByApptypeid(Integer userid, String region, String city, String state) {

    return SysResult.oK(appmanagerService.findAllByApptypeid(userid, region, city));
  }

  @RequestMapping("/findObjectByUser") // 查询user未添加的项目
  public SysResult findObjectByUesrid(Integer userid) {
    List<Appmanager> list = appmanagerService.findObjectByUesrid(userid);
    return SysResult.oK(list);
  }

  @RequestMapping("/deleteObject")
  public SysResult deleteObjectByIds(Integer appid, String dBname) {
    System.out.println(appid);
    int row = appmanagerService.deleteObjectsById(appid, dBname);
    if (row == 0) {
      return SysResult.build(50009, "可能已经不存在");
    }
    return SysResult.oK();
  }

  @RequestMapping("/save")
  public SysResult saveObject(Appmanager appmanager) {
    System.out.println(appmanager);
    int row = appmanagerService.saveObject(appmanager);
    if (row == 1) {
      return SysResult.oK();
    }
    return SysResult.build(50009, "失败");
  }

  @RequestMapping("/init")
  public SysResult initObject(String dBname, Integer appid) {
    int appCount = appmanagerService.getRowCountByLicense();
//    		String appMaxNum=CheckAuthorizeCode.getAppMaxNum(key, licensePath, 4);
//    		if(appCount>=Integer.valueOf(appMaxNum))return SysResult.build(50009, "项目数量达到上限"+appMaxNum);
//    AuthorizationParametersReq authorizationParametersReq = new AuthorizationParametersReq();
//    if (ObjectUtil.isEmpty(key)) {
//      authorizationParametersReq.setProjectId("202301010001");
//    } else {
//      authorizationParametersReq.setProjectId(key);
//    }
//    // 校验授权
//    AuthorizationCheckResult check = apiService.authorizationCheck(authorizationParametersReq);
//    if (check.getCode() != 0) {
//      return SysResult.build(50009, check.getMessage());
//    }
    int rows = appmanagerService.initObject(dBname, appid);
    System.out.println("库名：" + dBname + "  appid：" + appid);
    return SysResult.oK();
  }

  @RequestMapping("/update")
  private SysResult updateObjectById(Appmanager appmanager) {
    System.out.println(appmanager);
    int row = appmanagerService.updateObject(appmanager);
    if (row != 1) {
      return SysResult.build(50009, "失败");
    }
    return SysResult.oK();
  }

  @RequestMapping("/findCount")
  private SysResult findCount(String apptypeid) {
    int row = appmanagerService.findCount(apptypeid);
    return SysResult.oK(row);
  }

  @RequestMapping("/remove")
  private SysResult removeObject(String state, Integer... appids) {
    if ("0".equals(state)) {
      int appCount = appmanagerService.getRowCountByLicense();
      List<Appmanager> list = appmanagerService.findAllByAppidsAppstate(appids, "1");
      String appMaxNum = CheckAuthorizeCode.getAppMaxNum(key, licensePath, 4);
      System.out.println(list);
      if ((appCount + list.size()) > Integer.valueOf(appMaxNum))
        return SysResult.build(50009, "项目数量达到上限" + appMaxNum);
    }
    int row = appmanagerService.removeObject(state, appids);
    return SysResult.oK();
  }
}
