package com.photonstudio.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("模板删除传参")
public class ParaVo {

    @ApiModelProperty("模板的唯一id")
    @NotNull(message = "模板的唯一不能为空")
    private Integer id;
    @ApiModelProperty(" 2 假删除")
    @NotNull(message = "不能为空")
    private Integer status;

}
