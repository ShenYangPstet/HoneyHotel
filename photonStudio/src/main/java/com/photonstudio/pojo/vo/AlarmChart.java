package com.photonstudio.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 报警统计图返回对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AlarmChart extends Chart {
    private Integer totalAlarm;
}
