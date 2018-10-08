package com.lang.ftd.manager.dao.impl;

import org.springframework.stereotype.Repository;

import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.dbtools.base.dao.impl.BaseDaoImpl;
import com.lang.ftd.manager.dao.AdminUserRoleDao;
import com.lang.ftd.manager.model.AdminUserRole;

@Repository("adminUserRoleDaoImpl")
public class AdminUserRoleDaoImpl extends BaseDaoImpl<AdminUserRole> implements AdminUserRoleDao {
	private static final String SELECT_BY_PAGE = "com.lang.ftd.manager.model.AdminUserRole.selectByPage";
	
	@Override
	public PageMyBatis<AdminUserRole> selectByPage(PagingCriteria criteria) {
		return (PageMyBatis)( getSqlSession().selectList(SELECT_BY_PAGE, criteria));
	}
}
