package com.itheima.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1  获得请求参数 method
			String methodName = request.getParameter("method");
			// 默认方法名
			if(null==methodName){
				methodName = "execute";
			}
			// 2 获得当前运行类,需要指定的方法
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			// 3 执行方法
			String jspPath = (String) method.invoke(this, request,response);
			// 4 如果子类有返回值,请求到指定的jsp页面
			if(null != jspPath){
				request.getRequestDispatcher(jspPath).forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws  Exception{
		// NOOP (NO OPeration , 无操作)
		return null;
	}
}