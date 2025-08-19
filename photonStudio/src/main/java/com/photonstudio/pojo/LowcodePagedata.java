package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class LowcodePagedata implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 主题id 关联主题表lowcode_theme表
     */
    private Integer themeId;
    /**
     * 页面id(备用)
     */
    private Integer pageId;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * 样式数据 json
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String canvasStyle;
    /**
     * 组件数据 json
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String canvasData;
}
