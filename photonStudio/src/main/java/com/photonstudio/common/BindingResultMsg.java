package com.photonstudio.common;

import com.google.common.collect.Maps;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

/*
     获取检验的报错信息
 */
public class BindingResultMsg {


    public static Result  bindingResultMsg(BindingResult bindingResult) {
        Map<String, Object> errMap = Maps.newHashMap();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            String key;
            String msg;
            if (allError instanceof FieldError) {
                FieldError fe = (FieldError) allError;
                key = fe.getField();// 获取错误验证字段名
            } else {
                key = allError.getObjectName();// 获取验证对象名称
            }
            msg = allError.getDefaultMessage();
            errMap.put(key, msg);
        }
        return Result.build(Status.REQUEST_PARAMETER_ERROR.code, Status.REQUEST_PARAMETER_ERROR.message, errMap);
    }
}
