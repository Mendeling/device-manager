package com.lang.ftd.manager.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Title:菜单管理Entity
 * @Description: TODO
 * @author 郎骏
 * @since 2018-10-08 18:24:08
 * @version V1.0  
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminOpera implements Serializable {
	private Integer id ;
    private String name;//名称
    private String url;//请求地址
    private String operaType;//操作类型：menu；菜单；add：增肌按钮；delete：删除；update：修改；all：全部查询；one：查询单个；upload：上传

}
