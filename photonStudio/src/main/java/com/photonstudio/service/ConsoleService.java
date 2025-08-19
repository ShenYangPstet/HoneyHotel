package com.photonstudio.service;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.pojo.vo.TypeRegOption;

import java.util.List;

public interface ConsoleService {
    void TemplateInstructionSend(String dBname, Integer appId, JSONObject jsonObject);

    List<TypeRegOption> getConsoleMode(String database);
}
