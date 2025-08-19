package com.photonstudio.handler;



import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.Result;
import com.photonstudio.pojo.vo.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {




    private static final Log log = LogFactory.get();

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return FebsResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result validExceptionHandler(BindException e) {

        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return Result.build(Status.REQUEST_PARAMETER_ERROR.code,message.toString());

    }


    /**
     *  (普通传参)
     *
     * @param e ConstraintViolationException
     * @return FebsResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleConstraintViolationException(ConstraintViolationException e) {

        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));

        return Result.build(Status.REQUEST_PARAMETER_ERROR.code,message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return FebsResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString(), e);

        return Result.build(Status.REQUEST_PARAMETER_ERROR.code,message.toString());
    }



    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = String.format("该方法不支持%s请求", StringUtils.substringBetween(e.getMessage(), Strings.SINGLE_QUOTE, Strings.SINGLE_QUOTE));
        log.error(message, e.getMessage());
        return Result.build(Status.REQUEST_PARAMETER_ERROR.code,message.toString());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String message = "文件大小超出限制";
        log.error(message, e.getMessage());
        return Result.build(Status.REQUEST_PARAMETER_ERROR.code,message.toString());
    }

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return Result.build(Status.SYSTEM_ERROR.code,e.getMessage());
    }

}
