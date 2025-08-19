package com.photonstudio.common.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("参数类")
public class ParameterVo {


    @ApiModelProperty("页数")
    @NotNull(message = "页数不能为空")
    private Integer pageSum;
    @ApiModelProperty("条数")
    @NotNull(message = "条数不能为空")
    private Integer pageSize;
    @ApiModelProperty("用户名称")
    private String username;
    @ApiModelProperty("页面名称")
    private String pageName;
    @ApiModelProperty("显示状态 0不显示，1显示")
    private Integer status;
    @ApiModelProperty("发布状态 0不发布，1发布")
    private Integer release;
}
