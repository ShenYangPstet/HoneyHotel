package com.photonstudio.common.vo;

import com.photonstudio.pojo.Subinfo;
import lombok.Data;

import java.util.List;

/**
 * 页面设备参数列表展示vo对象
 */
@Data
public class RegSub {

    private Integer regId;
    private String regName;
    private Integer drId;
    private String regType;
    private String regUnits;
    private String regReadWrite;
    private String regListShowLevel;
    private String regSub;
    //private String regDrShowType;
    //private String regDrsub;
    private String tagName;
    private String tagValue;
    //private String ishistory;
    //private String isenergy;
    //private String isalarm;
    //private String alarmtype;
    //private String valueMin;
    //private String valueMax;
    //private String andOr;
    //private String alarmLevel;
   // private String tagAlarmState;
    //private String tagTime;
    //private String regmath;
    private List<Subinfo> subinfoList;//变量对应阈值

   /* private String text;
    private String url;//图片id 关联image表
    private String subname;
    private String imgurl;//图片路径*/
    private String regcolor;
    //private String drname;
   // private String alarmtypelevel;
    //private String drtypename;
    private String newtagvalue;//qstag值
    private Integer islistshow;//变量显示等级1低2中3高
}
