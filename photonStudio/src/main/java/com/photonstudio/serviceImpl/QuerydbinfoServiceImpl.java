package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.mapper.QueryconditioninfoMapper;
import com.photonstudio.mapper.QuerydbinfoMapper;
import com.photonstudio.mapper.QueryresultinfoMapper;
import com.photonstudio.pojo.Queryconditioninfo;
import com.photonstudio.pojo.Querydbinfo;
import com.photonstudio.pojo.Queryresultinfo;
import com.photonstudio.service.QuerydbinfoService;

@Service
public class QuerydbinfoServiceImpl implements QuerydbinfoService {
	@Autowired
	private QuerydbinfoMapper querydbinfoMapper;
	@Autowired
	private QueryconditioninfoMapper queryconditioninfoMapper;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private QueryresultinfoMapper QueryresultinfoMapper;

	@Override
	public SysResult findObjectByOtherdb(String dBname, Integer id, Map<String, String> param) { // TODO Auto-generated
																									// method stub
		System.out.println(param);
		String pageCurrent = param.get("pageCurrent");
		if (StringUtils.isEmpty(pageCurrent))
			param.put("pageCurrent", "1");
		Querydbinfo querydbinfo = querydbinfoMapper.findObjectById(dBname, id);
		if (querydbinfo == null)
			return SysResult.build(50009, "未设置查询模板");
		List<Queryconditioninfo> conditioninfolist = queryconditioninfoMapper.findObjectByconditionId(dBname,
				querydbinfo.getConditionId());
		System.out.println("conditioninfolist====" + conditioninfolist);
		String sql = querydbinfo.getDbsql();
		List<String> paramlist = new ArrayList<>();
		for (Queryconditioninfo queryconditioninfo : conditioninfolist) {
			String value = param.get(queryconditioninfo.getFiledDbName());
			System.out.println("value=" + value);
			System.out.println("FiledDbName===" + queryconditioninfo.getFiledDbName());
			if (!StringUtils.isEmpty(value)) {
				sql = sql.replace(queryconditioninfo.getFiledDbName(), queryconditioninfo.getFiedSql());
				paramlist.add(value);
			} else {
				sql = sql.replace(queryconditioninfo.getFiledDbName(), "");
			}
		}
		switch (querydbinfo.getDbtype()) {
		case 1:
		case 2:
			int startIndex = (Integer.valueOf(param.get("pageCurrent")) - 1) * 20;
			sql = sql.replace("startIndex", String.valueOf(startIndex));
			break;
		case 3:
		case 4:
			sql = sql.replace("pageCurrent", param.get("pageCurrent"));
			break;
		default:
			break;
		}
		System.out.println(sql);
		querydbinfo.setParam(paramlist);
		querydbinfo.setDbsql(sql);
		String httpurl = "http://" + querydbinfo.getHttpUrl() + "/ShujuCJ/findObject";
		SysResult sysResult = restTemplate.postForObject(httpurl, querydbinfo, SysResult.class);
		System.out.println(querydbinfo);
		System.out.println(paramlist);
		return sysResult;

	}

	@Override
	public SysResult findObjectByOtherdb23(String dBname, Integer id, Map<String, String> param) {
		// TODO Auto-generated method stub
		
		String pageCurrent = param.get("pageCurrent");
		if (StringUtils.isEmpty(pageCurrent))
			param.put("pageCurrent", "1");
		if(StringUtils.isEmpty(param.get("sorted")))param.put("sorted", "0");
		Querydbinfo querydbinfo = querydbinfoMapper.findObjectById(dBname, id);
		if (querydbinfo == null)
			return SysResult.build(50009, "未设置查询模板");
		List<Queryconditioninfo> conditioninfolist = queryconditioninfoMapper.findObjectByconditionId(dBname,
				querydbinfo.getConditionId());
		String sql = querydbinfo.getDbsql();
		List<String> paramlist = new ArrayList<>();
		if(conditioninfolist.size()>0) {
			
			for (Queryconditioninfo queryconditioninfo : conditioninfolist) {
				String value = param.get(queryconditioninfo.getFiledDbName());
				System.out.println("value=" + value);
				System.out.println("FiledDbName===" + queryconditioninfo.getFiledDbName());
				if (!StringUtils.isEmpty(value)) {
					sql = sql.replace(queryconditioninfo.getFiledDbName(), queryconditioninfo.getFiedSql());
					paramlist.add(value);
				} else {
					sql = sql.replace(queryconditioninfo.getFiledDbName(), "");
				}
			}
		}
		int startIndex = (Integer.valueOf(param.get("pageCurrent")) - 1) * 20;
		String endIndex = String.valueOf(Integer.valueOf(param.get("pageCurrent")) * 20);
		String sortedvalue=param.get("sorted");
		String sorted="";
		if(Integer.valueOf(sortedvalue)==0) {
			sorted=" DESC";
		}else {
			sorted=" ASC";
		}
		switch (querydbinfo.getDbtype()) {
		case 1:
		case 2:
			if(!StringUtils.isEmpty(querydbinfo.getOrderfield())) {
				
				sql=sql+" ORDER BY  "+querydbinfo.getOrderfield()+sorted;
			}
			sql=sql+" LIMIT startIndex, 20";
			sql = sql.replace("startIndex", String.valueOf(startIndex));
			break;
		case 3:
		case 4:
			String orderfield="";
			if(StringUtils.isEmpty(querydbinfo.getOrderfield())) {
				return SysResult.build(50009, "sqlserver数据库需要设置排序字段");
			}
			orderfield=querydbinfo.getOrderfield()+sorted;
			sql="SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY orderfield) AS RowIndex,"+sql+") AS PageTable WHERE RowIndex BETWEEN startIndex AND endIndex";
			sql=sql.replace("orderfield", orderfield);
			sql = sql.replace("startIndex", String.valueOf(startIndex));
			sql=sql.replace("endIndex",endIndex);
			break;
		default:
			break;
		}
		
		querydbinfo.setParam(paramlist);
		querydbinfo.setDbsql(sql);
		System.out.println(sql);
		String httpurl = "http://" + querydbinfo.getHttpUrl() + "/ShujuCJ/findObject";
		SysResult sysResult = restTemplate.postForObject(httpurl, querydbinfo, SysResult.class);
		return sysResult;
	}

	@Override
	public PageObject<Querydbinfo> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent == null || pageCurrent < 1)
			pageCurrent = 1;
		if (pageSize == null || pageSize < 5)
			pageSize = 5;
		List<Querydbinfo> list = new ArrayList<>();
		int rowCount = querydbinfoMapper.getRowCount(dBname);
		int startIndex = (pageCurrent - 1) * pageSize;
		list = querydbinfoMapper.findObject(dBname, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Querydbinfo querydbinfo) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = querydbinfoMapper.insertObject(dBname, querydbinfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = querydbinfoMapper.deleteObjectById(dBname, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Querydbinfo querydbinfo) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = querydbinfoMapper.updateObject(dBname, querydbinfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public Querydbinfo findObjectById(String dBname, Integer id) {
		// TODO Auto-generated method stub
		Querydbinfo querydbinfo = new Querydbinfo();
		try {
			querydbinfo = querydbinfoMapper.findObjectById(dBname, id);
			List<Queryconditioninfo> condlist = queryconditioninfoMapper.findObjectByconditionId(dBname,
					querydbinfo.getConditionId());
			List<Queryresultinfo> resultlist = QueryresultinfoMapper.findObjectByResultId(dBname,
					querydbinfo.getResultId(), null, null);
			querydbinfo.setConditioninfolist(condlist);
			querydbinfo.setResultinfolist(resultlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return querydbinfo;
	}
}
