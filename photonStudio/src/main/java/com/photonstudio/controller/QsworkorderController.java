package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.DrCheckLog;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Qsworkorder;
import com.photonstudio.service.QsworkorderService;

@RestController
@RequestMapping("/zsqy/qsworkorder")
public class QsworkorderController {

	@Autowired
	private QsworkorderService qsworkorderService;

	@RequestMapping("/{dBname}/findObject") // 分页查询
	public SysResult findObject(@PathVariable("dBname") String dBname, Integer state, Integer pageCurrent,
			Integer pageSize) {
		PageObject<Qsworkorder> pageObject = new PageObject<>();
		try {
			pageObject = qsworkorderService.findObject(dBname, state, pageCurrent, pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}

	@RequestMapping("/{dBname}/findObjectByNumber")
	public SysResult findObjectByNumber(@PathVariable("dBname") String dBname, String number) {
		try {
			List<Qsworkorder> qsworkorderList = qsworkorderService.findObjectByNumber(dBname, number);
			return SysResult.oK(qsworkorderList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}

	@RequestMapping("/{dBname}/delete")
	public SysResult delete(@PathVariable("dBname") String dBname, Integer[] ids) {
		int rows = qsworkorderService.delete(dBname, ids);
		if (rows > 0) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败");
	}

	@RequestMapping("/{dBname}/save")
	public SysResult save(@PathVariable("dBname") String dBname, Qsworkorder qsworkorder) {
		int rows = qsworkorderService.save(dBname, qsworkorder);
		if (rows > 0) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "新增失败");
	}

	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable("dBname") String dBname, Qsworkorder qsworkorder) {
		int rows = qsworkorderService.update(dBname, qsworkorder);
		if (rows > 0) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}

	@RequestMapping("/{dBname}/findEC")
	public SysResult findECByState(@PathVariable("dBname") String dBname) {
		try {
			List<EcharsObject> list = qsworkorderService.findECByState(dBname);
			List<EcharsObject> listEc = new ArrayList<EcharsObject>();
			if (list.size() < 1) {
				EcharsObject echarsObject1 = new EcharsObject();
				echarsObject1.setName("未处理");
				echarsObject1.setValue("0");
				listEc.add(echarsObject1);
				EcharsObject echarsObject2 = new EcharsObject();
				echarsObject2.setName("正在处理");
				echarsObject2.setValue("0");
				listEc.add(echarsObject2);
				EcharsObject echarsObject3 = new EcharsObject();
				echarsObject3.setName("处理完毕");
				echarsObject3.setValue("0");
				listEc.add(echarsObject3);
			} else {
				String value1 = "0";
				String value2 = "0";
				String value3 = "0";
				for (EcharsObject ec : list) {
					if (ec.getName().equals("未处理")) {
						value1 = ec.getValue();
					}
					if (ec.getName().equals("正在处理")) {
						value2 = ec.getValue();
					}
					if (ec.getName().equals("处理完毕")) {
						value3 = ec.getValue();
					}
				}
				EcharsObject echarsObject1 = new EcharsObject();
				echarsObject1.setName("未处理");
				echarsObject1.setValue(value1);
				listEc.add(echarsObject1);
				EcharsObject echarsObject2 = new EcharsObject();
				echarsObject2.setName("正在处理");
				echarsObject2.setValue(value2);
				listEc.add(echarsObject2);
				EcharsObject echarsObject3 = new EcharsObject();
				echarsObject3.setName("处理完毕");
				echarsObject3.setValue(value3);
				listEc.add(echarsObject3);
			}
			return SysResult.oK(listEc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}

	@RequestMapping("/{dBname}/findWorkOrderList") // 分页查询
	public SysResult findWorkOrderList(@PathVariable("dBname") String dBname, Integer state) {
		PageObject<Qsworkorder> pageObject = new PageObject<>();
		try {
			pageObject = qsworkorderService.findObject(dBname, state, null, null);
			return SysResult.oK(pageObject.getRecords());
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}

	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname, HttpServletResponse response, Integer... ids) {
		if (ids != null && ids.length == 0)
			ids = null;
		List<Qsworkorder> list = qsworkorderService.findAll(dBname, ids);

		ExcelUtil.exportExcel(list, "工单信息", dBname, Qsworkorder.class, dBname + "-工单信息.xls", response);

	}
	//工单查询接口--不带时间
	@RequestMapping("/{dBname}/findAllworklist")
	public SysResult findAllworklist(@PathVariable("dBname") String dBname,int state,String username, String workuser, Integer approvestate) {
		try {
			List<Qsworkorder> qsworkorderList=qsworkorderService.findAllworklist(dBname,state,username, workuser, approvestate);
			return SysResult.oK(qsworkorderList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/finddrcehcklog")
	public SysResult finddrcehcklog(@PathVariable("dBname") String dBname, String checkPerson, Date checkDate,
			Integer pageCurrent, Integer pageSize) {
		PageObject<DrCheckLog> pageObject = new PageObject<>();
		try {
			pageObject = qsworkorderService.finddrcehcklog(dBname, checkPerson, checkDate, pageCurrent, pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

}
