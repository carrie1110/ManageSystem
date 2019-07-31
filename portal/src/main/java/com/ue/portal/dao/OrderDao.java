package com.ue.portal.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author: hxy
 * @description: 文章Dao层
 * @date: 2017/10/24 16:06
 */
@Mapper
public interface OrderDao {
	/**
	 * 新增文章
	 */
	int addOrder(JSONObject jsonObject);

	/**
	 * 统计文章总数
	 */
	int countOrder(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	List<JSONObject> listOrder(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	int updateOrder(JSONObject jsonObject);
	/**
	 * 删除文章
	 */
	int removeOrder(JSONObject jsonObject);
}
