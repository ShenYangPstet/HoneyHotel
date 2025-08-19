package com.photonstudio.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 统计图展示对象
 */
@Data
@Accessors(chain = true)
public class Chart {
    private String name;
    private List<String> xList;
    private List<Curve> curveList;
    private Double monthCompareRate;
    private Double yearCompareRate;
}
