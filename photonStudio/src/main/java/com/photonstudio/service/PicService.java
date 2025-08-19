package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Pic;

public interface PicService {

	List<Pic> findObject(String dBname, Integer parentid);

	int deleteObject(String dBname, Integer picid);

	int saveObject(String dBname, Pic pic);

	int updateObject(String dBname, Pic pic);

	List<Pic> findAllPic(String dBname);

	List<Pic> findObjectInPicmodeid(String dBname, String picname);

	Pic findObjectById(String dBname, Integer picid);

	List<Pic> findAllPicNameid(String dBname);

	List<Pic> findAllPicByUserid(String dBname, Integer userid);

	int updateColor(String dBname, Integer picid, String color);

	List<Pic> findExportPic(String dBname);

	void importObjects(String dBname, List<Pic> list);
}
