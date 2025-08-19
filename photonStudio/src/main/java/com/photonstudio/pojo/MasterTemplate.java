package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("master_template")
@ApiModel("模板表")
public class MasterTemplate {

    @TableId(type = IdType.AUTO)
    private  Integer id;
    @ApiModelProperty("主题")
    @TableField("theme")
    private String theme;
    @ApiModelProperty("页面名称")
    @TableField("page_name")
    private String pageName;
    @ApiModelProperty("组件类型")
    @TableField("template_type")
    private String templateType;
    @ApiModelProperty("模板标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("封面图")
    @TableField("imgurl")
    private String imgurl;
    @ApiModelProperty("描述")
    @TableField("remark")
    private String remark;
    @ApiModelProperty("页面全局数据")
    @TableField("canvas_style_data")
    private String canvasStyleData;
    @ApiModelProperty("画布组件数据")
    @TableField("component_data")
    private String componentData;
    @ApiModelProperty("显示状态 0不显示，1显示，2假删除")
    @TableField("`status`")
    private Integer status;
    @ApiModelProperty("发布状态 0不发布，1发布")
    @TableField("`release`")
    private Integer release;
    @ApiModelProperty("创建的用户")
    @TableField("add_user")
    private String addUser;
    @ApiModelProperty("修改的用户")
    @TableField("update_user")
    private String updateUser;
    @ApiModelProperty("创建时间")
    @TableField("add_time")
    private  String addTime;
    @ApiModelProperty("修改时间")
    @TableField("updata_time")
    private String updataTime;



}
