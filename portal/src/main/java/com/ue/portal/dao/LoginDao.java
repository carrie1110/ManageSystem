package com.ue.portal.dao;

import com.alibaba.fastjson.JSONObject;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: hqq
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
@Mapper
public interface LoginDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	JSONObject getUser(@Param("username") String username, @Param("password") String password);
}
