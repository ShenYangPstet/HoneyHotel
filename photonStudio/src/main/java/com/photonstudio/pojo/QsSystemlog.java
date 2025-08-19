package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 操作日志类
 */
@Data
@ApiModel("用户操作日志")
@TableName("qs_systemlog")
public class QsSystemlog  {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private  Integer id;
    @TableField("user_code")

    @Excel(name = "操作者")
    private String userCode;//操作者
    @TableField("ip")
    @Excel(name = "ip地址",width = 25)
    private String ip; //地址
    @Excel(name = "操作类型",width = 25)
    @TableField("type")
    private String type;//操作类型
    @TableField("parameter")
    private String parameter;//参数
    @TableField("method")
    private String method;//方法名
    @TableField("operation_type")
    @Excel(name = "操作类型",width = 25)
    private String operationType;//操作模块
    @TableField("state")
    @Excel(name = "状态(0 失败 1成功)",width = 25)
    private Integer state;//状态(0 失败 1成功)
    @TableField("operation_describe")
    @Excel(name = "操作描述",width = 45)
    private String operationDescribe; //操作描述
    @TableField("operation_time")
    @Excel(name = "操作时间",width = 25)
    private String operationTime;//操作时间

}
