package com.lang.ftd.manager.dao.impl;

import org.springframework.stereotype.Repository;

import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.dbtools.base.dao.impl.BaseDaoImpl;
import com.lang.ftd.manager.dao.AdminOperaDao;
import com.lang.ftd.manager.model.AdminOpera;

@Repository("adminOperaDaoImpl")
public class AdminOperaDaoImpl extends BaseDaoImpl<AdminOpera> implements AdminOperaDao {
	private static final String SELECT_BY_PAGE = "com.lang.ftd.manager.model.AdminOpera.selectByPage";
	
	@Override
	public PageMyBatis<AdminOpera> selectByPage(PagingCriteria criteria) {
		return (PageMyBatis)( getSqlSession().selectList(SELECT_BY_PAGE, criteria));
	}
}
