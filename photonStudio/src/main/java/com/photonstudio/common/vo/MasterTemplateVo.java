package com.photonstudio.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@ApiModel("模板参数类")
public class MasterTemplateVo<T> {


    private Integer id;

    @ApiModelProperty("页面名称")
    @NotBlank(message = "页面名称不能为空")
    @Size(max = 50,message = "{noMoreThan}")
    private String pageName;

    @ApiModelProperty("主题")
    @NotBlank(message = "主题不能为空")
    @Size(max = 50,message = "{noMoreThan}")
    private String theme;


    @ApiModelProperty("组件类型")
    @NotBlank(message = "组件类型不能为空")
    private String templateType;

    @ApiModelProperty("模板标题")
    @NotBlank(message = "模板标题不能为空")
    private String title;

    @ApiModelProperty("封面图")
    @NotBlank(message = "封面图不能为空")
    private String imgurl;

    @ApiModelProperty("描述")
    @Size(max = 255,message = "{noMoreThan}")
    private String remark;

    @ApiModelProperty("页面全局数据")
    @NotBlank(message = "页面全局数据不能为空")
    private String canvasStyleData;

    @ApiModelProperty("画布组件数据")
    @NotBlank(message = "画布组件数据不能为空")
    private String componentData;

    @ApiModelProperty("显示状态 0不显示，1显示，2假删除")
    @NotNull(message = "显示状态不能为空")
    private Integer status;

    @ApiModelProperty("发布状态 0不发布，1发布")
    @NotNull(message = "发布状态不能为空")
    private Integer release;

}
