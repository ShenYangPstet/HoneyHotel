package com.photonstudio.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 曲线对象
 */
@Data
@Accessors(chain = true)
public class Curve {
    private String name;
    private String unit;
    private List<Integer> values;
}
