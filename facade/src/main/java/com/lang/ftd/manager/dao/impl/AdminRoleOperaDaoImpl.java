package com.lang.ftd.manager.dao.impl;

import org.springframework.stereotype.Repository;

import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.dbtools.base.dao.impl.BaseDaoImpl;
import com.lang.ftd.manager.dao.AdminRoleOperaDao;
import com.lang.ftd.manager.model.AdminRoleOpera;

@Repository("adminRoleOperaDaoImpl")
public class AdminRoleOperaDaoImpl extends BaseDaoImpl<AdminRoleOpera> implements AdminRoleOperaDao {
	private static final String SELECT_BY_PAGE = "com.lang.ftd.manager.model.AdminRoleOpera.selectByPage";
	
	@Override
	public PageMyBatis<AdminRoleOpera> selectByPage(PagingCriteria criteria) {
		return (PageMyBatis)( getSqlSession().selectList(SELECT_BY_PAGE, criteria));
	}
}
