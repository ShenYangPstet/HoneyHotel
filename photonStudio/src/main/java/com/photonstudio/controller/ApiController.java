package com.photonstudio.controller;


import com.photonstudio.service.ApiService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

/**
 * @ClassName ApiController
 * @Description TODO 第三方接口
 * @Author 16526
 * @Date 2020/12/19 10:33
 * @ModifyDate 2020/12/19 10:33
 * @Version 1.0
 */
@RestController
@RequestMapping("/zsqyapi")
@Api(tags = "第三方接口")
@RequiredArgsConstructor
public class ApiController {
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final ApiService apiService;


}
