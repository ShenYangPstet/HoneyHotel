package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Images;

public interface ImagesService {

	PageObject<Images> findObjectByType(String imgtype, String appname, String imgname, Integer pageCurrent, Integer pageSize);

	int deleteObjectById(String dBname, Integer imgid);

	int updateObject(String dBname, Images images);

	int saveObject(String dBname, Images images);

	List<Images> findAllByImgtype(String dBname, String imgtype,String imgname);

	Images findObjectById(String dBname, Integer imgid);

	PageObject<Images> findObjectPByType(String imgtype, String dBname, Integer pageCurrent, Integer pageSize);

	List<Images> findAllPByImgtype(String dBname, String imgtype);

	int updateObjectP(String dBname, Images images);

	int saveObjectP(String dBname, Images images);

	Images findObjectPById(String dBname, Integer imgid);

	int deleteObjectPById(String dBname, Integer imgid);
	
}
