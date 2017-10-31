/**
 * 用户模块的DAO层接口
 */
/**
 * @author 令琦
 *
 */
package com.itheima.store.dao;

import java.sql.SQLException;

import com.itheima.store.domain.User;

public interface UserDao {
	/**
	 * 保存用户
	 */
	void save(User user) throws SQLException;

	User active(String code) throws SQLException;

	void updateState(User user)throws SQLException;

	User login(User loginUser)throws SQLException;

	User findByUsername(String username)throws SQLException;

}