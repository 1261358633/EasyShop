package com.itheima.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao{

	@Override
	public void save(User u) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="INSERT INTO user(uid,username,password,name,email,telephone,birthday,sex,state,code) VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),
							u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode()};
		qr.update(sql, params);
	
	}

	@Override
	public User active(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="select * from user where code = ?";
		return qr.query(sql, new BeanHandler<User>(User.class),code);
	}

	@Override
	public void updateState(User u) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="UPDATE USER SET	username = ? ,PASSWORD = ? ,NAME = ? , email = ?, telephone =? ,birthday = ? ,sex =?,state=?,CODE=? WHERE uid =?";
		Object[] params = {u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),
				u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode(),u.getUid()};
		qr.update(sql, params);
	}

	@Override
	public User login(User loginUser) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="select * from user where username = ? and password = ?";
		Object[] params ={loginUser.getUsername(),loginUser.getPassword()};
		return qr.query(sql, new BeanHandler<User>(User.class), params);
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="select * from user where username = ?";
		
		return qr.query(sql, new BeanHandler<User>(User.class), username);
	}
	
}
