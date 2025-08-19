package com.photonstudio.common;

import java.util.HashMap;
import java.util.Map;

public class JobStatesUtil {
	public static String getTriggerStatesCN(String key) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("BLOCKED", "阻塞");
	    map.put("COMPLETE", "完成");
	    map.put("ERROR", "出错");
	    map.put("NONE", "未启动");
	    map.put("NORMAL", "正常");
	    map.put("PAUSED", "暂停");

	    map.put("4", "阻塞");
	    map.put("2", "完成");
	    map.put("3", "出错");
	    map.put("-1", "未启动");
	    map.put("0", "正常");
	    map.put("1", "暂停");
	    /*  **STATE_BLOCKED 4 阻塞
	STATE_COMPLETE 2 完成
	STATE_ERROR 3 错误
	STATE_NONE -1 不存在
	STATE_NORMAL 0 正常
	STATE_PAUSED 1 暂停***/
	    return map.get(key);
	}
}
