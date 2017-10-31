package com.itheima.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.impl.CategoryServiceImp;
import com.itheima.store.utils.JedisUtils;
import com.itheima.store.utils.JsonUtil;
import com.itheima.store.web.base.BaseServlet;

import redis.clients.jedis.Jedis;

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	public void findAllCate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = null;
		try {
			list = categoryService.findAllCate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将查询结果转换为json
		String jsonStr = JsonUtil.list2json(list);
		//将json格式的数据响应到客户端
		//D:\develop\apache-tomcat-7.0.52\conf
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
	}
	public String findAllByAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//设置编码格式
		response.setContentType("application/json;charset=utf-8");
		String jsonStr = null ;
		//获取jeson实例
		Jedis jedis = JedisUtils.getJedis();
		jsonStr = jedis.get("allCats");
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = null;
		//判断redis 中是否存在allCats对应json 格式的字符串
		if(null == jsonStr||"".equals(jsonStr)){
			System.out.println("redis缓存中不存在分类");
			//调用业务层功能查询
			list = categoryService.findAllCate();
			jsonStr = JsonUtil.list2json(list);
			//向jeson中存放所有商品的信息
			jedis.set("allCats", jsonStr);
		}else{
			//可以获取到直接响应集合
			System.out.println("redis缓存中已经存在分类");
		}
		//向客户端响应JSON格式的数据
		JedisUtils.closeJedis(jedis);
		response.getWriter().print(jsonStr);
		return null;
	}

}
