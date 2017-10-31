package com.itheima.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.CookUtils;


public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 0 强转
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//如果是登录页直接放行
		String ServletPath = req.getContextPath();
		if(ServletPath.startsWith("/UserServlet")){
			String method = req.getParameter("method");
			if("loginUI".equals(method)){
				chain.doFilter(req, resp);
				return;
			}
		}
		
		// 1.用户登录信息
		User loginUser =  (User) req.getSession().getAttribute("loginUser");
		
		// 2.如果已经登录放行,不需要自动登录
		if(null != loginUser){
			chain.doFilter(req, resp);
			return;//程序结束
		}
		
		// 3.获得自动登录cookie信息
		Cookie autoLoginCookie = CookUtils.getCookieByName("autoLoginCookie", req.getCookies());
		
		// 4.判断自动登录cookie是否存在,如果没有cookie不需要自动登录
		if(null == autoLoginCookie){
			chain.doFilter(req, resp);
			return;
		}
		
		// 5.通过用户的Cookie记录查询用户
		String[] split = autoLoginCookie.getValue().split("@");
		String username =split[0];
		String password =split[1];
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		try {
			// 5.2 执行登录
			UserService userService = new UserServiceImpl();
			loginUser = userService.login(user);
			// 6. 如果没有则返回
			if(null == loginUser){
				chain.doFilter(req, resp);
				return;
			}
			
			// 7.自动登录
			req.getSession().setAttribute("loginUser", loginUser);
			//放行
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
