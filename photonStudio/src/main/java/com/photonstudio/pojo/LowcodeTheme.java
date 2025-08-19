package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class LowcodeTheme implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 项目ID
     */
    private Integer appId;
    /**
     * 类型ID(备用)
     */
    private Integer type_id;
    /**
     * 状态0隐藏1启用
     */
    private Integer status;
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
