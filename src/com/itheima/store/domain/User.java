package com.itheima.store.domain;

import java.sql.Date;

public class User {
	/*
	 * `uid` varchar(32) NOT NULL,
	  `username` varchar(20) DEFAULT NULL,		#用户名
	  `password` varchar(20) DEFAULT NULL,		#密码
	  `name` varchar(20) DEFAULT NULL,			#昵称
	  `email` varchar(30) DEFAULT NULL,			#电子邮箱
	  `telephone` varchar(20) DEFAULT NULL,		#电话
	  `birthday` date DEFAULT NULL,				#生日
	  `sex` varchar(10) DEFAULT NULL,			#性别
	  `state` int(11) DEFAULT 0,				#状态：0=未激活，1=已激活
	  `code` varchar(64) DEFAULT NULL,			#激活码
	 */
	private String uid; 
	private String  username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private Date birthday;
	private String sex;
	private int state;
	private String code;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String uid, String username, String password, String name, String email, String telephone,
			Date birthday, String sex, int state, String code) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.birthday = birthday;
		this.sex = sex;
		this.state = state;
		this.code = code;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
