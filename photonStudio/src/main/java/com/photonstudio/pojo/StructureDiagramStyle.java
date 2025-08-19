package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 结构图样式表实体类
 *
 * @author hanxi
 * @date 2022-05-19
 */
@Data
public class StructureDiagramStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 项目id
     */
    private Integer appId;
    /**
     * 设备类型id
     */
    private Integer drtypeId;
    /**
     * 样式数据
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String canvasStyle;
    /**
     * 组件数据
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String canvasData;
}
