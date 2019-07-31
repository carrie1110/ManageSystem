package com.ue.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ue.portal.dao.OrderDao;
import com.ue.portal.service.OrderService;
import com.ue.portal.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:07
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	/**
	 * 新增文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addOrder(JSONObject jsonObject) {
		orderDao.addOrder(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 文章列表
	 */
	@Override
	public JSONObject listOrder(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = orderDao.countOrder(jsonObject);
		List<JSONObject> list = orderDao.listOrder(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrder(JSONObject jsonObject) {
		orderDao.updateOrder(jsonObject);
		return CommonUtil.successJson();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject removeOrder(JSONObject jsonObject) {
		orderDao.removeOrder(jsonObject);
		return CommonUtil.successJson();
	}
}
