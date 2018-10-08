package com.lang.ftd.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import common.tools.mybatisUtils.pagination.BaseModel;
import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.mybatisUtils.pagination.SearchField;
import common.tools.mybatisUtils.pagination.SortField;
import common.tools.dbtools.base.dao.BaseDao;
import common.tools.dbtools.base.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import com.lang.ftd.manager.dao.AdminUserRoleDao;
import com.lang.ftd.manager.model.AdminUserRole;

/**
 * @Title:用户角色管理Service
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@Service("adminUserRoleService")
@Slf4j
@Transactional
public class AdminUserRoleService extends BaseService<AdminUserRole> {

	@Autowired
	private AdminUserRoleDao adminUserRoleDaoImpl;
	
	@Override
	public BaseDao<AdminUserRole> getBaseDao() {
		return adminUserRoleDaoImpl;
	}
	
	public PageMyBatis<AdminUserRole> findByPage(BaseModel baseModel,AdminUserRole adminUserRole){
		List<SortField> sortFields =  Lists.newArrayList();
		
		if(StringUtils.isEmpty(baseModel.getSort())){
			baseModel.setSort("id");
		}
		if(StringUtils.isEmpty(baseModel.getOrder())){
			baseModel.setOrder("desc");
		}
		
		sortFields.add(new SortField(baseModel.getSort(), baseModel.getOrder()));
		
		List<SearchField> searchFields = Lists.newArrayList();
		
		PagingCriteria criteria = PagingCriteria.createCriteriaWithAllParamter((baseModel.getPage()-1) * baseModel.getRows(), 
				baseModel.getRows(), baseModel.getPage(), sortFields, searchFields);
		return adminUserRoleDaoImpl.selectByPage(criteria);
	}
}
