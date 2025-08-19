package com.photonstudio.common;

import java.util.List;

import com.photonstudio.common.vo.PageObject;

public class PagesUtil {
  static public <T> PageObject<T> getPageObject(List<T> list,
		                        Integer rowCount,Integer pageSize
		                        ,Integer pageCurrent){
	  PageObject<T> pageObject=new PageObject<>();
		//对查询结果进行封装并返回。
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(list);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
  }
}
