package com.photonstudio.common;

import com.photonstudio.pojo.Appsysuser;

public class UserThreadLocal {
	private static ThreadLocal<Appsysuser> userThreadLocal = 
			new ThreadLocal<>();
	
	public static void set(Appsysuser user) {
		
		userThreadLocal.set(user);
	}
	
	public static Appsysuser get() {
		
		return userThreadLocal.get();
	}
	
	//使用ThreadLocal切记删除对象.防止内存泄漏.
	public static void remove() {
		
		userThreadLocal.remove();
	}
}
