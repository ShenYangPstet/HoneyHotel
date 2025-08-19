package com.photonstudio.serviceImpl;

import com.photonstudio.mapper.MjSpMapper;
import com.photonstudio.pojo.MjSp;
import com.photonstudio.service.MjSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MjSpServiceImpl implements MjSpService {
    @Autowired
    private MjSpMapper mjSpMapper;

    @Override
    public void zjxg(String dBname, MjSp mjsp) {
        try {
            if (mjSpMapper.findMjID(dBname, mjsp.getMjid()) != null) {
                mjSpMapper.updateMjSp(dBname, mjsp);
            } else {
                mjSpMapper.insertMjSp(dBname, mjsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String dBname, String mjid) {
        try {
            mjSpMapper.deleteMjSp(dBname, mjid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findSpByMj(String dBname, String mjid) {
        try {
            String spID = mjSpMapper.findSpID(dBname, mjid);
            return spID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
