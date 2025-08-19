package com.photonstudio.common.annotation;

import java.lang.annotation.*;

/**
    设备控制以及参数修改操作
 */
@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface OperationLogAnnotation {
    String operModul() default ""; // 操作模块

    String operType() default "";  // 操作类型

    boolean  Mass()  default false;// 群发下发操作

    boolean singleShot() default  false;//单发操作

    String Linkagetask() default ""; //联动任务操作;

    String Energy() default ""; //能耗配置操作;

    boolean implementAlarm() default  false; //实施报警;

    String  Modetask() default  "";//

    String ModetaskTime() default "";//时间模式



}
