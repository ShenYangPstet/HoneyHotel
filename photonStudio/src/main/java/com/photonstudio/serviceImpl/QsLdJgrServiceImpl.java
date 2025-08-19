package com.photonstudio.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.enums.SendType;
import com.photonstudio.mapper.*;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.vo.SendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.service.QsLdJgrService;

import javax.servlet.http.HttpServletRequest;

@Service
@PropertySource(value = "classpath:/properties/ipaddress.properties")
public class QsLdJgrServiceImpl implements QsLdJgrService {
	@Autowired
	private QsLdJgrMapper qsLdJgrMapper;
	@Value("${ip.address}")
	private String Consumerip;
	@Autowired
	private RegMapper regMapper;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private QsLdLogMapper qsLdLogMapper;
	@Autowired
	private AppmanagerMapper appmanagerMapper;
	@Autowired
	private SmsLogMapper smsLogMapper;
	@Autowired
	private PicMapper picMapper;

	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private QsSystemlogMapper qsSystemlogMapper;
	@Override
	public PageObject<QsLdJgr> findObject(String dBname, Integer ldLxId, Integer pageCurrent, Integer pageSize) {
		if (pageCurrent == null || pageCurrent < 1)
			pageCurrent = 1;
		if (pageSize == null || pageSize < 5)
			pageSize = 5;
		int startIndex = (pageCurrent - 1) * pageSize;
		int rowCount = qsLdJgrMapper.getRowCount(dBname, ldLxId);
		List<QsLdJgr> list = new ArrayList<>();
		list = qsLdJgrMapper.findObject(dBname, ldLxId, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsLdJgr qsLdJgr) {
		int rows = 0;
		try {
			rows = qsLdJgrMapper.insertObject(dBname, qsLdJgr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		int rows = 0;
		try {
			rows = qsLdJgrMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsLdJgr qsLdJgr) {
		int rows = 0;
		try {
			rows = qsLdJgrMapper.updateObject(dBname, qsLdJgr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<QsLdJgr> findAllByRwType(String dBname, Integer ldLxId) {
		List<QsLdJgr> list = new ArrayList<>();
		list = qsLdJgrMapper.findObject(dBname, ldLxId, null, null);
		return list;
	}

	@Override
	public void importObjects(String dBname, List<QsLdJgr> list) {
		int count = 0;
		for (QsLdJgr qsLdJgr : list) {
			int rows = 0;
			Integer id = qsLdJgr.getLdJgId();
			if (id == null || id < 1) {
				rows = qsLdJgrMapper.insertObject(dBname, qsLdJgr);
				count = count + rows;
			} else {
				int row = qsLdJgrMapper.findObjectById(dBname, id);
				if (row == 0) {
					rows = qsLdJgrMapper.insertObject(dBname, qsLdJgr);
					count = count + rows;
				} else {
					rows = qsLdJgrMapper.updateObject(dBname, qsLdJgr);
					count = count + rows;
				}
			}
		}

	}

	@Override
	public List<QsLdJgr> findObjectByldRwId(String dBname, Integer ldRwId) {
		List<QsLdJgr> list = new ArrayList<>();
		try {
			list = qsLdJgrMapper.findObjectByldRwId(dBname, ldRwId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询任务结果失败");
		}

		return list;
	}

	@Override
	public void doQsLdJg(String dBname, List<QsLdJgr> list, Integer appid,Integer tjid) {
		System.out.println("tjid==="+tjid);
		Appmanager appmanager = appmanagerMapper.findObjectByAppid(appid);
		String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport() ;
		for (QsLdJgr qsLdJgr : list) {
			switch (qsLdJgr.getLdLxId()) {
			case 1:
				if (StringUtils.isEmpty(appmanager.getIpaddr()) || StringUtils.isEmpty(appmanager.getAppport()))
					break;
				Reg reg = new Reg();
				Integer regid = qsLdJgr.getRegId();
				if (regid != null && regid > 0)
					reg = regMapper.findRegByid(dBname, regid);
				/*
				 * RegSocket regSocket=new RegSocket(); regSocket.setRegid(regid);
				 * regSocket.setTagname(reg.getTagName());
				 * regSocket.setTagvalue(qsLdJgr.getTagValue());
				 */
				if (reg != null && !StringUtils.isEmpty(reg.getTagName())
						&& !StringUtils.isEmpty(qsLdJgr.getTagValue())) {
					restTemplate.getForObject(url+"/caiji/reg/" + reg.getTagName() + "/" + qsLdJgr.getTagValue(), SysResult.class);
					qsLdLogMapper.insertObjectByQsLdJgr(dBname, qsLdJgr,tjid);
				}
				break;
			case 2:
				if (qsLdJgr.getSpId() != null) {
					restTemplate.getForObject(url+"/caiji/sp/"+ qsLdJgr.getSpId(), SysResult.class);
					qsLdLogMapper.insertObjectByQsLdJgr(dBname, qsLdJgr,tjid);
				}

				break;
			case 3:
				if (qsLdJgr.getPicId() != null) {
					Pic pic=picMapper.findObjectById(dBname, qsLdJgr.getPicId());
					if(pic==null)break;
					restTemplate.getForObject(url+"/caiji/pic/"+ qsLdJgr.getPicId()+"/"+pic.getPicmodeid(), SysResult.class);
					qsLdLogMapper.insertObjectByQsLdJgr(dBname, qsLdJgr,tjid);
				}
				break;
			case 4:
				if(!StringUtils.isEmpty(qsLdJgr.getPhone())&&!StringUtils.isEmpty(qsLdJgr.getMessage())) {
					SmsLog smsLog=new SmsLog();
					smsLog.setSendcontent(qsLdJgr.getMessage());
					smsLog.setSendphone(qsLdJgr.getPhone());
					smsLog.setSendstate(1);
					smsLogMapper.insertObject(dBname,smsLog);
				}
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void doimplements(String dBname, Integer appid, String msg, HttpServletRequest request) {
        //System.out.println("接收指令参数" + msg);
        String[] split = msg.split(",");
        Appmanager appmanager = appmanagerMapper.findObjectByAppid(appid);
        Map<String, String> sendValueMap = new HashMap<>();
        for (String string : split) {
            String[] split2 = string.split(":");
            if (split2.length == 2 && StrUtil.isNotEmpty(split2[0]) && StrUtil.isNotEmpty(split2[1])) {
                sendValueMap.put(split2[0], split2[1]);
            }
        }
        if (sendValueMap.isEmpty()) return;
        SendVo sendVo = new SendVo(SendType.CONSOLE.sendType, sendValueMap);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport() + "/caiji/ConsoleSend";
        HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(sendVo), headers);
        if (!sendValueMap.isEmpty()) {
            restTemplate.postForEntity(url, formEntity, String.class);
        }
	}

	@Override
	public void deleteObjectByRwId(String dBname, Integer...ldRwid) {
		// TODO Auto-generated method stub
		try {
			qsLdJgrMapper.deleteObjectByRwId(dBname, ldRwid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void doQsLdJgByJgId(String dBname, Integer appid, Integer ldJgId) {
		// TODO Auto-generated method stub
		Appmanager appmanager = appmanagerMapper.findObjectByAppid(appid);
		String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport() ;
		QsLdJgr qsLdJgr=qsLdJgrMapper.findObjectByLdJgId(dBname,ldJgId);
		if(qsLdJgr==null)throw new ServiceException("该执行结果可能已不存在");

		switch (qsLdJgr.getLdLxId()) {
		case 1:
			if (StringUtils.isEmpty(appmanager.getIpaddr()) || StringUtils.isEmpty(appmanager.getAppport()))
				break;
			Reg reg = new Reg();
			Integer regid = qsLdJgr.getRegId();
			if (regid != null && regid > 0)
				reg = regMapper.findRegByid(dBname, regid);
			/*
			 * RegSocket regSocket=new RegSocket(); regSocket.setRegid(regid);
			 * regSocket.setTagname(reg.getTagName());
			 * regSocket.setTagvalue(qsLdJgr.getTagValue());
			 */
			if (reg != null && !StringUtils.isEmpty(reg.getTagName())
					&& !StringUtils.isEmpty(qsLdJgr.getTagValue())) {
				restTemplate.getForObject(url+"/caiji/reg/" + reg.getTagName() + "/" + qsLdJgr.getTagValue(), SysResult.class);

			}
			break;
		case 2:
			if (qsLdJgr.getSpId() != null) {
				restTemplate.getForObject(url+"/caiji/sp/"+ qsLdJgr.getSpId(), SysResult.class);
			}

			break;
		case 3:
			if (qsLdJgr.getPicId() != null) {
				Pic pic=picMapper.findObjectById(dBname, qsLdJgr.getPicId());
				System.out.println("pic===="+pic);
				if(pic==null)break;
				restTemplate.getForObject(url+"/caiji/pic/"+ qsLdJgr.getPicId()+"/"+pic.getPicmodeid(), SysResult.class);
			}
			break;
		case 4:
			if(!StringUtils.isEmpty(qsLdJgr.getPhone())&&!StringUtils.isEmpty(qsLdJgr.getMessage())) {
				SmsLog smsLog=new SmsLog();
				smsLog.setSendcontent(qsLdJgr.getMessage());
				smsLog.setSendphone(qsLdJgr.getPhone());
				smsLog.setSendstate(1);
				smsLogMapper.insertObject(dBname,smsLog);
			}
			break;
		default:
			break;
		}
	}
}
