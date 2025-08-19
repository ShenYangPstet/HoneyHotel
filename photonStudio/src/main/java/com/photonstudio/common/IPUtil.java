package com.photonstudio.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class IPUtil {
	public static String getIp(HttpServletRequest request){
		//代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

		ip = request.getHeader("HTTP_CLIENT_IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

		ip = request.getHeader("HTTP_X_FORWARDED_FOR");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

		ip = request.getHeader("X-Real-IP");

		   }
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

			}
		 //使用代理，则获取第一个IP地址
	       if(!StringUtils.isEmpty(ip) && ip.length() > 15) {			
	    	   if(ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
			}
	        
	        return ip;
	}
}
