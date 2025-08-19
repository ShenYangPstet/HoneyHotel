package com.photonstudio.controller;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.MjSp;
import com.photonstudio.service.MjSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zsqy/mjsp/{dBname}")
public class MjSpController {
    @Autowired
    private MjSpService mjSpService;

    @RequestMapping("/zjxg")
    public SysResult ZjXgMjSp(@PathVariable String dBname, MjSp mjsp){
        try {
            mjSpService.zjxg(dBname, mjsp);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(50009, "操作失败");
        }
    }

    @RequestMapping("/deletemjsp")
    public SysResult deleteMjSp(@PathVariable String dBname, String mjid){
        try {
            mjSpService.delete(dBname, mjid);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(50009, "删除失败");
        }
    }

    @RequestMapping("/findsp")
    public SysResult findSpByMj(@PathVariable String dBname, String mjid){
        try {
            String spid = mjSpService.findSpByMj(dBname, mjid);
            return SysResult.oK(spid);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(50009, "查找失败");
        }
    }
}
