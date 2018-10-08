package com.lang.ftd.controller;

import com.lang.ftd.entity.ResponseResult;
import common.tools.mybatisUtils.pagination.BaseModel;
import common.tools.mybatisUtils.pagination.PageMyBatis;
import lombok.extern.slf4j.Slf4j;
import com.lang.ftd.manager.model.AdminOpera;
import com.lang.ftd.manager.service.AdminOperaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * @Title:菜单管理Controller
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@RestController
@Slf4j
@RequestMapping("/adminOpera")
public class AdminOperaController {

	
	@Autowired
	private AdminOperaService adminOperaService;


	@RequestMapping(method=RequestMethod.GET)
	public ResponseResult<PageMyBatis<AdminOpera>> list(BaseModel baseModel, AdminOpera adminOpera){
		return ResponseResult.success(adminOperaService.findByPage(baseModel,adminOpera));
	}
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseResult<AdminOpera> one(@PathVariable("id")Integer id){
		return ResponseResult.success(adminOperaService.get(id));
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseResult delete(@PathVariable("id")Integer id){
		adminOperaService.delete(id);
		return ResponseResult.success();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseResult<AdminOpera> update(AdminOpera adminOpera){
		adminOperaService.update4Selective(adminOpera);
		return ResponseResult.success(adminOpera);
				
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseResult<AdminOpera> add(AdminOpera adminOpera){
		adminOperaService.create(adminOpera);
		return ResponseResult.success(adminOpera);
	}
}
