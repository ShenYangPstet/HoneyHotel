package com.photonstudio.execute;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.IPUtil;
import com.photonstudio.common.UserThreadLocal;
import com.photonstudio.common.annotation.OperationLogAnnotation;
import com.photonstudio.mapper.*;
import com.photonstudio.pojo.*;
import com.photonstudio.service.QsSystemlogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Aspect
@Component
public class OperationLogAspect {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Resource
    private QsSystemlogService qsSystemlogService;

    @Resource
    private AppsysuserMapper appsysuserMapper;

    @Resource
    private DrtypeinfoMapper drtypeinfoMapper;

    @Resource
    private QsMsMapper qsMsMapper;

    @Resource
    private SubinfoMapper subinfoMapper;

    @Resource
    private RegMapper regMapper;

    @Resource
    private QsSystemlogMapper qsSystemlogMapper;
    //获取删除之前的数据
    private List<Qslinkrw> qslinkrw;
    private List<Energyinfo> energyinfoList;
    private Qsmsrw qsmsrwList;
    private Qsmsdz qsmsdzlist;

    private List<Qsmsjg> qsmsjglsit;


    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.photonstudio.common.annotation.OperationLogAnnotation)")
    public void operLogPoinCut() {

    }

    @Before(value = "operLogPoinCut()")
    public void doAround(JoinPoint joinPoint) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.
                resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取方法名
        String methodname =signature.getName();
        //获取参数将参数放在map中
        Map<String, String> rtnMap = converMap(request.getParameterMap());
        //System.out.println(rtnMap);
        //获取uri上的参数
        Map<String, String> pathVars =  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        //获取dBname
        String dBname = pathVars.get("dBname");
        //获取方法上的注解
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation.Linkagetask().equals("删除")){
            String ids  = rtnMap.get("ids");
            String  id[] = ids.split(",");
            System.out.println(ids);
            qslinkrw = qsSystemlogService.findByids(dBname,id);
        }
        if (annotation.Energy().equals("删除")){
            String ids = rtnMap.get("ids");
            String id[] = ids.split(",");
            energyinfoList = qsSystemlogService.findEnergyinfo(dBname,id);
        }
        if (annotation.Modetask().equals("任务模式删除")){
            Integer ids = Integer.valueOf(rtnMap.get("id"));
            qsmsrwList = qsSystemlogMapper.findAll(dBname,ids);
        }
        if (annotation.Modetask().equals("任务动作删除")){
            Integer id = Integer.valueOf(rtnMap.get("id"));
            qsmsdzlist = qsMsMapper.findMsDzById(dBname,id);
        }
        if (annotation.Modetask().equals("配置结果删除")){
            String ids = rtnMap.get("ids");
            String id [] = ids.split(",");
            qsmsjglsit = qsSystemlogMapper.findAllByid(dBname,id);
        }
    }


    @AfterReturning(returning = "result", value = "operLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable{
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.
                resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try {
            //将返回值换成map集合
            Object map =  result;
            QsSystemlog qsSystemlog = new QsSystemlog();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取方法名
            String methodname =signature.getName();

            //获取参数将参数放在map中
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            //获取uri上的参数
            Map<String, String> pathVars =  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            //获取dBname
            String dBname = pathVars.get("dBname");
            //获取方法上的注解
            OperationLogAnnotation  annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation.Mass() == true){ // 启停控制 下发命令群发
                this.SetMass(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method);
            }
            if (annotation.singleShot()==true){//启停控制 下发命令 单发
                this.SetsingleShot(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method);
            }
            if (annotation.Linkagetask().equals("新增")){//联动新增
                this.Linkagetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }

            if (annotation.Linkagetask().equals("修改")){//联动修改
                this.UpdateLinkagetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Linkagetask().equals("删除")){//联动删除
                this.DeletedLinkagetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Energy().equals("新增")){//能耗新增
                this.SaveEnergy(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Energy().equals("修改")){//能耗修改
                this.UpdateSaveEnergy(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Energy().equals("删除")){//能耗删除
                this.DeleteEnergy(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if(annotation.implementAlarm()==true){//报警应答操作
                this.implementAlarm(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("任务模式新增")){
                this.Modetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("任务模式修改")){
                this.updateModetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("任务模式删除")){
                this.DeleteModetask(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if(annotation.Modetask().equals("任务动作新增")){
                this.ModetaskDz(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("任务动作修改")){
                this.UpdateModetaskDz(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("任务动作删除")){
                this.DeleteModetaskDz(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.ModetaskTime().equals("时间模式新增")){
                this.insetModetaskTime(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("配置结果新增")){
                this.ModetaskEditSave(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("配置结果修改")){
                this.ModetaskEditSave(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }
            if (annotation.Modetask().equals("配置结果删除")){
                this.ModetaskEditDelete(joinPoint,result,rtnMap,request,qsSystemlog,methodname,method,dBname);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    };




    /**
     * 设备控制 群发下发操作
     */
    public void SetMass(JoinPoint joinPoint, Object result,
                        Map<String, String> rtnMap,HttpServletRequest
                                request,QsSystemlog qsSystemlog,String methodname, Method method) {
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String msg = rtnMap.get("msg");
        //System.out.println(request.getRequestURI());
        //获取uri上的参数
        Map<String, String> pathVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        //获取dBname
        String dBname = pathVars.get("dBname");
        //System.out.println("dBname====" + dBname);
        if (msg != null) {
            String tagname = "";
            String value = "";
            //System.out.println("msg====" + msg);
            String[] split = msg.split(",");
            for (String string : split) {
                String[] split2 = string.split(":");
                tagname = split2[0];
                value = split2[1];
            }
            List<Reg> regList = qsSystemlogService.findByRegState(dBname, tagname, null);
            //System.out.println(regList);
            for (Reg reg : regList) {
                Drinfo drinfo = qsSystemlogService.findDrinfo(dBname, tagname);
                String type = "";
                List<Subinfo> subList = subinfoMapper.findObject(dBname,null,null,null);
                for (Subinfo subinfo : subList) {
                    if (subinfo.getSubid()==Integer.parseInt(reg.getRegSub())){
                        if (value.equals(subinfo.getValue())){
                            type = subinfo.getText();
                        }
                    }
                }

                String regname = reg.getRegName().split(":", -1)[1];
                String describe = "设备控制：" + drinfo.getDrname() + "，变量名:" + regname + "，修改为：" + type;
                qsSystemlog.setOperationDescribe(describe);

                //获取操作
                OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
                if (annotation != null) {
                    qsSystemlog.setOperationType(annotation.operModul());//操作模块
                    qsSystemlog.setType(annotation.operType());//操作类型
                }
                UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
            }
        }

    }




    /**
     * 设备控制 单发 下发操作
     */

    public void SetsingleShot(JoinPoint joinPoint, Object result,
                              Map<String, String> rtnMap,HttpServletRequest
                                      request,QsSystemlog qsSystemlog,String methodname, Method method){

        System.out.println("单发操作！");
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String tagname = rtnMap.get("tagname");
        String value = rtnMap.get("tagvalue");
        //System.out.println(request.getRequestURI());
        //获取uri上的参数
        Map<String, String> pathVars =  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        //获取dBname
        String dBname = pathVars.get("dBname");
        System.out.println("dBname===="+dBname);
        if (tagname!=null&&value!=null){
            List<Reg> regList = qsSystemlogService.findByRegState(dBname, tagname,null);
            for (Reg reg:regList){
                Drinfo drinfo = qsSystemlogService.findDrinfo(dBname,tagname);
                String type = "";
                List<Subinfo> subList = subinfoMapper.findObject(dBname,null,null,null);
                for (Subinfo subinfo : subList) {
                    if (subinfo.getSubid()==Integer.parseInt(reg.getRegSub())){
                        if (value.equals(subinfo.getValue())){
                            type = subinfo.getText();
                        }
                    }
                }

                //变量名称
                String regname = reg.getRegName().split(":", -1)[1];
                String describe = "设备控制："+drinfo.getDrname()+"，变量名：" +regname+"，修改为：" + type;
                qsSystemlog.setOperationDescribe(describe);
                //获取操作
                OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
                if (annotation != null) {
                    qsSystemlog.setOperationType(annotation.operModul());//操作模块
                    qsSystemlog.setType(annotation.operType());//操作类型
                }
                UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
            }
        }


    }

    /**
     * 联动配置新增操作
     */
    public void Linkagetask(JoinPoint joinPoint, Object result,
                            Map<String, String> rtnMap,HttpServletRequest
                                    request,QsSystemlog qsSystemlog,String methodname,
                            Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String rwName = rtnMap.get("rwName");//任务名称
        Integer tjId = Integer.valueOf(rtnMap.get("tjId"));//条件id;
        Integer jgId = Integer.valueOf(rtnMap.get("jgId"));//结果id;

        List<Qslinkrw> qslinkrwList = qsSystemlogService.findQslinByid(dBname,tjId,jgId);
        //System.out.println(qslinkrwList);
        for (Qslinkrw qslinkrw :qslinkrwList){
            String describe = "联动配置新增："+"联动任务名："+qslinkrw.getRwName()+"，联动条件："+qslinkrw.getTjName()+"，联动结果为："+qslinkrw.getJgName();
            qsSystemlog.setOperationDescribe(describe);

        }
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }
    /*
      删除联动配置操作
     */
    public void DeletedLinkagetask(JoinPoint joinPoint, Object result,
                                   Map<String, String> rtnMap,HttpServletRequest
                                           request,QsSystemlog qsSystemlog,String methodname,
                                   Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);
        String ids = rtnMap.get("ids");
        List<Qslinkrw> qslinkrwList = qslinkrw;
        for (Qslinkrw qslinkrw :qslinkrwList) {
            //System.out.println(qslinkrwList);
            String describe = "删除联动配置：删除的联动任务名称：" + qslinkrw.getRwName();
            qsSystemlog.setOperationDescribe(describe);

            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                qsSystemlog.setOperationType(annotation.operModul());//操作模块
                qsSystemlog.setType(annotation.operType());//操作类型
            }
            UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
        }
    }

    /**
     * 修改联动配置操作
     */
    public void UpdateLinkagetask(JoinPoint joinPoint, Object result,
                                  Map<String, String> rtnMap,HttpServletRequest
                                          request,QsSystemlog qsSystemlog,String methodname,
                                  Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String rwId = rtnMap.get("rwId");//修改的id
        String rwName = rtnMap.get("rwName");//任务名称
        Integer tjId = Integer.valueOf(rtnMap.get("tjId"));//条件id;
        Integer jgId = Integer.valueOf(rtnMap.get("jgId"));//结果id;

        List<Qslinkrw> qslinkrwList = qsSystemlogService.findQslinByid(dBname,tjId,jgId);
        //System.out.println(qslinkrwList);
        for (Qslinkrw qslinkrw :qslinkrwList){

            String describe = "联动配置修改:"+"修改的id为:"+rwId+",联动任务名为:"+qslinkrw.getRwName()+",联动条件为:"+qslinkrw.getTjName()+",联动结果为:"+qslinkrw.getJgName();
            qsSystemlog.setOperationDescribe(describe);
        }
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作

    }

    /**
     * 能耗配置新增
     */
    public void SaveEnergy(JoinPoint joinPoint, Object result,
                           Map<String, String> rtnMap,HttpServletRequest
                                   request,QsSystemlog qsSystemlog,String methodname,
                           Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String energytypename = rtnMap.get("energytypename");//能耗名称
        String energytypeExplain = rtnMap.get("energytypeExplain");//能耗描述
        String  tagnames = rtnMap.get("tagnames");
        String energytypeid = rtnMap.get("energytypeid");//能耗类型 1总能耗 2分类能耗 3 自定义能耗
        String isapptotal = rtnMap.get("isapptotal");//是否为项目总能耗  0不是 1是
        String isapptotalType="";
        if (Integer.parseInt(isapptotal)==0){
            isapptotalType="否";
        }else {
            isapptotalType ="是";
        }
        List<Reg> regList = qsSystemlogService.findByRegState(dBname,tagnames,"1");
        for (Reg reg:regList){
            Drinfo drinfo = qsSystemlogService.findDrinfo(dBname,tagnames);
            String regname = reg.getRegName().split(":", -1)[1];
            String type="";
            if (Integer.parseInt(energytypeid)==1){
                type="总能耗";
            }else if (Integer.parseInt(energytypeid)==2){
                type="分类能耗";
            }else {
                type="自定义能耗";
            }

            String describe ="能耗配置信息，添加的设备："+drinfo.getDrname()+",变量名称："+regname+"，能耗类型"+type+"，能耗名称为："
                    +energytypename+"，能耗描述："+energytypeExplain+"，是否为项目总能耗："+isapptotalType;
            qsSystemlog.setOperationDescribe(describe);
        }
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作

    }
    /**
     * 能耗配置修改
     */

    public void UpdateSaveEnergy(JoinPoint joinPoint, Object result,
                                 Map<String, String> rtnMap,HttpServletRequest
                                         request,QsSystemlog qsSystemlog,String methodname,
                                 Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);

        String energytypename = rtnMap.get("energytypename");//能耗名称
        String energytypeExplain = rtnMap.get("energytypeExplain");//能耗描述
        String  tagnames = rtnMap.get("tagnames");
        String energytypeid = rtnMap.get("energytypeid");//能耗类型 1总能耗 2分类能耗 3 自定义能耗
        String type="";
        if (Integer.parseInt(energytypeid)==1){
            type="总能耗";
        }else if (Integer.parseInt(energytypeid)==2){
            type="分类能耗";
        }else {
            type="自定义能耗";
        }
//        if (tagnames!=null){
//            List<Reg> regList = qsSystemlogService.findByRegState(dBname,tagnames,"1");
//            for (Reg reg:regList){
//                String regname = reg.getRegName().split(":", -1)[1];
//                Drinfo drinfo = qsSystemlogService.findDrinfo(dBname,tagnames);
//                String describe ="能耗配置信息，设备："+drinfo.getDrname()+",变量名称："+regname+"，能耗类型："+type+"，能耗名称修改为："+energytypename+"，能耗描述："+energytypeExplain;
//                qsSystemlog.setOperationDescribe(describe);
//            }
//        }
        String describe ="修改能耗配置："+"能耗名称修改为："+energytypename+"，能耗描述："+energytypeExplain+"，能耗类型："+type;
        qsSystemlog.setOperationDescribe(describe);


        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }

    /**
     * 能耗配置删除
     */

    public void DeleteEnergy(JoinPoint joinPoint, Object result,
                             Map<String, String> rtnMap,HttpServletRequest
                                     request,QsSystemlog qsSystemlog,String methodname,
                             Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        //System.out.println(params);
        String ids = rtnMap.get("ids");
        List<Energyinfo> energyinfos = energyinfoList;
        for (Energyinfo energyinfo :energyinfos) {
            String type="";
            if (energyinfo.getEnergytypeid()==1){
                type="总能耗";
            }else if (energyinfo.getEnergytypeid()==2){
                type="分类能耗";
            }else {
                type="自定义能耗";
            }
            String isapptotalType="";
            if (energyinfo.getIsapptotal()==0){
                isapptotalType="否";
            }else {
                isapptotalType ="是";
            }
            //System.out.println(energyinfo);
            String describe = "删除能耗配置：删除的能耗类型：" +type+"，能耗名称："+energyinfo.getEnergytypename()+"，是否删除是项目总能耗："+isapptotalType;
            qsSystemlog.setOperationDescribe(describe);

            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                qsSystemlog.setOperationType(annotation.operModul());//操作模块
                qsSystemlog.setType(annotation.operType());//操作类型
            }
            UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
        }


    }


    /**
     * 实时报警应答
     */
    public void implementAlarm(JoinPoint joinPoint, Object result,
                               Map<String, String> rtnMap,HttpServletRequest
                                       request,QsSystemlog qsSystemlog,String methodname,
                               Method method,String dBname){

        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        String alarmanswer = rtnMap.get("alarmanswer");//报警应答 1报警 2误报 3测试
        String alarmhandle = rtnMap.get("alarmhandle");//报警应答说明
        String  id = rtnMap.get("id");//报警id
        List<QsAlarmlog> qsAlarmlogList = qsSystemlogService.findQsAlarmalogBy(dBname,id);
        System.out.println(qsAlarmlogList);
        for (QsAlarmlog qsAlarmlog:qsAlarmlogList){
            String type="";
            if (Integer.parseInt(alarmanswer)==1){
                type="报警";
            } else if (Integer.parseInt(alarmanswer)==2){
                type="误报";
            }else {
                type="测试";
            }
            String describe ="报警应答，"+"设备名："
                    +qsAlarmlog.getDrname()+"，变量名："+
                    qsAlarmlog.getRegName()+"，参数："+qsAlarmlog.getAlarmvalue()+"，级别："
                    +qsAlarmlog.getAlarmtypename()+"，报警描述："+qsAlarmlog.getAlarmexplain()+"，应答："+type
                    +"，报警应答说明："+alarmhandle;
            qsSystemlog.setOperationDescribe(describe);

        }

        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }

    /**
     * 模式任务新增
     */
    public void Modetask(JoinPoint joinPoint, Object result,
                         Map<String, String> rtnMap,HttpServletRequest
                                 request,QsSystemlog qsSystemlog,String methodname,
                         Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Integer drtypeid = Integer.valueOf(rtnMap.get("drtypeid"));//设备类型id
        String msname = rtnMap.get("msname");//模式名;
        String type = rtnMap.get("type"); //1左上角 2右上角 3 左下角 4右下角  5整块
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,drtypeid);
        System.out.println(drtypeinfo);
        String  mstype ="";
        if (Integer.parseInt(type)==1) mstype="左上角";
        if (Integer.parseInt(type)==2) mstype="右上角";
        if (Integer.parseInt(type)==3) mstype="左下角";
        if (Integer.parseInt(type)==4) mstype="右下角";
        if (Integer.parseInt(type)==5) mstype="整块";
        String describe ="模式任务新增，"+"模式名："
                +msname+"，模式标记："+mstype+"，设备类型："+drtypeinfo.getDrtypename();
        qsSystemlog.setOperationDescribe(describe);

        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }

    /**
     * 模式任务修改
     */
    public void updateModetask(JoinPoint joinPoint, Object result,
                               Map<String, String> rtnMap,HttpServletRequest
                                       request,QsSystemlog qsSystemlog,String methodname,
                               Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Integer id = Integer.valueOf(rtnMap.get("drtypeid"));//修改的id
        String msname = rtnMap.get("msname");//模式名;
        String type = rtnMap.get("type"); //1左上角 2右上角 3 左下角 4右下角 5整块
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,id);

        String  mstype ="";
        if (Integer.parseInt(type)==1) mstype="左上角";
        if (Integer.parseInt(type)==2) mstype="右上角";
        if (Integer.parseInt(type)==3) mstype="左下角";
        if (Integer.parseInt(type)==4) mstype="右下角";
        if (Integer.parseInt(type)==5) mstype="整块";
        String describe ="模式任务修改，"+"模式名："
                +msname+"，模式标记："+mstype+"，设备类型："+drtypeinfo.getDrtypename();
        qsSystemlog.setOperationDescribe(describe);

        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }
    /*
     模式任务删除
     */
    public void DeleteModetask(JoinPoint joinPoint, Object result,
                               Map<String, String> rtnMap,HttpServletRequest
                                       request,QsSystemlog qsSystemlog,String methodname,
                               Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Qsmsrw qsmsrw = qsmsrwList;
        System.out.println(qsmsrw);
        //1左上角 2右上角 3 左下角 4右下角 5整块
        String  mstype ="";
        if (qsmsrw.getType()==1) mstype="左上角";
        if (qsmsrw.getType()==2) mstype="右上角";
        if (qsmsrw.getType()==3) mstype="左下角";
        if (qsmsrw.getType()==4) mstype="右下角";
        if (qsmsrw.getType()==5) mstype="整块";
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,qsmsrw.getDrtypeid());

        String describe ="模式任务删除，"+"模式名："
                +qsmsrw.getMsname()+"，模式标记："+mstype+"，设备类型："+drtypeinfo.getDrtypename();
        qsSystemlog.setOperationDescribe(describe);

        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }

    /*
    模式任务动作新增
     */
    public void ModetaskDz(JoinPoint joinPoint, Object result,
                           Map<String, String> rtnMap,HttpServletRequest
                                   request,QsSystemlog qsSystemlog,String methodname,
                           Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Integer rwid = Integer.valueOf(rtnMap.get("rwid"));//任务id
        String time = rtnMap.get("time");//时间点
        String dzname = rtnMap.get("dzname");//动作名字
        Qsmsrw  qsmsrw = qsSystemlogMapper.findAll(dBname,rwid);
        System.out.println(qsmsrw);
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,qsmsrw.getDrtypeid());
        System.out.println(drtypeinfo);
        String describe ="任务动作新增，"+"模式名："
                +qsmsrw.getMsname()+"，设备类型："+drtypeinfo.getDrtypename()
                +"，动作名"+dzname+"，时间点："+time;
        qsSystemlog.setOperationDescribe(describe);
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }
    /*
    任务动作修改
     */
    public void UpdateModetaskDz(JoinPoint joinPoint, Object result,
                                 Map<String, String> rtnMap, HttpServletRequest
                                         request, QsSystemlog qsSystemlog, String methodname,
                                 Method method, String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Integer rwid = Integer.valueOf(rtnMap.get("rwid"));//任务id
        String time = rtnMap.get("time");//时间点
        String dzname = rtnMap.get("dzname");//动作名字
        Qsmsrw qsmsrw = qsSystemlogMapper.findAll(dBname,rwid);
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,qsmsrw.getDrtypeid());
        String describe ="任务动作修改，"+"模式名："
                +qsmsrw.getMsname()+"，设备类型："+drtypeinfo.getDrtypename()
                +"，动作名"+dzname+"，时间点："+time;
        qsSystemlog.setOperationDescribe(describe);
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
    }
    /*
    任务动作删除
     */
    public void DeleteModetaskDz(JoinPoint joinPoint, Object result,
                                 Map<String, String> rtnMap,HttpServletRequest
                                         request,QsSystemlog qsSystemlog,String methodname,
                                 Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        Qsmsdz qsmsdz = qsmsdzlist;
        Qsmsrw  qsmsrw = qsSystemlogMapper.findAll(dBname,qsmsdz.getRwid());
        Drtypeinfo drtypeinfo = drtypeinfoMapper.findObjectByDrtypeid(dBname,qsmsrw.getDrtypeid());
        String describe ="任务动作删除，"+"模式名："
                +qsmsrw.getMsname()+"，设备类型："+drtypeinfo.getDrtypename()
                +"，动作名:"+qsmsdz.getDzname()+"，时间点："+qsmsdz.getTime();
        qsSystemlog.setOperationDescribe(describe);
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作

    }

    /*
      模式编辑-配置新增和修改
     */
    public void ModetaskEditSave(JoinPoint joinPoint, Object result,
                                 Map<String, String> rtnMap,HttpServletRequest
                                         request,QsSystemlog qsSystemlog,String methodname,
                                 Method method,String dBname){

        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);

        String dzid=rtnMap.get("dzid");//动作id
        String regids =rtnMap.get("regids"); //变量id
        String value = rtnMap.get("value");//变量值

        String regid[] = regids.split(",");

        List<Reg> regList = qsSystemlogMapper.indRegfById(dBname,regid);
        Qsmsdz qsmsdz = qsMsMapper.findMsDzById(dBname, Integer.valueOf(dzid));
        for (Reg reg : regList) {
            String describe ="联动结果："+qsmsdz.getDzname()+"，变量名："+reg.getRegName()
                    +"，变量值："+value;
            qsSystemlog.setOperationDescribe(describe);

            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                qsSystemlog.setOperationType(annotation.operModul());//操作模块
                qsSystemlog.setType(annotation.operType());//操作类型
            }
            UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
        }



    }
    /*
    模式编辑-配置删除
   */
    public void ModetaskEditDelete(JoinPoint joinPoint, Object result,
                                   Map<String, String> rtnMap,HttpServletRequest
                                           request,QsSystemlog qsSystemlog,String methodname,
                                   Method method,String dBname){

        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
        List<Qsmsjg> qsmsjgList  = qsmsjglsit;
        for (Qsmsjg qsmsjg : qsmsjgList) {
            Qsmsdz qsmsdz = qsMsMapper.findMsDzById(dBname,qsmsjg.getDzid());
            Reg reg = regMapper.findRegByid(dBname,qsmsjg.getRegid());
            String describe ="联动结果："+qsmsdz.getDzname()+"，变量名："+reg.getRegName()
                    +"，变量值："+qsmsjg.getValue();
            qsSystemlog.setOperationDescribe(describe);
            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                qsSystemlog.setOperationType(annotation.operModul());//操作模块
                qsSystemlog.setType(annotation.operType());//操作类型
            }
            UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
        }


    }



    /*
       时间模式 新增
     */
    public void insetModetaskTime(JoinPoint joinPoint, Object result,
                                  Map<String, String> rtnMap,HttpServletRequest
                                          request,QsSystemlog qsSystemlog,String methodname,
                                  Method method,String dBname){
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);

        String ymds = rtnMap.get("ymds");//时间
        String rwids = rtnMap.get("rwids");//任务id

        String rwid[] = rwids.split(",");
        String ymd [] = ymds.split(",");
        List<Qsmsrw> qsmsrwList = qsSystemlogMapper.findById(dBname,rwid);
        for (Qsmsrw qsmsrw : qsmsrwList) {
            Drtypeinfo drtypeinfo = drtypeinfoMapper.findDrtypeinfoByDrid(dBname,qsmsrw.getDrtypeid());
            for (int i=0;i<ymd.length;i++){
                String describe ="任务名称："+qsmsrw.getMsname()+"，执行时间："+ymd[i];
                qsSystemlog.setOperationDescribe(describe);
                //获取操作
                OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
                if (annotation != null) {
                    qsSystemlog.setOperationType(annotation.operModul());//操作模块
                    qsSystemlog.setType(annotation.operType());//操作类型
                }
                UserlogSystem(qsSystemlog,methodname,method,dBname,params,request,result);//添加操作
            }
        }

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     * @param exceptionName
     * @param exceptionMessage
     * @param elements
     * @return
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" +
                exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }



    //添加到日志表里面
    public  void UserlogSystem(QsSystemlog qsSystemlog,String methodname,
                               Method method,String dBname,String params,HttpServletRequest
                                       request,Object result){
        //获取操作
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        if (annotation != null) {
            qsSystemlog.setOperationType(annotation.operModul());//操作模块
            qsSystemlog.setType(annotation.operType());//操作类型
        }
        //获取用户信息
        String tokenh = request.getHeader("ZSQY_TEST");
        Usertoken usertoken = appsysuserMapper.findUsertokenByToken(tokenh);
        Appsysuser user = appsysuserMapper.findAppsysuserByUsername(usertoken.getUsername());
        request.setAttribute("ZSQY_USER", user);
        UserThreadLocal.set(user);
        //用户名
        qsSystemlog.setUserCode(user.getUsername());
        //方法名
        qsSystemlog.setMethod(methodname);
        //参数
        qsSystemlog.setParameter(params);
        //地址ip
        qsSystemlog.setIp(IPUtil.getIp(request));
        //操作时间
        qsSystemlog.setOperationTime(sdf.format(new Date()));
        //ip地址
        qsSystemlog.setIp(IPUtil.getIp(request));
        String json =JSON.toJSONString(result);
        JSONObject obj = JSON.parseObject(json);
        if (obj.get("status").equals(20000)){
            qsSystemlog.setState(1);
        }else {
            qsSystemlog.setState(0);
        }

        //入数据库
        qsSystemlogService.SaveLog(dBname, qsSystemlog);

    }
}
