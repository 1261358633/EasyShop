package com.itheima.store.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Orders {
	/*订单详细信息
	 *  `oid` varchar(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,		#下单时间
  `total` double DEFAULT NULL,				#总价
  `state` int(11) DEFAULT NULL,				#订单状态：1=未付款;2=已付款,未发货;3=已发货,没收货;4=收货,订单结束
  `address` varchar(30) DEFAULT NULL,		#收获地址
  `name` varchar(20) DEFAULT NULL,			#收获人
  `telephone` varchar(20) DEFAULT NULL,		#收货人电话
  `uid` varchar(32) DEFAULT NULL,
	 */
	
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;
	private User user;
	
	
	//订单项集合
	private List<Orderitem> list = new ArrayList<Orderitem>();
	
	
	public List<Orderitem> getList() {
		return list;
	}
	public void setList(List<Orderitem> list) {
		this.list = list;
	}
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orders(String oid, Date ordertime, double total, int state, String address, String name, String telephone,
			User user) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.user = user;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
