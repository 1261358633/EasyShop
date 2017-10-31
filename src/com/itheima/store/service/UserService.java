package com.itheima.store.service;

import java.sql.SQLException;

import com.itheima.store.domain.User;

/*
 * 用户模块的Service层的接口
 */
public interface UserService {
	void regist(User user) throws  Exception;
	/**
	 * 激活用户
	 * @return 
	 */
	User active(String code)throws  Exception;
	void updateState(User user)throws  Exception;
	User login(User loginUser)throws  Exception;
	User findByUsername(String username)throws  Exception;;
}
