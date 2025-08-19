package com.photonstudio.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class PersonInout {
    private Integer id;
    private Integer index;//编号
    private Integer type;//记录类型 1刷卡记录2门磁,按钮, 设备启动, 远程开门记录3报警记录	1
    private Integer validity;//有效性0不通过1通过
    private Integer doorNum;//门号
    private Integer inout;//进出门1进门2出门
    private Long cardCode;//卡号
    private Date recordtime;//记录时间
}
