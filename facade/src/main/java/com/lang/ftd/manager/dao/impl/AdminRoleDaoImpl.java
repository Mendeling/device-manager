package com.lang.ftd.manager.dao.impl;

import org.springframework.stereotype.Repository;

import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.dbtools.base.dao.impl.BaseDaoImpl;
import com.lang.ftd.manager.dao.AdminRoleDao;
import com.lang.ftd.manager.model.AdminRole;

@Repository("adminRoleDaoImpl")
public class AdminRoleDaoImpl extends BaseDaoImpl<AdminRole> implements AdminRoleDao {
	private static final String SELECT_BY_PAGE = "com.lang.ftd.manager.model.AdminRole.selectByPage";
	
	@Override
	public PageMyBatis<AdminRole> selectByPage(PagingCriteria criteria) {
		return (PageMyBatis)( getSqlSession().selectList(SELECT_BY_PAGE, criteria));
	}
}
