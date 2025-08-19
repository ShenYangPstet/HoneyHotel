package com.photonstudio.dataupload.controller;

import com.github.pagehelper.PageInfo;

import com.photonstudio.common.vo.Result;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.dataupload.req.DeviceReq;
import com.photonstudio.dataupload.req.RegReq;
import com.photonstudio.dataupload.vo.DeviceVO;
import com.photonstudio.dataupload.vo.DrtypeinfoVO;
import com.photonstudio.dataupload.vo.RegVO;
import com.photonstudio.service.DrinfoService;
import com.photonstudio.service.DrtypeinfoService;
import com.photonstudio.service.QsLdJgrService;
import com.photonstudio.service.RegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/dataupload")
@RequiredArgsConstructor
@Api(tags = "提供给第三方接口文档")
public class DataUploadController {

  private final DrtypeinfoService drtypeinfoService;

  private final DrinfoService drinfoService;

  private final RegService regService;

  private final QsLdJgrService qsLdJgrService;

  @GetMapping("/finDrTypeInfo")
  @ApiOperation("获取所有设备类型信息")
  public Result<List<DrtypeinfoVO>> finDrTypeInfo() {
    List<DrtypeinfoVO> list =
        drtypeinfoService.list().stream()
            .filter(item -> item.getParentid() != 0)
            .map(
                item -> {
                  DrtypeinfoVO vo = new DrtypeinfoVO();
                  vo.setDrtypeid(item.getDrtypeid());
                  vo.setDrtypename(item.getDrtypename());
                  return vo;
                })
            .collect(Collectors.toList());
    return Result.ok(list);
  }

  @PostMapping("/findDevicePage")
  @ApiOperation("分页查询所有设备信息")
  public Result<PageInfo<DeviceVO>> findDevicePage(@Validated @RequestBody DeviceReq deviceReq) {
    return Result.ok(drinfoService.findDevicePage(deviceReq));
  }

  @PostMapping("/findDeviceRegInfoPage")
  @ApiOperation("分页查询所有设备变量信息")
  public Result<PageInfo<RegVO>> findDeviceRegInfoPage(@Validated @RequestBody RegReq regReq) {
    return Result.ok(regService.findDeviceRegInfoPage(regReq));
  }

  @GetMapping("/implement")
  @ApiOperation("设备控制")
  public SysResult doimplement(
      @NotNull(message = "{required}") @ApiParam("项目id") Integer appid,
      @NotBlank(message = "{required}") @ApiParam("寄存器 通过分页查询设备变量获取") String tagname,
      @NotBlank(message = "{required}") @ApiParam("下发值") String tagvalue) {
    HttpServletRequest request =
        ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    String database = request.getHeader("database");

    String msg = tagname + ":" + tagvalue;
    try {
      qsLdJgrService.doimplements(database, appid, msg, request);
      return SysResult.oK();
    } catch (RestClientException e) {
      return SysResult.build(50009, "执行失败");
    }
  }
}
