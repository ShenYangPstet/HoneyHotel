package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.DrtypemodeMapper;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Reg;
import com.photonstudio.service.DrtypemodeService;
import com.photonstudio.service.QsLdJgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrtypemodeServiceImpl extends ServiceImpl<DrtypemodeMapper, Drtypemode> implements DrtypemodeService {
    @Autowired
    private DrtypemodeMapper drtypemodeMapper;
    @Autowired
    private QsLdJgrService qsLdJgrService;

    @Override
    public PageObject<Drtypemode> findObject(String dBname, Integer drtypeid, Integer pageCurrent, Integer pageSize) {
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        int rowCount = drtypemodeMapper.getRowCount(dBname, drtypeid);
        int startIndex = (pageCurrent - 1) * pageSize;
        List<Drtypemode> list = new ArrayList<>();
        list = drtypemodeMapper.findObject(dBname, drtypeid, startIndex, pageSize);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public List<Drtypemode> findAllByDrtypeid(String dBname, Integer drtypeid) {

        return drtypemodeMapper.findObject(dBname, drtypeid, null, null);
    }

    @Override
    public int saveObject(String dBname, Drtypemode drtypemode) {
        int rows = 0;
        try {
            rows = drtypemodeMapper.insertObject(dBname, drtypemode);
            if (dBname.equals("zsqy_v2")) {
                return rows;
            }
            int count = drtypemodeMapper.queryCntDrinfo(dBname, drtypemode.getDrtypeid());//查询设备箱类型下有没有设备
            if (count > 0) {
                drtypemodeMapper.insertReg(dBname, drtypemode.getDrtypeid(), drtypemode.getRegListShowLevel());//新增模板时候 如果已经存在设备 需要将模板的数据插入到设备对应的变量
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        return rows;
    }

    @Override
    public int updateObject(String dBname, Drtypemode drtypemode) {
        int rows = 0;
        try {
            if (!dBname.equals("zsqy_v2")) {
                rows = drtypemodeMapper.updateRegObject(dBname, drtypemode);
            }
            rows = drtypemodeMapper.updateObject(dBname, drtypemode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("更新失败");
        }
        return rows;
    }

    @Override
    public int deleteObjectById(String dBname, Integer... ids) {
        if (ids == null || ids.length == 0) throw new IllegalArgumentException("请选择");
        int rows = 0;
        try {
            if (!dBname.equals("zsqy_v2")) {
                for (Integer id : ids) {
                    Drtypemode drtypemode = drtypemodeMapper.queryDrtypemodeByid(dBname, id);
                    Integer drtypeid = drtypemode.getDrtypeid();
                    String regListShowLevel = String.valueOf(drtypemode.getRegListShowLevel());
                    drtypemodeMapper.deleteJgtMode(dBname, drtypeid, regListShowLevel);
                    drtypemodeMapper.deleteReg(dBname, drtypeid, regListShowLevel);
                }
            }
            rows = drtypemodeMapper.deleteObjectById(dBname, ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败");
        }
        return rows;
    }

    @Override
    public void importObjects(String dBname, List<Drtypemode> list) {
        int rows = 0;
        Integer typemodeid;
        for (Drtypemode drtypemode : list) {
            typemodeid = drtypemode.getTypemodeid();
            if (typemodeid == null || typemodeid == 0) {
                this.saveObject(dBname, drtypemode);
            } else {
                rows = drtypemodeMapper.findRowCountById(dBname, typemodeid);
                if (rows == 1) {
                    this.updateObject(dBname, drtypemode);
                } else {
                    this.saveObject(dBname, drtypemode);
                }
            }
        }

    }

    @Override
    public List<Drtypemode> findAllWriteByDrtypeid(String dBname, Integer drtypeid) {
        // TODO Auto-generated method stub
        return drtypemodeMapper.findAllWriteByDrtypeid(dBname, drtypeid);
    }

    @Override
    public void writeDrtypeMode(String dBname, Integer drtypeid, Integer appid, String msg) {
        // TODO Auto-generated method stub
        String[] msgArry = msg.split(",", -1);
        for (String s : msgArry) {
            String[] sarry = s.split("_", -1);
            String regShowlevel = sarry[0];
            String value = sarry[1];
            List<Reg> regList = drtypemodeMapper.findAllRegByLevel(dBname, drtypeid, regShowlevel);
            String sendmsg = "";
            for (Reg reg : regList) {
                if (reg.getTagName() != null && !reg.getTagName().equals("")) {
                    sendmsg = sendmsg + reg.getTagName() + ":" + value + ",";
                }
            }
            if (!sendmsg.equals("")) {
                sendmsg = sendmsg.substring(0, sendmsg.length() - 1);
            }
            qsLdJgrService.doimplements(dBname, appid, sendmsg, null);
            //修改typemode的控制值
            drtypemodeMapper.updateTypemodeValue(dBname, drtypeid, Integer.valueOf(regShowlevel), value);
        }
    }

}
