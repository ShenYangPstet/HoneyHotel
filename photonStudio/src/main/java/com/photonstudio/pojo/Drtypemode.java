package com.photonstudio.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Drtypemode {
    @Excel(name = "id")
    private Integer typemodeid;
    @Excel(name = "设备类型id")
    private Integer drtypeid;
    @Excel(name = "寄存器名")
    private String regName;
    @Excel(name = "寄存器类型")
    private String regType;
    @Excel(name = "寄存器单位")
    private String regUnits;
    @Excel(name = "读写属性")
    @TableField("reg_readWrite")
    private String regReadWrite;
    @Excel(name = "寄存器初始值")
    private String tagValue;
    @Excel(name = "寄存器显示顺序", width = 15)
    @TableField("reg_listShowLevel")
    private Integer regListShowLevel;
    @Excel(name = "寄存器的阀值")
    private String regSub;
    @Excel(name = "设备属性")
    @TableField("reg_drShowType")
    private String regDrShowType;
    @Excel(name = "设备显示阀值")
    private String regDrsub;
    @Excel(name = "是否历史存储")
    private String ishistory;
    @Excel(name = "是否能耗存储")
    private String isenergy;
    @Excel(name = "是否报警")
    private String isalarm;
    @Excel(name = "报警类别")
    private String alarmtype;
    @Excel(name = "报警条件最小值", width = 15)
    private String valueMin;
    @Excel(name = "报警条件最大值", width = 15)
    private String valueMax;
    @Excel(name = "报警条件与或")
    private String andOr;
    @Excel(name = "报警级别")
    private String alarmLevel;
    @Excel(name = "是否列表显示")
    private Integer islistshow;
    @Excel(name = "寄存器采集周期", width = 15)
    private String tagTime;
    private String regcolor;
    @TableField(exist = false)
    private String subname;
    @TableField(exist = false)
    private String drtypename;
    @TableField(exist = false)
    private String alarmtypename;
    @TableField(exist = false)
    private String tagName;

}
