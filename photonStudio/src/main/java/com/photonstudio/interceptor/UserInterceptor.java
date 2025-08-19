package com.photonstudio.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.photonstudio.common.SessionUtil;
import com.photonstudio.common.UserThreadLocal;
import com.photonstudio.mapper.AppsysuserMapper;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Usertoken;
@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private AppsysuserMapper appsysuserMapper;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String tokenh = request.getHeader("ZSQY_TEST");
		/*
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			String token = null;
			for (Cookie cookie : cookies) {
				if("ZSQY_TEST".equals(cookie.getName())) {
					token = cookie.getValue();
					System.out.println("cookie中==="+token);
					break;
				}
			}*/
			if(!StringUtils.isEmpty(tokenh)) {
				//2.判断数据库是否存在该记录
				Usertoken usertoken = appsysuserMapper.findUsertokenByToken(tokenh);
				if(usertoken!=null) {
					//获取用户信息
					Appsysuser user = appsysuserMapper.findAppsysuserByUsername(usertoken.getUsername());
					request.setAttribute("ZSQY_USER",user);
					UserThreadLocal.set(user);
					if(user.getRole()==0) {
						Map<String, String> pathVars =  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
						if(pathVars.get("dBname")!=null) {
							Appuser appuser = SessionUtil.getAppuser(pathVars.get("dBname"));
							if(appuser==null) {
								response.sendError(50007, "项目登录失效");
								return false;
							}
						}
					}
					return true;	//放行请求
				}
			}
		//}
			//必须重定向到用户登陆页面.
			response.sendError(50008, "请重新登录");
			
			return false;//false表示拦截   true表示放行
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		UserThreadLocal.remove();
	}
	
}
