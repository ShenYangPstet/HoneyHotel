package com.photonstudio.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel("设备列表数据")
public class DeviceListDataVO {

    @ApiModelProperty("标头数据")
    List<Header>  headerList;
    @ApiModelProperty("设备变量数据")
    List<DeviceRegData> deviceRegDataList;


    @Data
    @Accessors(chain = true)
    @ApiModel("标头数据")
    public static class  Header{
        @ApiModelProperty("变量名称")
        private String regName;
        @ApiModelProperty("变量单位")
        private String regUnits;
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("设备变量数据")
    public static class DeviceRegData {

        @ApiModelProperty("设备名称")
        private String deviceName;
        @ApiModelProperty("设备id")
        private Integer deviceId;
        @ApiModelProperty("设备类型id")
        private Integer deviceTypeId;
        @ApiModelProperty("设备类型名称")
        private String deviceType;
        private List<RegData> regData;

        @Data
        @Accessors(chain = true)
        @ApiModel("设备变量数据")
        public static class RegData {
           @ApiModelProperty("初始值")
           private String tagValue;
           @ApiModelProperty("实际值")
           private String newTagValue;
           @ApiModelProperty("读写状态，1是只读，2读写")
           private Integer regReadWrite;
           @ApiModelProperty("值说明")
           private String subName;
           @ApiModelProperty("寄存器")
           private String tagName;
        }
    }


}
