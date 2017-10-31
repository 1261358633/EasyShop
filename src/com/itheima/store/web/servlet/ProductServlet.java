package com.itheima.store.web.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImp;
import com.itheima.store.web.base.BaseServlet;
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获得参数pid
		String pid = request.getParameter("pid");
		//调用业务层查询
		ProductService productService = new ProductServiceImp();
		Product product = productService.findProductByPid(pid);
		//将product放入到request
		request.setAttribute("pro", product);
		//转到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	
	public String findProByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cid = request.getParameter("cid");
		String num = request.getParameter("num");
		ProductService productService = new ProductServiceImp();
		PageModel page = productService.findProByCidWithPage(num,cid);
		request.setAttribute("page", page);
		return "/jsp/product_list.jsp";
	}
}
