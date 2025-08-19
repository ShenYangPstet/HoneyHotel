package com.photonstudio.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photonstudio.common.CheckAuthorizeCode;
import com.photonstudio.common.EncoderFile;
import com.photonstudio.common.LicenseCode;
import com.photonstudio.common.SessionUtil;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Appusergroup;
import com.photonstudio.pojo.QsUserloginlog;
import com.photonstudio.pojo.Sysmenuinfo;
import com.photonstudio.pojo.Usertoken;
import com.photonstudio.service.AppinfoService;
import com.photonstudio.service.AppmanagerService;
import com.photonstudio.service.AppsysuserService;
import com.photonstudio.service.AppuserService;
import com.photonstudio.service.AppusergroupService;
import com.photonstudio.service.DrinfoService;
import com.photonstudio.service.QsUserloginlogService;
import com.photonstudio.service.RegService;
import com.photonstudio.service.SysmenuinfoService;
import com.photonstudio.webSocket.WebSocketServer;

@Controller
@PropertySource(value = "classpath:/properties/key.properties")
@RequestMapping("/")
public class IndexController {
	@Value("${key.keydefault}")
	private String key;
	@Value("${key.path}")
	private String licensePath;
	public String xzqh = "wuhan";
	public String xm_name = "studio20";
	@Autowired
	private AppsysuserService appsysuserService;
	@Autowired
	private SysmenuinfoService sysmenuinfoService;
	@Autowired
	private AppusergroupService appusergroupService;
	@Autowired
	private DrinfoService drinfoService;
	@Autowired
	private QsUserloginlogService qsUserloginlogService;
	@Autowired
	private AppuserService appuserService;
	@Autowired
	private AppinfoService appinfoService;
	@Autowired
	private RegService regService;
	@Autowired
	private AppmanagerService appmanagerService;

	@RequestMapping("/user/login")
	@ResponseBody
	public SysResult login(Appsysuser user) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = appsysuserService.findUserByUP(user);
			if (map.isEmpty()) {
				// System.out.println(map);
				return SysResult.build(50009, "用户名或密码错误");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return SysResult.build(50009, "用户名或密码错误");
		}
/*
		 * try { if (!CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh))
		 * {
		 *
		 * return SysResult.build(50000, LicenseCode.getApplyCode(key, xm_name, xzqh));
		 * }else { File file = new File(licensePath); String
		 * en=LicenseCode.getPlaintext(EncoderFile.myread(file).trim(),key); String
		 * sqtime=en.substring(en.lastIndexOf(",")+1); map.put("sqtime", sqtime); } }
		 * catch (Exception e) { e.printStackTrace(); return SysResult.build(50000,
		 * LicenseCode.getApplyCode(key, xm_name, xzqh)); }
*/
		/*
		 * Appsysuser userDB = (Appsysuser) map.get("user"); Date licensetime =
		 * userDB.getLicensetime(); System.out.println(licensetime); Date date = new
		 * Date(); if (licensetime != null) { if (licensetime.getTime() <
		 * date.getTime()) { return SysResult.build(50009, "用户时间已到期请联系管理员"); } }
		 */
		SessionUtil.clearAll();
		return SysResult.oK(map);
	}

	@RequestMapping("/user/dologin")
	@ResponseBody
	public SysResult findAppsysuserByToken(String token) {
		Usertoken tokenDB = appsysuserService.findUsertokenByToken(token);
		if (tokenDB == null) {
			return SysResult.build(50008, "重新登录");
		}
		Appsysuser user = appsysuserService.findAppsysuserByUsername(tokenDB.getUsername());
		if(user.getRole()==0) {
			try {
				File file = new File(licensePath);
				String encoder = EncoderFile.myread(file).trim();
				String en=LicenseCode.getPlaintext(encoder,key);
				if (!CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh)) {

					return SysResult.build(50000, LicenseCode.getApplyCode(key, xm_name, xzqh));
				}
				if(!CheckAuthorizeCode.dateyz(key, en))
					return SysResult.build(50000,encoder,"授权时间到期");
					String[] split = en.split(",");
					int appsum=appmanagerService.getRowCountByLicense();
					if(appsum>Integer.valueOf(split[4]))
						return SysResult.build(50009, "项目数量超过平台上限，请联系管理员", encoder);
					String sqtime=en.substring(en.lastIndexOf(",")+1);
					user.setXmsqtime(sqtime);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return SysResult.build(50000,"授权服务可能断开请！请联系管理员");
			}
		}

		Date licensetime = user.getLicensetime();
		Date date = new Date();
		if (licensetime != null) {
			if (licensetime.getTime() < date.getTime()) {
				return SysResult.build(50009, "用户时间已到期请联系管理员");
			}
		}
		// System.out.println("用户id==="+user.getId());
		List<Appusergroup> list = new ArrayList<>();
		list = appusergroupService.findObjectByuserid(user.getId(),null,null);
		for (Appusergroup appusergroup : list) {
			String dBname = appusergroup.getAppid() + appusergroup.getAppName();
			int drCount = drinfoService.getRowCount(dBname, null, null);
			List<Sysmenuinfo> menuList = sysmenuinfoService.findObjectByGroupid(dBname,
					appusergroup.getAppuserground());
			appusergroup.setDrCount(drCount);
			appusergroup.setMenuList(menuList);
			appusergroup.setAppSumday(appinfoService.findAppinfo(dBname));
			appusergroup.setDrmalfunctionSum(regService.getCountByShowTypeAndState(dBname,"3","1"));
			Appinfo appinfo = appsysuserService.queryAppinfoByAppname(dBname, appusergroup.getAppName());
			appusergroup.setAppinfo(appinfo);
		}
		// System.out.println("appusergroup==="+list);
		user.setAppusergroup(list);
		return SysResult.oK(user);
	}
	@RequestMapping("/user/clearsession")
	@ResponseBody
	public SysResult clearsession() {
		SessionUtil.clearAll();
		return SysResult.oK();
	}
	@RequestMapping("/user/appmanager/{dBname}/login")
	@ResponseBody
	public SysResult saveObject(@PathVariable String dBname,QsUserloginlog qsUserloginlog,HttpServletRequest request) {
//		String regMaxCountStr=CheckAuthorizeCode.getAppMaxNum(key, licensePath, 5);
//		int regCount=regService.getRegCount(dBname,null);
//		if(regCount>Integer.valueOf(regMaxCountStr))
//			return SysResult.build(50009, "项目点位超过上限"+regMaxCountStr+"请联系管理员",LicenseCode.getApplyCode(key, xm_name, xzqh) );
		List<Integer> dridlist = drinfoService.findDridByUserid(dBname, qsUserloginlog.getUserid());
		if(SessionUtil.getAppuser(dBname)==null) {
			Appuser appuser=appuserService.findObjectByUserid(dBname,qsUserloginlog.getUserid());
			SessionUtil.setAppuser(dBname,appuser);
			//request.getSession().setAttribute("appuser", appuser);
		}
		 Appuser appusersession= SessionUtil.getAppuser(dBname);
		if(dridlist!=null&&dridlist.size()>0)
		 appusersession.setDridlist(dridlist);
		 SessionUtil.setAppuser(dBname,appusersession);
		int rows=qsUserloginlogService.saveObject(dBname,qsUserloginlog,request);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/")
	public String getIndex() {
		return "index.html";
	}

	@RequestMapping("/websocket.html")
	public String webSocket() {
		try {
			WebSocketServer.sendAll("为了联盟");
			WebSocketServer.sendmsg(25, "测试！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "websocket.html";
	}
}
