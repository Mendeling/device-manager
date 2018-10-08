package com.lang.ftd.controller;

import com.lang.ftd.entity.ResponseResult;
import common.tools.mybatisUtils.pagination.BaseModel;
import common.tools.mybatisUtils.pagination.PageMyBatis;
import lombok.extern.slf4j.Slf4j;
import com.lang.ftd.manager.model.AdminRole;
import com.lang.ftd.manager.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * @Title:角色管理Controller
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@RestController
@Slf4j
@RequestMapping("/adminRole")
public class AdminRoleController {

	
	@Autowired
	private AdminRoleService adminRoleService;


	@RequestMapping(method=RequestMethod.GET)
	public ResponseResult<PageMyBatis<AdminRole>> list(BaseModel baseModel, AdminRole adminRole){
		return ResponseResult.success(adminRoleService.findByPage(baseModel,adminRole));
	}
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseResult<AdminRole> one(@PathVariable("id")Integer id){
		return ResponseResult.success(adminRoleService.get(id));
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseResult delete(@PathVariable("id")Integer id){
		adminRoleService.delete(id);
		return ResponseResult.success();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseResult<AdminRole> update(AdminRole adminRole){
		adminRoleService.update4Selective(adminRole);
		return ResponseResult.success(adminRole);
				
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseResult<AdminRole> add(AdminRole adminRole){
		adminRoleService.create(adminRole);
		return ResponseResult.success(adminRole);
	}
}
