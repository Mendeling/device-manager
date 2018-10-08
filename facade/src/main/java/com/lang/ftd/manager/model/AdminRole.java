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
 * @Title:角色管理Entity
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
public class AdminRole implements Serializable {
	private Integer id ;
    private String roleName;//角色名称
    private Integer type;//区分角色类型（0：系统管理员：1：其他）
    private String description;//描述

}
