package com.itheima.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.MyBeanUtils;
import com.itheima.store.utils.UUIDUtils;
import com.itheima.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("findAll");
	}

	/*
	 * 用户注册
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/jsp/register.jsp";
	}

	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1.获得数据并封装
		User user = MyBeanUtils.populate(User.class, request.getParameterMap());
		// 1.1 处理服务器自动生成
		user.setUid(UUIDUtils.getId());
		user.setCode(UUIDUtils.getCode()); // 激活码
		user.setState(0);// 未激活
		// 2 处理
		UserService userServiceImpl = new UserServiceImpl();
		userServiceImpl.regist(user);
		// 3 成功提示
		request.setAttribute("msg", "注册成功,请邮件激活后登录");
		return "/jsp/info.jsp";

	}

	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 获取激活码
		String code = request.getParameter("code");
		// 2. 调用业务层查询用户
		UserService userService = new UserServiceImpl();
		User user = userService.active(code);
		if (null != user) {
			// 激活成功
			user.setState(1);
			user.setCode("");
			userService.updateState(user);
			request.setAttribute("msg", "激活成功,欢迎登陆");
			return "/jsp/login.jsp";

		} else {
			// 激活失败返回失败消息
			request.setAttribute("msg", "激活失败,请重新激活");
			return "/jsp/info.jsp";
		}
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/jsp/login.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User loginUser = MyBeanUtils.populate(User.class, request.getParameterMap());
		UserService userService = new UserServiceImpl();
		User user = userService.login(loginUser);
		if (null != user) {
			// 成功处理
			if (null != loginUser) {
				// #1 自动登录
				String autoLogin = request.getParameter("autoLogin");
				if ("1".equals(autoLogin)) {
					// 如果勾选就发送cookie
					Cookie autoLoginCookie = new Cookie("autoLoginCookie",
							user.getUsername() + "@" + user.getPassword());
					autoLoginCookie.setPath("/");
					autoLoginCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(autoLoginCookie);
				} else {
					// 如果没有勾选,删除Cookie
					Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
					autoLoginCookie.setPath("/");
					autoLoginCookie.setMaxAge(0);
					response.addCookie(autoLoginCookie);
				}
				
				// #2 记住用户名
				String remeberme = request.getParameter("remeberme");
				if("1".equals(remeberme)){
					//如果勾选就发送cookie
					Cookie remebermeCookie = new Cookie("remebermeCookie", user.getUsername());
					remebermeCookie.setPath("/");
					remebermeCookie.setMaxAge(60*60*24*7);
					response.addCookie(remebermeCookie);
				}else{
					//如果没有勾选,删除Cookie
					Cookie autoLoginCookie = new Cookie("remebermeCookie", "");
					autoLoginCookie.setPath("/");
					autoLoginCookie.setMaxAge(0);
					response.addCookie(autoLoginCookie);
				}
			}
			
			
			
			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect(request.getContextPath());
			return null;
		} else {
			request.setAttribute("msg", "用户名密码错误");
			return "/jsp/login.jsp";
		}

	}

	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 将 sesson 信息,cookie信息移除
		request.getSession().removeAttribute("loginUser");
		Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
		autoLoginCookie.setPath("/");
		autoLoginCookie.setMaxAge(0);
		response.addCookie(autoLoginCookie);

		// 2. 重定向到首页
		response.sendRedirect(request.getContextPath());
		return null;
	}

	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 接收文本框的值
		String username = request.getParameter("username");
		//调用业务层查询
		UserService userService = new UserServiceImpl();
		User user = userService.findByUsername(username);
		//判断
		if(null == user){
			//没有使用
			response.getWriter().print(1);
		}else{
			//使用了
			response.getWriter().print(2);
		}
	}
}
