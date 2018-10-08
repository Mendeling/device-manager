package com.lang.ftd.controller;

import com.lang.ftd.entity.ResponseResult;
import common.tools.mybatisUtils.pagination.BaseModel;
import common.tools.mybatisUtils.pagination.PageMyBatis;
import lombok.extern.slf4j.Slf4j;
import com.lang.ftd.manager.model.AdminUserRole;
import com.lang.ftd.manager.service.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * @Title:用户角色管理Controller
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@RestController
@Slf4j
@RequestMapping("/adminUserRole")
public class AdminUserRoleController {

	
	@Autowired
	private AdminUserRoleService adminUserRoleService;


	@RequestMapping(method=RequestMethod.GET)
	public ResponseResult<PageMyBatis<AdminUserRole>> list(BaseModel baseModel, AdminUserRole adminUserRole){
		return ResponseResult.success(adminUserRoleService.findByPage(baseModel,adminUserRole));
	}
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseResult<AdminUserRole> one(@PathVariable("id")Integer id){
		return ResponseResult.success(adminUserRoleService.get(id));
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseResult delete(@PathVariable("id")Integer id){
		adminUserRoleService.delete(id);
		return ResponseResult.success();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseResult<AdminUserRole> update(AdminUserRole adminUserRole){
		adminUserRoleService.update4Selective(adminUserRole);
		return ResponseResult.success(adminUserRole);
				
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseResult<AdminUserRole> add(AdminUserRole adminUserRole){
		adminUserRoleService.create(adminUserRole);
		return ResponseResult.success(adminUserRole);
	}
}
