package com.photonstudio.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Idtree;

public interface DrtypeinfoService  extends IService<Drtypeinfo> {

	List<Drtypeinfo> findObjectByParentId(String dBname, Integer parentid);

	int deleteObject(String dBname, Integer drtypeid);

	int saveObject(String dBname, Drtypeinfo drtypeinfo);

	int updateObject(String dBname, Drtypeinfo drtypeinfo);

	List<Drtypeinfo> findAllDrtypeAndDr(String dBname, Integer iscustomType);

	List<Drinfo> findDrinfoByType(String dBname, Integer drtypeid, String drname);

	List<Drtypeinfo> findAllDrtypeJgt(String dBname);

	PageObject<Drtypeinfo> findiscustom(String dBname,Integer pageCurrent,
			Integer pageSize);

	Drtypeinfo findDrtypeinfoByDrid(String dBname, Integer drid);

	List<Drtypeinfo> findAllByIscustom(String dBname, Integer iscustomType);

	List<Drtypeinfo> findAllDrtypevo(String dBname, Integer drtypeid);

	Map<String, Double> findWorkorderByDrtype(String dBname);

	void moveDrtypeAndModle(String dBname, String drtypeids);

	Drtypeinfo findDrtypeinfoBytypeid(String dBname, Integer drtypeid);

	List<Drtypeinfo> findBasisDrtype(String dBname);

	List<Idtree> findIdtree(String dBname, Integer id);

	List<EcharsObject>  findDrStateByDrtype(String dBname, Integer drtypeid);


	List<Drtypeinfo> findAllStatusVos(String dBname,List<Integer> integerList);

	/**
	 * 根据设备类型父id集合递归查询下面所有设备类型id
	 *
	 * @param parentIds 父id集合 为空时返回空集合
	 * @return 设备类型id集合
	 */
	List<Integer> listDrTypeIdByParentId(Collection<Integer> parentIds);

	/**
	 * 根据设备类型父id递归查询下面所有设备类型id
	 *
	 * @param parentId 父id 为null时返回空集合
	 * @return 设备类型id集合
	 */
	List<Integer> listDrTypeIdByParentId(Integer parentId);
}
