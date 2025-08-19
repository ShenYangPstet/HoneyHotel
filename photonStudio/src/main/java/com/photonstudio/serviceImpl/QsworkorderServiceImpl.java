package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QsworkorderMapper;
import com.photonstudio.pojo.DrCheckLog;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Qsworkorder;
import com.photonstudio.service.QsworkorderService;

@Service
public class QsworkorderServiceImpl implements QsworkorderService {

	@Autowired
	private QsworkorderMapper qsworkorderMapper;

	@Override
	public PageObject<Qsworkorder> findObject(String dBname, Integer state, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent == null || pageCurrent < 1)
			pageCurrent = 1;
		if (pageSize == null || pageSize < 5)
			pageSize = 5;
		List<Qsworkorder> list = new ArrayList<>();
		int rowCount = qsworkorderMapper.getRowCount(dBname, state);
		int startIndex = (pageCurrent - 1) * pageSize;
		list = qsworkorderMapper.findObject(dBname, state, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public List<Qsworkorder> findObjectByNumber(String dBname, String number) {
		// TODO Auto-generated method stub
		List<Qsworkorder> qsworkorderList = qsworkorderMapper.findObjectByNumber(dBname, number);
		return qsworkorderList;
	}

	@Override
	public int delete(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = qsworkorderMapper.delete(dBname, ids);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}

	@Override
	public int save(String dBname, Qsworkorder qsworkorder) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = qsworkorderMapper.save(dBname, qsworkorder);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}

	@Override
	public int update(String dBname, Qsworkorder qsworkorder) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = qsworkorderMapper.update(dBname, qsworkorder);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}

	@Override
	public List<EcharsObject> findECByState(String dBname) {
		// TODO Auto-generated method stub

		List<EcharsObject> list = new ArrayList<>();
		try {
			list = qsworkorderMapper.findEC(dBname);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return list;
	}

	@Override
	public List<Qsworkorder> findAll(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		List<Qsworkorder> list = new ArrayList<>();
		try {
			list = qsworkorderMapper.findObjectByIds(dBname, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageObject<DrCheckLog> finddrcehcklog(String dBname, String checkPerson, Date checkDate, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent == null || pageCurrent < 1)
			pageCurrent = 1;
		if (pageSize == null || pageSize < 5)
			pageSize = 5;
		int startIndex = (pageCurrent - 1) * pageSize;
		System.out.println(checkPerson);
		List<DrCheckLog> list = qsworkorderMapper.finddrcehcklog(dBname, checkPerson, checkDate, startIndex, pageSize);
		int rowCount = qsworkorderMapper.getDrCheckLogCount(dBname, checkPerson, checkDate);
		PageObject<DrCheckLog> pageObject = PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
		return pageObject;
	}
	@Override
	public List<Qsworkorder> findAllworklist(String dBname, int state, String username, String workuser, Integer approvestate) {
		// TODO Auto-generated method stub
		return qsworkorderMapper.findworklist(dBname,state,null,null,username,workuser,approvestate);
	}
}
