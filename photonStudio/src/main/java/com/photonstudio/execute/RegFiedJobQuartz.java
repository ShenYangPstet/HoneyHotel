package com.photonstudio.execute;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.enums.SendType;
import com.photonstudio.mapper.AppmanagerMapper;
import com.photonstudio.mapper.QsMsMapper;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Qsmsjblog;
import com.photonstudio.pojo.Qsmsjg;
import com.photonstudio.pojo.vo.SendVo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisallowConcurrentExecution
public class RegFiedJobQuartz implements Job{
	@Autowired
	private QsMsMapper qsMsMapper;
	@Autowired
	private AppmanagerMapper appmanagerMapper;
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("start ---------");
		JobDataMap jdMap=context.getJobDetail().getJobDataMap();
		String dBname = jdMap.getString("dBname");
		Integer dzid = jdMap.getInt("dzid");
		Integer appid = jdMap.getInt("appid");
		List<Qsmsjg> qsmsjgList = qsMsMapper.findQsmsjg(dBname, dzid);
		Appmanager appmanager = appmanagerMapper.findObjectByAppid(appid);
		String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport() +"/caiji/ConsoleSend";
		if(qsmsjgList.size()>0)
		{
			for(Qsmsjg qsmsjg : qsmsjgList)
			{
						Qsmsjblog qsmsjblog = new Qsmsjblog();
						qsmsjblog.setDzid(dzid);
						qsmsjblog.setDzname(qsmsjg.getDzname());
						qsmsjblog.setRegname(qsmsjg.getRegName());
						qsmsjblog.setValue(qsmsjg.getValue());
						qsmsjblog.setSendtime(new Date());
				if (!StringUtils.isEmpty(qsmsjg.getTagName())
						&& !StringUtils.isEmpty(qsmsjg.getValue()))
				{
					try
					{
						Map<String, String> sendValueMap = new HashMap<>();
						sendValueMap.put(qsmsjg.getTagName(), qsmsjg.getValue());
						SendVo sendVo = new SendVo(SendType.CONSOLE.sendType, sendValueMap);
						HttpHeaders headers = new HttpHeaders();
						MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
						headers.setContentType(type);
						HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(sendVo), headers);
						restTemplate.postForEntity(url, formEntity, String.class);
						//System.out.println("tagname="+qsmsjg.getTagName()+"||value="+qsmsjg.getValue());
						//restTemplate.getForObject(url+"/caiji/reg/" + qsmsjg.getTagName() + "/" + qsmsjg.getValue(), SysResult.class);
						qsmsjblog.setState(0);

//						SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//						Date date = new Date(System.currentTimeMillis());
//						System.out.println(formatter.format(date));

						//获得当前时间
						Date date = new Date(System.currentTimeMillis());
						qsmsjblog.setFinshtime(date);
						qsMsMapper.insertQsmsjblog(dBname, qsmsjblog);
						
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						qsmsjblog.setState(1);
						qsMsMapper.insertQsmsjblog(dBname, qsmsjblog);

					}
				}
			}
		}
	}

}
