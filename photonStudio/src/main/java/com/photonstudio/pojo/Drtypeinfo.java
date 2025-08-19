package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
@TableName("drtypeinfo")
@Accessors(chain = true)
public class Drtypeinfo {

    @TableId(type = IdType.AUTO)
    private Integer drtypeid;
    @TableField("drtypename")
    private String drtypename;
    @TableField("parentid")
    private Integer parentid;
    @TableField("drtypeiconid")
    private Integer drtypeiconid;
    @TableField("isshow")
    private Integer isshow;
    @TableField("isdrtype")
    private Integer isdrtype;

    @TableField("iscustom_type")
    private Integer iscustomType;
    @TableField("picid")
    private Integer picid;
    @TableField("parameter_display")
    private Integer parameterDisplay;//页面编辑是否展示设备列表信息栏0不显示1显示
    @TableField("parameter_list")
    private String parameterList;//资产信息information，历史数据historicaldata，报警记录alarm
    @TableField(exist = false)
    private Integer picmodeid;
    @TableField(exist = false)
    private String icontype;
    @TableField(exist = false)
    private List<Drtypeinfo> drtypeinfoList;
    @TableField(exist = false)
    private List<Drinfo> drinfList;
    @TableField(exist = false)
    private Integer drinfoSum;
    @TableField(exist = false)
    private Integer drinfomalfunctionSum;
    @TableField(exist = false)
    private Integer drinfoRunSum;
    @TableField(exist = false)
    private Integer drinfoAlarmSum;

    public Boolean isShow() {
        return isshow != null && isshow.equals(1);
    }

    /**
     * 设备信息设置图标
     *
     * @param iconId2Url 设备id-设备图标路径map
     * @return 设备信息设置图标函数
     */
    public static Function<Drtypeinfo, Drtypeinfo> fillIconUrl(Map<Integer, String> iconId2Url) {
        return (drtypeinfo) -> drtypeinfo.setIcontype(iconId2Url.get(drtypeinfo.getDrtypeiconid()));
    }
}
