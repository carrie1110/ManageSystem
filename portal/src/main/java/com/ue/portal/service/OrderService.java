package com.ue.portal.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface OrderService {
	/**
	 * 新增文章
	 */
	JSONObject addOrder(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	JSONObject listOrder(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	JSONObject updateOrder(JSONObject jsonObject);
	
	JSONObject removeOrder(JSONObject jsonObject);
}
