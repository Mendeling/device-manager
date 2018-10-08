package com.lang.ftd.manager.dao;

import common.tools.mybatisUtils.pagination.PageMyBatis;
import common.tools.mybatisUtils.pagination.PagingCriteria;
import common.tools.dbtools.base.dao.BaseDao;
import common.tools.dbtools.base.dao.BaseDao;
import com.lang.ftd.manager.model.AdminUserRole;

/**
 * @Title:用户角色管理Dao
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
public interface AdminUserRoleDao extends BaseDao<AdminUserRole> {
	PageMyBatis<AdminUserRole> selectByPage(PagingCriteria criteria);
}

