package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.photonstudio.common.IPUtil;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QsUserloginlogMapper;
import com.photonstudio.pojo.QsUserloginlog;
import com.photonstudio.service.QsUserloginlogService;
@Service
public class QsUserloginlogServiceImpl implements QsUserloginlogService{
	@Autowired
	private QsUserloginlogMapper qsUserloginlogMapper;
	//@Autowired
	//private RestTemplate restTemplate;
	@Override
	public PageObject<QsUserloginlog> findObject(String dBname, String username,
										Integer pageCurrent, Integer pageSize,
											Date startTime, Date endTime) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=qsUserloginlogMapper.getRowCount(dBname,username,startTime,endTime);
		int startIndex=(pageCurrent-1)*pageSize;
		List<QsUserloginlog>list=new ArrayList<>();
		list=qsUserloginlogMapper.findObject(dBname,username,startIndex,
									pageSize,startTime,endTime);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsUserloginlog qsUserloginlog,HttpServletRequest request) {
		int rows=0;
		String ip=IPUtil.getIp(request);
		System.out.println(ip);
		//Iplog iplog=new Iplog();
		//iplog.setRegion("");
		//iplog.setCity("");
		//try {
		//	String ipdata =
				//	 restTemplate.getForObject("http://ip.taobao.com/service/getIpInfo2.php?"+
				//	  "ip="+ip, String.class); 
				//	  iplog = ObjectMapperUtil.toObject(ipdata, IpResult.class).getData();
		//} catch (RestClientException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
				  //String userip=ip+"-"+iplog.getRegion()+iplog.getCity();
		qsUserloginlog.setIp(ip);
		qsUserloginlog.setTime(new Date());
		
		try {
			rows=qsUserloginlogMapper.insertObject(dBname,qsUserloginlog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer...ids) {
		int rows=0;
		try {
			rows=qsUserloginlogMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsUserloginlog qsUserloginlog) {
		// TODO Auto-generated method stub
		return 0;
	}

}
