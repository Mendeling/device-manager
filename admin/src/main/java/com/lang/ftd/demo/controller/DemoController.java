package com.lang.ftd.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.exception.AppException;
import common.exception.AppExceptionEnum;

@RestController
@Slf4j
public class DemoController {

	@RequestMapping(value="/demo" ,method={RequestMethod.GET,RequestMethod.POST})
	public String welcome(HttpServletRequest request,HttpServletResponse response){
		log.info("-------------TEST-------------");
		throw new AppException(AppExceptionEnum.FileIsNull);
	}
}
