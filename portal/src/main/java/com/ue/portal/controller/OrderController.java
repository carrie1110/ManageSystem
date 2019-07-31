package com.ue.portal.controller;

import com.alibaba.fastjson.JSONObject;
import com.ue.portal.service.OrderService;
import com.ue.portal.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hxy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 查询文章列表
	 */
	@RequiresPermissions("order:list")
	@GetMapping("/listOrder")
	public JSONObject listArticle(HttpServletRequest request) {
		return orderService.listOrder(CommonUtil.request2Json(request));
	}

	/**
	 * 新增文章
	 */
	@RequiresPermissions("order:add")
	@PostMapping("/addOrder")
	public JSONObject addArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "content");
		return orderService.addOrder(requestJson);
	}

	/**
	 * 修改文章
	 */
	@RequiresPermissions("order:update")
	@PostMapping("/updateOrder")
	public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "id,content");
		return orderService.updateOrder(requestJson);
	}
	@RequiresPermissions("order:remove")
	@PostMapping("/removeOrder")
	public JSONObject removeArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "id");
		return orderService.removeOrder(requestJson);
	}
}
