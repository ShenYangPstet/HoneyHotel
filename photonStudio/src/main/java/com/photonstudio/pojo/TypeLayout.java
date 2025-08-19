package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 设备类型布局
 *
 * @author bingo
 */
@Data
public class TypeLayout {
    /**
     * 设备类型布局id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 设备类型id
     */
    private Integer typeId;
    /**
     * 设备类型布局索引
     */
    private Integer typeIndex;
    /**
     * 用户id
     */
    private Integer userId;
}
