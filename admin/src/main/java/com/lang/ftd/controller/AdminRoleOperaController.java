package com.lang.ftd.controller;

import com.lang.ftd.entity.ResponseResult;
import common.tools.mybatisUtils.pagination.BaseModel;
import common.tools.mybatisUtils.pagination.PageMyBatis;
import lombok.extern.slf4j.Slf4j;
import com.lang.ftd.manager.model.AdminRoleOpera;
import com.lang.ftd.manager.service.AdminRoleOperaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * @Title:菜单权限管理Controller
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@RestController
@Slf4j
@RequestMapping("/adminRoleOpera")
public class AdminRoleOperaController {

	
	@Autowired
	private AdminRoleOperaService adminRoleOperaService;


	@RequestMapping(method=RequestMethod.GET)
	public ResponseResult<PageMyBatis<AdminRoleOpera>> list(BaseModel baseModel,AdminRoleOpera adminRoleOpera){
		return ResponseResult.success(adminRoleOperaService.findByPage(baseModel,adminRoleOpera));
	}
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseResult<AdminRoleOpera> one(@PathVariable("id")Integer id){
		return ResponseResult.success(adminRoleOperaService.get(id));
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseResult delete(@PathVariable("id")Integer id){
		adminRoleOperaService.delete(id);
		return ResponseResult.success();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseResult<AdminRoleOpera> update(AdminRoleOpera adminRoleOpera){
		adminRoleOperaService.update4Selective(adminRoleOpera);
		return ResponseResult.success(adminRoleOpera);
				
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseResult<AdminRoleOpera> add(AdminRoleOpera adminRoleOpera){
		adminRoleOperaService.create(adminRoleOpera);
		return ResponseResult.success(adminRoleOpera);
	}
}
