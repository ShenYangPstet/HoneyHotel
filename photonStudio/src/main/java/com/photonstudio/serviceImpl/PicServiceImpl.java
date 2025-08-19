package com.photonstudio.serviceImpl;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.PicMapper;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Pic;
import com.photonstudio.service.PageStyleService;
import com.photonstudio.service.PicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PicServiceImpl implements PicService {
	private final PicMapper picMapper;
	private final PageStyleService pageStyleService;

	@Override
	public List<Pic> findObject(String dBname, Integer parentid) {

		return picMapper.findObject(dBname, parentid);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteObject(String dBname, Integer picId) {
		if (picId == null || picId < 1) {
			throw new IllegalArgumentException("请先选择");
		}
		//删除子菜单
		findChildren(dBname, picId);
		int rows = 0;
		rows = picMapper.deleteObjectById(dBname, picId);
		pageStyleService.deleteByPicId(picId);
		return rows;
	}

	private void findChildren(String dbName, Integer picId) {
		List<Integer> list = picMapper.findChildrenId(dbName, picId);
		if (list == null || list.size() < 1) {
			return;
		}
		for (Integer childrenId : list) {
			findChildren(dbName, childrenId);
			picMapper.deleteObjectById(dbName, childrenId);
			pageStyleService.deleteByPicId(childrenId);
		}
	}

	@Override
	public int saveObject(String dBname, Pic pic) {
		int rows = 0;
		int pid = pic.getParentid();
		/*
		 * if(pid!=0) {
		 *
		 * picMapper.updateParent(dBname,pid);
		 *
		 * }
		 */
		try {
			String hyponymy= pic.getPicname();
			if (!StringUtils.isEmpty(pic.getHyponymy())){
				hyponymy=pic.getHyponymy()+","+hyponymy;
			}
			pic.setHyponymy(hyponymy);
			rows = picMapper.insertObject(dBname, pic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("失败");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, Pic pic) {
		int rows=0;		
		if(pic.getParentid()==pic.getPicid())throw new IllegalArgumentException("不能隶属于自己");
		try {
			String hyponymy= pic.getPicname();
			if (!StringUtils.isEmpty(pic.getHyponymy())){
				hyponymy=pic.getHyponymy()+","+hyponymy;
			}
			pic.setHyponymy(hyponymy);
			rows=picMapper.updateObject(dBname, pic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		return rows;
	}
	@Override
	public List<Pic> findAllPic(String dBname) {
		// TODO Auto-generated method stub
		List<Pic> picList=new ArrayList<>();
		try {
			picList = picMapper.findAllPic(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return picList;
	}
	@Override
	public List<Pic> findObjectInPicmodeid(String dBname,String picname) {
		List<Pic> list=new ArrayList<>();
		List<Pic> picList = new ArrayList<Pic>();
		try {
			list = picMapper.findObjectInPicmodeid(dBname,picname);
			// 最后的结果
		    // 先找到所有的一级菜单
		   for (Pic pic : list) {
			   if(pic.getParentid()==0) {
				   picList.add(pic);
			   }
			
		}
		    // 为一级菜单设置子菜单，getChild是递归调用的
		    for (Pic pic : picList) {
		        pic.setChildren(getChild(pic.getPicid(), list));
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return picList;
	}
	private List<Pic> getChild(Integer picid, List<Pic> list) {
		// TODO Auto-generated method stub
		  // 子菜单
	    List<Pic> childList = new ArrayList<>();
	    for (Pic pic : list) {
	        if (0 != pic.getParentid()) {
	            if (pic.getParentid().equals(picid)) {
	                childList.add(pic);
	            }
	        }
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (Pic pic: childList) {
	            // 递归
	            pic.setChildren(getChild(pic.getPicid(), list));
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}
	@Override
	public Pic findObjectById(String dBname, Integer picid) {
		// TODO Auto-generated method stub
		return picMapper.findObjectById(dBname,picid);
	}
	@Override
	public List<Pic> findAllPicNameid(String dBname) {
		// TODO Auto-generated method stub
		List<Pic> list=new ArrayList<>();
		List<Pic> picList = new ArrayList<Pic>();
		try {
			list = picMapper.findAllPicNameid(dBname);
			// 最后的结果
		    // 先找到所有的一级菜单
		   for (Pic pic : list) {
			   if(pic.getParentid()==0) {
				   picList.add(pic);
			   }
			
		}
	    // 为一级菜单设置子菜单，getChild是递归调用的
	    for (Pic pic : picList) {
	        pic.setChildren(getChild(pic.getPicid(), list));
	    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return picList;
	}
	@Override
	public List<Pic> findAllPicByUserid(String dBname, Integer userid) {
		// TODO Auto-generated method stub
		Appuser appuser = picMapper.findDepartmentid(dBname,userid);
		if(appuser !=null )
		{
			if(appuser.getDepartmentid() != null)
			{
				List<Pic> piclist = picMapper.findPiclistBydpid(dBname,appuser.getDepartmentid());
				return piclist;
			}
			else
			{
				List<Pic> piclist = picMapper.findPiclistAll(dBname);
				return piclist;
			}
		}
		return null;
	}
	@Override
	public int updateColor(String dBname, Integer picid, String color) {
		// TODO Auto-generated method stub
		int rows=0;		
		try {
			rows=picMapper.updateColor(dBname, picid,color);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		return rows;
	}

	@Override
	public List<Pic> findExportPic(String dBname) {
		return picMapper.findExportPic(dBname);
	}
	@Transactional
	@Override
	public void importObjects(String dBname, List<Pic> list) {
		Integer picid;
		int rows=0;
		for(Pic pic : list)
		{
			picid = pic.getPicid();
			if(picid == null || picid <1)
			{
				//System.out.println(pic);
				picMapper.insertObject(dBname,pic);
			}
			else
			{
				Pic objectById = picMapper.findObjectById(dBname, picid);
				if(objectById==null)
				{
					//System.out.println(pic);
					picMapper.insertObject(dBname,pic);
				}
				else
				{
					picMapper.updateObject(dBname,pic);
				}
			}

		}
	}

}
