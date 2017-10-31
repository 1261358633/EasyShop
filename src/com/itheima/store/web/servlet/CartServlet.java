package com.itheima.store.web.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.Product;
import com.itheima.store.domain.User;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImp;
import com.itheima.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	public String addCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//判断用户是否等录
		User user = (User) request.getSession().getAttribute("loginUser");
		if(null == user){
			request.setAttribute("msg", "请先登录!");
			return "/jsp/info.jsp";
		}	
		
		// 获得请求参数
		String pid = request.getParameter("pid");
		String count = request.getParameter("count");
		ProductService productService = new ProductServiceImp();
		Product product = productService.findProductByPid(pid);
		
		//获取购物车
		Cart cart = getCart(request.getSession());
		
		cart.addCart(product, Integer.parseInt(count));
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	private Cart getCart(HttpSession session){
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart; 
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cart cart = getCart(request.getSession());
		cart.clearCart();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	public String removeCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		Cart cart = getCart(request.getSession());
		cart.removeCart(pid);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}
