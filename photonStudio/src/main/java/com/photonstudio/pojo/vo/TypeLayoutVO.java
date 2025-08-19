package com.photonstudio.pojo.vo;

import com.photonstudio.pojo.Drtypeinfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.function.Function;

/**
 * 设备类型布局VO
 *
 * @author bingo
 */
@Data
@Accessors(chain = true)
public class TypeLayoutVO {
    /**
     * 设备类型id
     */
    private Integer typeId;
    /**
     * 设备类型名称
     */
    private String typeName;
    /**
     * 设备类型图标
     */
    private String typeIconUrl;
    /**
     * 绑定的页面编辑生成的页面id
     */
    private Integer pageId;
    /**
     * 设备类型布局索引
     */
    private Integer typeIndex;
    /**
     * 设备总数
     */
    private Integer totalNum;
    /**
     * 在线数
     */
    private Integer onlineNum;
    /**
     * 离线数
     */
    private Integer offlineNum;
    /**
     * 报警数
     */
    private Integer alarmNum;
    /**
     * 故障数
     */
    private Integer faultNum;

    /**
     * 设备类型信息转设备类型布局信息
     *
     * @param drtypeinfo 带有设备运行状况统计信息的设备类型信息
     * @return 设备类型布局信息VO
     */
    public static TypeLayoutVO drTypeInfo2TypeLayoutVO(Drtypeinfo drtypeinfo) {
        return new TypeLayoutVO()
                .setTypeId(drtypeinfo.getDrtypeid())
                .setTypeName(drtypeinfo.getDrtypename())
                .setTypeIconUrl(drtypeinfo.getIcontype())
                .setPageId(drtypeinfo.getPicid())
                .setTotalNum(drtypeinfo.getDrinfoSum())
                .setOnlineNum(drtypeinfo.getDrinfoRunSum())
                .setOfflineNum(drtypeinfo.getDrinfoSum() - drtypeinfo.getDrinfoRunSum())
                .setAlarmNum(drtypeinfo.getDrinfoAlarmSum())
                .setFaultNum(drtypeinfo.getDrinfomalfunctionSum());
    }

    /**
     * 设备类型布局填充布局索引
     *
     * @param typeId2TypeIndexMap 设备类型id-布局索引map
     * @return 设备类型布局填充布局索引函数
     */
    public static Function<TypeLayoutVO, TypeLayoutVO> fillTypeIndex(Map<Integer, Integer> typeId2TypeIndexMap) {
        return typeLayoutVO -> typeLayoutVO.setTypeIndex(typeId2TypeIndexMap.get(typeLayoutVO.getTypeId()));
    }
}
