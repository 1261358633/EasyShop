package com.itheima.store.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.impl.CategoryServiceImp;
import com.itheima.store.utils.JedisUtils;
import com.itheima.store.utils.MyBeanUtils;
import com.itheima.store.utils.UUIDUtils;
import com.itheima.store.web.base.BaseServlet;

import redis.clients.jedis.Jedis;

public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取到全部分类 list<category>
		CategoryServlet categoryServlet = new CategoryServlet();
		String findAllByAjax = categoryServlet.findAllByAjax(request, response);
		return findAllByAjax;
		
	}
	public String delCartByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cid = request.getParameter("cid");
		/*
		 * 
			1_删除分类前 设置相关的商品的 cid  为 null
			2_删除出分类之前 相关的商品同时删除
			3_建立分类表的时候,多设置一个列 validate
		 */
		CategoryService CategoryService = new CategoryServiceImp();
		CategoryService.delCartByCid(cid);
		response.getWriter().print("delCatOk");
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
		return null;
		
	}
	public String addCat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		CategoryService CategoryService = new CategoryServiceImp();
		CategoryService.addCat(category);
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
		response.getWriter().print("addCatOk");
		return null;
	}
	public String updateCat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Category category = MyBeanUtils.populate(Category.class, request.getParameterMap());
		System.out.println("11111111111111111");
		CategoryService categoryService = new CategoryServiceImp();
		categoryService.updateCat(category);
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
		response.getWriter().print("updateCatOk");
		
		return null;
		
	}
}