package com.itheima.store.service.impl;

import java.sql.SQLException;

import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.UserDaoImpl;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.MailUtils;

/*
 * 用户模块的Service层实现类
 */
public class UserServiceImpl implements UserService{

	@Override
	public void regist(User user) throws Exception {
		//保存用户
		UserDao userDaoImpl = new UserDaoImpl();
		userDaoImpl.save(user);
		//发邮件
		MailUtils.sendMail(user.getEmail(), user.getCode());
	}
	
	
	
	@Override
	public User active(String code) throws Exception {
		UserDao userDaoImpl = new UserDaoImpl();
		return userDaoImpl.active(code);
	}



	@Override
	public void updateState(User user) throws Exception {
		UserDao userDaoImpl = new UserDaoImpl();
		userDaoImpl.updateState(user);
	}



	@Override
	public User login(User loginUser) throws Exception {
		UserDao userDaoImpl = new UserDaoImpl();
		return userDaoImpl.login(loginUser);
	}



	@Override
	public User findByUsername(String username) throws Exception {
		UserDao userDaoImpl = new UserDaoImpl();
		return userDaoImpl.findByUsername(username);
	}
	
}
