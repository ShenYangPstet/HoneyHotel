package com.photonstudio.serviceImpl;

import com.photonstudio.mapper.ApiMapper;
import com.photonstudio.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ApiServiceImpl
 * @Description TODO
 * @Author 16526
 * @Date 2020/12/21 14:51
 * @ModifyDate 2020/12/21 14:51
 * @Version 1.0
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ApiMapper apiMapper;

}
