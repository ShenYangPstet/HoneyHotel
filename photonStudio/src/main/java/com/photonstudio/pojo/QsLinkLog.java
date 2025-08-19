package com.photonstudio.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Api("类")
@Data
public class QsLinkLog {
    private Integer id;
    private Integer rwId;//任务ID
    @ApiModelProperty("任务名")
    private String rwName;//任务名
    private Integer jgId;//结果ID
    @ApiModelProperty("结果名称")
    private String jgName;//结果名称
    private String regName;//变量名称
    private String regValue;//变量值
    private String spId;//视频ID
    private String spName;
    private Integer picId;//页面,Id
    private String picName;
    private String picParameter;//页面参数
    private String phone;//手机号
    private String message;//短信内容
    @ApiModelProperty("联动时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sendtime;//联动时间
    @ApiModelProperty("执行状态(0成功，1失败）")
    private Integer executeState;//执行状态(0成功，1失败）

    //以下为非数据库字段

    private Integer startIndex;//起始页码（转换）

    private Integer pageCurrent;//页码

    private Integer pageSize;//页大小

    private String range;//查询范围（select 后接）

    private String dBname;//数据库+表名

    private Integer count;//条数

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime; //结束时间




}
