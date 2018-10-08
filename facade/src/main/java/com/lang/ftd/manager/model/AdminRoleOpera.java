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
 * @Title:菜单权限管理Entity
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
public class AdminRoleOpera implements Serializable {
	private Integer id ;
    private Integer pid;//pid
    private String roleId;//角色id
    private String operId;//操作id
    private String description;//描述

}
