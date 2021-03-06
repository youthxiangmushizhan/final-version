package com.zyy.pinyougou.sellergoods.service;
import java.util.List;
import com.zyy.pinyougou.pojo.TbItemCat;

import com.github.pagehelper.PageInfo;
import com.zyy.pinyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ItemCatService extends CoreService<TbItemCat> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbItemCat> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbItemCat> findPage(Integer pageNo, Integer pageSize, TbItemCat ItemCat);


	List<TbItemCat> findByParentId(Long id);

    void updateStatus(String status, Long[] ids);

    void add(List<TbItemCat> itemCatList);
}
