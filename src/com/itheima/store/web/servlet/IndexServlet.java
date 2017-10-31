package com.itheima.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImp;
import com.itheima.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*//查询所有分类
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> list = null;
		try {
			list = categoryService.findAllCate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将查询的结果放在作用域
		request.setAttribute("allCategory", list);*/
		
		/*
		 * 查询最新9件商品返回集合
		 */
		ProductService productService = new ProductServiceImp();
		List<Product> list01 = productService.find9News();
		List<Product> list02 = productService.find9Hots();
		//将集合放入到request
		request.setAttribute("News", list01);
		request.setAttribute("Hots", list02);
		return "/jsp/index.jsp";
	}
	
}