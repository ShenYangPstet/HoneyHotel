package com.photonstudio.serviceImpl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.mapper.*;
import com.photonstudio.pojo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.SessionUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.service.DrtypeinfoService;

@Service
public class DrtypeinfoServiceImpl extends ServiceImpl<DrtypeinfoMapper, Drtypeinfo> implements DrtypeinfoService {
    @Autowired
    private DrtypeinfoMapper drtypeinfoMapper;
    @Autowired
    private DrtypemodeMapper drtypemodeMapper;
    @Autowired
    private DrinfoMapper drinfoMapper;
    @Autowired
    private FileUploadProperteis fileUploadProperteis;

    @Autowired
    private QstagMapper qstagMapper;

    @Autowired
    private RegMapper regMapper;

    @Override
    public List<Drtypeinfo> findObjectByParentId(String dBname, Integer parentid) {

        return drtypeinfoMapper.findObjectByParentId(dBname, parentid, 0);

    }

    @Override
    public int deleteObject(String dBname, Integer drtypeid) {
        //1.对参数进行校验
        if (drtypeid == null)
            throw new IllegalArgumentException("请先选择");
        //删除子菜单
        try {
            findChildren(dBname, drtypeid);
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new ServiceException("子目录删除失败");
        }
        int rows = 0;
        try {//通过dao访问数据库服务器可能会有异常，此异常也可不再此位置处理
            if (dBname.equals("zsqy_v2")) {
                rows = drtypeinfoMapper.deleteTypeAndModeById(dBname, drtypeid);
            } else {
                rows = drtypeinfoMapper.deleteObjectById(dBname, drtypeid);
            }
        } catch (Throwable e) {
            e.printStackTrace();

            throw new ServiceException(e);
        }
        System.out.println("影响行数===>" + rows);
        return rows;
    }


    private void findChildren(String dBname, Integer drtypeid) {
        List<Integer> list = new ArrayList<>();
        list = drtypeinfoMapper.findChildrenId(dBname, drtypeid);
        if (list == null || list.size() < 1) {
            return;
        }
        for (Integer ChildrenId : list) {
            findChildren(dBname, ChildrenId);
            drtypeinfoMapper.deleteObjectById(dBname, ChildrenId);
        }
    }

    @Override
    public int saveObject(String dBname, Drtypeinfo drtypeinfo) {

        int rows = 0;
        try {
            rows = drtypeinfoMapper.insertObject(dBname, drtypeinfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("失败");
        }

        return rows;
    }

    @Override
    public int updateObject(String dBname, Drtypeinfo drtypeinfo) {
        int rows = 0;
        if (drtypeinfo.getParentid() == drtypeinfo.getDrtypeid())
            throw new IllegalArgumentException("不能隶属于自己");
        try {
            rows = drtypeinfoMapper.updateObject(dBname, drtypeinfo);
        } catch (Exception e) {

            e.printStackTrace();
            throw new ServiceException("失败");
        }

        return rows;
    }

    @Override
    public List<Drtypeinfo> findAllDrtypeAndDr(String dBname, Integer iscustomType) {
        // TODO Auto-generated method stub
        List<Drtypeinfo> drtypeinfoList = drtypeinfoMapper.findAllDrtypeAndDr(dBname, iscustomType);
//				System.out.println("drtypeinfoList==>"+drtypeinfoList);
        List<Drtypeinfo> drtypeinfoListResult = new ArrayList<>();
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {
            if (drtypeinfo.getParentid().intValue() == 0) {
                drtypeinfoListResult.add(drtypeinfo);
            }
        }
        for (Drtypeinfo drtypeinfo : drtypeinfoListResult) {
            drtypeinfo.setDrtypeinfoList(getChild(drtypeinfo.getDrtypeid(), drtypeinfoList, dBname));
        }
        return drtypeinfoListResult;
    }

    private List<Drtypeinfo> getChild(Integer drtypeid, List<Drtypeinfo> drtypeinfoList, String dBname) {
        // TODO Auto-generated method stub
        List<Drtypeinfo> childList = new ArrayList<>();
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {

            if (0 != drtypeinfo.getParentid()) {
                if (drtypeinfo.getParentid().intValue() == drtypeid.intValue()) {
                    if (!dBname.equals("zsqy_v2")) {
                        List<Drinfo> drinfos = drinfoMapper.findObject(dBname, drtypeinfo.getDrtypeid(), null, null, null, null, null, null, null);
                        drtypeinfo.setDrinfList(drinfos);
                    }
                    childList.add(drtypeinfo);
                }
            }
        }
        for (Drtypeinfo drtypeinfo : childList) {
            drtypeinfo.setDrtypeinfoList(getChild(drtypeinfo.getDrtypeid(), drtypeinfoList, dBname));
        }
        return childList;
    }

    @Override
    public List<Drinfo> findDrinfoByType(String dBname, Integer drtypeid, String drname) {
        // TODO Auto-generated method stub

        String staticAccessPath = fileUploadProperteis.getStaticAccessPath();
//				System.out.println("staticAccessPath=="+staticAccessPath);
        return drtypeinfoMapper.findDrinfoByDrtypeid(dBname, drtypeid, staticAccessPath, drname, null);
    }

    @Override
    public List<Drtypeinfo> findAllDrtypeJgt(String dBname) {
        // TODO Auto-generated method stub
        List<Drtypeinfo> drtypeinfoList = drtypeinfoMapper.findAllDrtypeJgtr(dBname);
        List<Drtypeinfo> drtypeinfoListResult = new ArrayList<>();
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {
            if (drtypeinfo.getParentid() == 0) {
                drtypeinfoListResult.add(drtypeinfo);
            }
        }
        for (Drtypeinfo drtypeinfo : drtypeinfoListResult) {
            drtypeinfo.setDrtypeinfoList(getChild(drtypeinfo.getDrtypeid(), drtypeinfoList, dBname));
        }
//				System.out.println("drtypeinfoListResult=="+drtypeinfoListResult);
        return drtypeinfoListResult;
    }

    @Override
    public PageObject<Drtypeinfo> findiscustom(String dBname, Integer pageCurrent,
                                               Integer pageSize) {
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<Drtypeinfo> list = new ArrayList<>();
        int startIndex = (pageCurrent - 1) * pageSize;
        int rowCount = drtypeinfoMapper.getRowCountByiscustomType(dBname, 1);
        list = drtypeinfoMapper.findObjectByiscustomType(dBname, 1, startIndex, pageSize);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public Drtypeinfo findDrtypeinfoByDrid(String dBname, Integer drid) {
        // TODO Auto-generated method stub
        Drtypeinfo drtypeinfo = new Drtypeinfo();
        try {
            drtypeinfo = drtypeinfoMapper.findDrtypeinfoByDrid(dBname, drid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return drtypeinfo;
    }

    @Override
    public List<Drtypeinfo> findAllByIscustom(String dBname, Integer iscustomType) {
        List<Drtypeinfo> list = new ArrayList<>();
        try {
            list = drtypeinfoMapper.findObjectByiscustomType(dBname, iscustomType, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Drtypeinfo> findAllDrtypevo(String dBname, Integer drtypeid) {
        List<Reg> reglist = regMapper.findRegByShowType(dBname, "1");
        Map<Integer, Reg> runmap = new HashMap<>();
        for (Reg reg : reglist) {
            runmap.put(reg.getDrId(), reg);
        }

        List<Drtypeinfo> Parentlist = new ArrayList<>();
        if (drtypeid == null || drtypeid < 1) {

            Parentlist = drtypeinfoMapper.findObjectByParentId(dBname, 0, 0);
        } else {
					/*Drtypeinfo drtypeinfo= drtypeinfoMapper.findObjectByDrtypeid(dBname,drtypeid);
					Parentlist.add(drtypeinfo);*/
            Parentlist = drtypeinfoMapper.findObjectByParentId(dBname, drtypeid, 0);
        }
        for (Drtypeinfo drtypeinfo : Parentlist) {
            int drinfoRunSum = 0;
            int drinfoAlarmSum = 0;
            int drmalfunctionSum = 0;
            List<Drinfo> list = new ArrayList<>();
            list = getAllChild(dBname, drtypeinfo.getDrtypeid());
            drtypeinfo.setDrinfoSum(list.size());
            if (list != null && list.size() > 0) {
                for (Drinfo drinfo : list) {

                    Reg reg = runmap.get(drinfo.getDrid());
                    if (reg != null) {
                        if (!StringUtils.isEmpty(reg.getTagName())) {
                            String value = regMapper.findQstagByName(dBname, reg.getTagName());
                            if (value != null && value.equals("1")) {
                                drinfoRunSum = drinfoRunSum + 1;
                            }
                        } else {
                            if (reg.getTagValue() != null && reg.getTagValue().equals("1")) {
                                drinfoRunSum = drinfoRunSum + 1;
                            }
                        }
                    }

                }
                List<Integer> childidlist = ChildDrtypeid(dBname, drtypeinfo.getDrtypeid());
                drinfoAlarmSum = regMapper.getCountByRegDrShowType(dBname, childidlist, "2", "1");
                drmalfunctionSum = regMapper.getCountByRegDrShowType(dBname, childidlist, "3", "1");
                ;
            }
            drtypeinfo.setDrinfoRunSum(drinfoRunSum);
            drtypeinfo.setDrinfoAlarmSum(drinfoAlarmSum);
            drtypeinfo.setDrinfomalfunctionSum(drmalfunctionSum);
        }
        return Parentlist;
    }

    private List<Integer> ChildDrtypeid(String dBname, Integer drtypeid) {
        List<Integer> childidlist = new ArrayList<>();
        List<Integer> chidid = drtypeinfoMapper.findChildrenId(dBname, drtypeid);
        System.out.println("子类型id==" + chidid);
        if (chidid == null || chidid.size() == 0) {
            childidlist.add(drtypeid);
            return childidlist;
        }
        for (Integer integer : chidid) {
            childidlist.addAll(0, ChildDrtypeid(dBname, integer));
        }
        System.out.println("最终子类型id==" + childidlist);
        return childidlist;
    }

    //获取子类型下的设备如果没有子类型直接获取设备
    private List<Drinfo> getAllChild(String dBname, Integer drtypeid) {
        Appuser appuser = SessionUtil.getAppuser(dBname);
        List<Integer> dridlist = new ArrayList<>();
        if (appuser != null && appuser.getUsertype() == 1 && appuser.getDridlist() != null && !appuser.getDridlist().isEmpty()) {
            dridlist = appuser.getDridlist();
        }
        List<Integer> chidid = drtypeinfoMapper.findChildrenId(dBname, drtypeid);
        if (chidid == null || chidid.size() == 0) {
            return drinfoMapper.findObject(dBname, drtypeid, null, null, dridlist, null, null, null, null);
        }
        List<Drinfo> drlist = new ArrayList<>();
        for (Integer integer : chidid) {
            drlist.addAll(0, getAllChild(dBname, integer));
        }

        return drlist;
    }

    @Override
    public Map<String, Double> findWorkorderByDrtype(String dBname) {
        List<Drtypeinfo> list = drtypeinfoMapper.findBasisDrtype(dBname);
        Date date3 = new Date();
        Map<String, Double> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        //昨天0点
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);
        Date date1 = calendar.getTime();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date date2 = calendar.getTime();
        for (Drtypeinfo drtypeinfo : list) {
            double a = drtypeinfoMapper.findWorkorderByDrtype(dBname, drtypeinfo.getDrtypeid(), date1, date2, date3);
            map.put(drtypeinfo.getDrtypename(), a);

        }
        return map;
    }

    @Override
    public void moveDrtypeAndModle(String dBname, String drtypeids) {
        // TODO Auto-generated method stub
        String[] drtypeidArry = drtypeids.split(",", -1);
        Integer drtypeid;
        String dBnameZ = "zsqy_v2";
        for (String drtypeidStr : drtypeidArry) {
            drtypeid = Integer.valueOf(drtypeidStr);
            Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBnameZ, drtypeid);
            List<Drtypemode> drtypemodeList = drtypeinfoMapper.findModeList(dBnameZ, drtypeid);
            if (drtypeinfo != null) {
                drtypeinfoMapper.deleteDrtypeinfoById(dBname, drtypeid);
                drtypeinfoMapper.insertObject(dBname, drtypeinfo);
                if (drtypemodeList != null) {
                    drtypemodeMapper.deleteDrtypemodeById(dBname, drtypeid);
                    for (Drtypemode drtypemode : drtypemodeList) {
                        drtypemodeMapper.insertObject(dBname, drtypemode);
                    }
                }
            }

        }
    }

    @Override
    public Drtypeinfo findDrtypeinfoBytypeid(String dBname, Integer drtypeid) {
        // TODO Auto-generated method stub
        return drtypeinfoMapper.findDrtypeinfoBytypeid(dBname, drtypeid);
    }

    @Override
    public List<Drtypeinfo> findBasisDrtype(String dBname) {
        // TODO Auto-generated method stub
        return drtypeinfoMapper.findBasisDrtype(dBname);
    }

    @Override
    public List<Idtree> findIdtree(String dBname, Integer id) {
        // TODO Auto-generated method stub
        List<Drtypeinfo> drtypeinfoList = drtypeinfoMapper.findAllDrtypeAndDr(dBname, 0);
        List<Idtree> idtrees = new ArrayList<>();
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {
            if (drtypeinfo.getParentid().intValue() == 0) {
                Idtree idtree = new Idtree();
                idtree.setId(drtypeinfo.getDrtypeid());
                idtree.setName(drtypeinfo.getDrtypename());
                idtree.setType(1);
                idtree.setChild(getChildIdtree(dBname, drtypeinfo.getDrtypeid(), drtypeinfoList));
                idtrees.add(idtree);
            }
        }
        return idtrees;
    }

    private List<Idtree> getChildIdtree(String dBname, Integer drtypeid, List<Drtypeinfo> drtypeinfoList) {
        // TODO Auto-generated method stub
        List<Idtree> ChildIdtrees = new ArrayList<>();
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {
            if (0 != drtypeinfo.getParentid()) {
                if (drtypeinfo.getParentid().intValue() == drtypeid.intValue()) {
                    Idtree idtree = new Idtree();
                    idtree.setId(drtypeinfo.getDrtypeid());
                    idtree.setName(drtypeinfo.getDrtypename());
                    idtree.setType(1);
                    ChildIdtrees.add(idtree);
                }
            }
        }
        //if(ChildIdtrees==null||ChildIdtrees.size()==0)return ChildIdtrees;
        for (Idtree idtree : ChildIdtrees) {
            idtree.setChild(getChildIdtree(dBname, idtree.getId(), drtypeinfoList));
        }
        return ChildIdtrees;
    }

    @Override
    public List<EcharsObject> findDrStateByDrtype(String dBname, Integer drtypeid) {
        // TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        List<Drinfo> drlist = getAllChild(dBname, drtypeid);
        List<Reg> reglist = regMapper.findShowTypeAllByDrtypeid(dBname, drtypeid);
        long time1 = System.currentTimeMillis();
        System.out.println("获取reg 设备时间时间===" + (time1 - start));
        int runStateSum = 0;
        int alarmStateSum = 0;
        int errorStateSum = 0;
        int stopStateSum = 0;
        for (Drinfo drinfo : drlist) {
            int drid = drinfo.getDrid();
            int runStateCnt = 0;
            int alarmStateCnt = 0;
            int errorStateCnt = 0;
            for (Reg reg : reglist) {
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("1")) {

                    //if(null != reg.getTagName() && !reg.getTagName().equals(""))
                    //{
                    String value = reg.getQstagvalue();
                    //System.out.println("查询运行tag=="+value);
                    if (value != null && value.equals("1")) {
                        runStateCnt = 1;
                    }
                    //}
                    else {
                        if (reg.getTagValue().equals("1")) {
                            runStateCnt = 1;
                        }
                    }
                }
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("2")) {
                    if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                        alarmStateCnt = 2;
                    }
                }
                if (reg.getDrId() == drid && reg.getRegDrShowType().equals("3")) {
                    if (null != reg.getTagAlarmState() && reg.getTagAlarmState().equals("1")) {
                        errorStateCnt = 4;
                    }
                }

            }
            int state = runStateCnt + alarmStateCnt + errorStateCnt;
            if (state == 0) {
                stopStateSum++;
            } else if ((state > 0 && state < 2)) {
                runStateSum++;
            } else if (state >= 2 && state < 4) {
                alarmStateSum++;
            } else {
                errorStateSum++;
            }


        }
        List<EcharsObject> echarsObjects = new ArrayList<>();
        EcharsObject echarsStop = new EcharsObject();
        echarsStop.setName("停止");
        echarsStop.setValue(stopStateSum + "");
        EcharsObject echarsRun = new EcharsObject();
        echarsRun.setName("运行");
        echarsRun.setValue(runStateSum + "");
        EcharsObject echarsError = new EcharsObject();
        echarsError.setName("故障");
        echarsError.setValue(errorStateSum + "");
        echarsObjects.add(echarsStop);
        echarsObjects.add(echarsRun);
        echarsObjects.add(echarsError);
        /*
         * Map<String, Integer>map=new HashMap<>(); map.put("停止", stopStateSum);
         * map.put("运行", runStateSum); map.put("报警", alarmStateSum); map.put("故障",
         * errorStateSum);
         */
        long time2 = System.currentTimeMillis();
        System.out.println("完成遍历时间===" + (time2 - time1));
        return echarsObjects;
    }

    @Override
    public List<Drtypeinfo> findAllStatusVos(String dBname, List<Integer> integerList) {
        List<Reg> reglist = regMapper.selectList(new LambdaQueryWrapper<Reg>().eq(Reg::getRegDrShowType, "1"));
        Map<Integer, Reg> runmap = new HashMap<>();
        for (Reg reg : reglist) {
            runmap.put(reg.getDrId(), reg);
        }
        List<Qstag> qsTagList = qstagMapper.selectList(null);
        Map<String, String> tagNameToQsTagList = qsTagList.stream().collect(Collectors.toMap(Qstag::getTagname, Qstag::getTagvalue));
        List<Drtypeinfo> drtypeinfoList = drtypeinfoMapper.selectList(new LambdaQueryWrapper<Drtypeinfo>().in(!ObjectUtil.isEmpty(integerList), Drtypeinfo::getDrtypeid, integerList));
        for (Drtypeinfo drtypeinfo : drtypeinfoList) {
            int drinfoRunSum = 0;
            int drinfoAlarmSum = 0;
            int drmalfunctionSum = 0;
            List<Drinfo> list = new ArrayList<>();
            list = getAllChild(dBname, drtypeinfo.getDrtypeid());
            drtypeinfo.setDrinfoSum(list.size());
            for (Drinfo drinfo : list) {
                Reg reg = runmap.get(drinfo.getDrid());
                if (!ObjectUtil.isEmpty(reg)) {
                    if (!StringUtils.isEmpty(reg.getTagName())) {
                        String value = tagNameToQsTagList.get(reg.getTagName());
                        if (value != null && value.equals("1")) {
                            drinfoRunSum = drinfoRunSum + 1;
                        }
                    } else {
                        if (reg.getTagValue() != null && reg.getTagValue().equals("1")) {
                            drinfoRunSum = drinfoRunSum + 1;
                        }
                    }
                }

            }
            List<Integer> childidlist = ChildDrtypeid(dBname, drtypeinfo.getDrtypeid());
            drinfoAlarmSum = regMapper.getCountByRegDrShowType(dBname, childidlist, "2", "1");
            drmalfunctionSum = regMapper.getCountByRegDrShowType(dBname, childidlist, "3", "1");
            drtypeinfo.setDrinfoRunSum(drinfoRunSum);
            drtypeinfo.setDrinfoAlarmSum(drinfoAlarmSum);
            drtypeinfo.setDrinfomalfunctionSum(drmalfunctionSum);
        }
        return drtypeinfoList;
    }
    @Override
    public List<Integer> listDrTypeIdByParentId(Collection<Integer> parentIds) {
        List<Integer> drTypeIds = new ArrayList<>(parentIds);
        while (CollectionUtils.isNotEmpty(parentIds)) {
            List<Drtypeinfo> drTypeInfos = lambdaQuery().select(Drtypeinfo::getDrtypeid).in(Drtypeinfo::getParentid, parentIds).list();
            drTypeInfos.forEach(drtypeinfo -> drTypeIds.add(drtypeinfo.getDrtypeid()));
            parentIds = drTypeInfos.stream().map(Drtypeinfo::getDrtypeid).collect(Collectors.toList());
        }
        return drTypeIds;
    }

    @Override
    public List<Integer> listDrTypeIdByParentId(Integer parentId) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        return listDrTypeIdByParentId(Collections.singletonList(parentId));
    }
}
