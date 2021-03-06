package com.zyy.pinyougou.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; 
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.zyy.pinyougou.core.service.CoreServiceImpl;

import tk.mybatis.mapper.entity.Example;

import com.zyy.pinyougou.mapper.TbBrandMapper;
import com.zyy.pinyougou.pojo.TbBrand;

import com.zyy.pinyougou.sellergoods.service.BrandService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class BrandServiceImpl extends CoreServiceImpl<TbBrand>  implements BrandService {

	
	private TbBrandMapper brandMapper;

	@Autowired
	public BrandServiceImpl(TbBrandMapper brandMapper) {
		super(brandMapper, TbBrand.class);
		this.brandMapper=brandMapper;
	}

	@Override
    public PageInfo<TbBrand> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbBrand> all = brandMapper.selectAll();
        PageInfo<TbBrand> info = new PageInfo<TbBrand>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbBrand> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbBrand> findPage(Integer pageNo, Integer pageSize, TbBrand brand) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();

        if(brand!=null){			
			if(StringUtils.isNotBlank(brand.getName())){
				criteria.andLike("name","%"+brand.getName()+"%");
				//criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(StringUtils.isNotBlank(brand.getFirstChar())){
				criteria.andLike("firstChar","%"+brand.getFirstChar()+"%");
				//criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}
            if(StringUtils.isNotBlank(brand.getStatus())){
                criteria.andEqualTo("status",brand.getStatus());
                //criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
	
		}
        List<TbBrand> all = brandMapper.selectByExample(example);
        PageInfo<TbBrand> info = new PageInfo<TbBrand>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbBrand> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }

    @Override
    public void add(List<TbBrand> brandList) {
        for (TbBrand tbBrand : brandList) {
            if (tbBrand != null) {
                add(tbBrand);
            }
        }
    }

    //通过品牌的id修改品牌的状态
    @Override
    public void updateStatus(String status, Long[] ids) {
        for (Long id : ids) {
            TbBrand brand = brandMapper.selectByPrimaryKey(id);
            brand.setStatus(status);
            brandMapper.updateByPrimaryKey(brand);
        }
    }
}
