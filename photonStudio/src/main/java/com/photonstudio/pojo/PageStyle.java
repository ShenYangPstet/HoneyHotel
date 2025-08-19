package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 页面样式表实体类
 *
 * @author bingo
 * @date 2022-05-07
 */
@Data
public class PageStyle implements Serializable {

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
     * 页面id
     */
    private Integer picId;
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
