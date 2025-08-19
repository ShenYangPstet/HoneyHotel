package com.photonstudio.service;

import com.photonstudio.pojo.MjSp;

public interface MjSpService {

    void zjxg(String dBname, MjSp mjsp);

    void delete(String dBname, String mjid);

    String findSpByMj(String dBname, String mjid);
}
