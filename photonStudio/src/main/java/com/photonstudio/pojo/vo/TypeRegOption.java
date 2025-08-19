package com.photonstudio.pojo.vo;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Subinfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 设备类型、类型模版寄存器、寄存器分项组装结构VO
 *
 * @author bingo
 * @date 2022-10-09 13:41
 */
@Data
@Accessors(chain = true)
@ApiModel("设备类型、类型模版寄存器、寄存器选项组装结构")
public class TypeRegOption {
    /**
     * 设备类型id
     */
    @ApiModelProperty("设备类型id")
    private Integer typeId;
    /**
     * 设备类型名称
     */
    @ApiModelProperty("设备类型名称")
    private String typeName;
    /**
     * 寄存器集合
     */
    @ApiModelProperty("寄存器集合")
    private List<TypeReg> typeRegs;
    /**
     * 设备总数
     */
    @ApiModelProperty("设备总数")
    private Integer deviceTotalNum;
    /**
     * 设备运行总数
     */
    @ApiModelProperty("设备运行数")
    private Integer deviceRunNum;
    /**
     * 设备报警总数
     */
    @ApiModelProperty("设备报警数")
    private Integer deviceAlarmNum;

    public static TypeRegOption drTypeInfo2TypeTree(Drtypeinfo type) {
        return new TypeRegOption()
                .setTypeId(type.getDrtypeid())
                .setTypeName(type.getDrtypename())
                .setDeviceTotalNum(type.getDrinfoSum())
                .setDeviceRunNum(type.getDrinfoRunSum())
                .setDeviceAlarmNum(type.getDrinfoAlarmSum());
    }

    /**
     * 类型模版寄存器
     */
    @Data
    @Accessors(chain = true)
    @ApiModel("类型模版寄存器、寄存器选项组装结构")
    public static class TypeReg {
        /**
         * 设备类型id
         */
        @JsonIgnore
        private Integer typeId;
        /**
         * 寄存器选项id
         */
        @JsonIgnore
        private Integer subId;
        /**
         * 寄存器名字
         */
        @ApiModelProperty("寄存器名字")
        private String regName;
        /**
         * 寄存器单位
         */
        @ApiModelProperty("寄存器单位")
        private String regUnits;
        /**
         * 寄存器默认值
         */
        @ApiModelProperty("寄存器默认值")
        private String defaultValue;
        /**
         * 寄存器排序位置
         */
        @ApiModelProperty("寄存器排序位置")
        private Integer order;
        /**
         * 寄存器选项
         */
        @ApiModelProperty("寄存器选项")
        private List<RegOption> options;

        public static TypeReg drTypeMode2TypeReg(Drtypemode typeMode) {
            return new TypeReg()
                    .setTypeId(typeMode.getDrtypeid())
                    .setSubId(Convert.toInt(typeMode.getRegSub()))
                    .setRegName(typeMode.getRegName())
                    .setRegUnits(typeMode.getRegUnits())
                    .setDefaultValue(typeMode.getTagValue())
                    .setOrder(typeMode.getRegListShowLevel());
        }
    }

    /**
     * 寄存器选项
     */
    @Data
    @Accessors(chain = true)
    @ApiModel("寄存器选项")
    public static class RegOption {
        /**
         * 寄存器选项id
         */
        @JsonIgnore
        private Integer subId;
        /**
         * 选项名称
         */
        @ApiModelProperty("选项名称")
        private String name;
        /**
         * 选项值
         */
        @ApiModelProperty("选项值")
        private String value;

        public static RegOption subInfo2RegOption(Subinfo subinfo) {
            return new RegOption()
                    .setSubId(subinfo.getSubid())
                    .setName(subinfo.getText())
                    .setValue(subinfo.getValue());
        }
    }
}