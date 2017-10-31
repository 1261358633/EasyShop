package com.itheima.store.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Orderitem;
import com.itheima.store.domain.Orders;
import com.itheima.store.domain.Product;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;

public class OrderDaoImp implements OrderDao {
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	@Override
	public void addOrder( Orders o) throws Exception {
		Connection connection = JDBCUtils.getConnection();
		String sql ="INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		Object[] params ={o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getUser().getUid()};
		qr.update(connection, sql, params);
	}

	@Override
	public void addOrderItem(Orderitem o) throws Exception {
		Connection connection = JDBCUtils.getConnection();
		String sql ="INSERT INTO orderitem VALUES(?,?,?,?,?)";
		Object[] params ={o.getItemid(),o.getQuantity(),o.getTotal(),o.getProduct().getPid(),o.getOrders().getOid()};
		qr.update(connection, sql, params);
	}

	@Override
	public Orders findOrderByOid(String oid) throws Exception {
		String sql ="SELECT * FROM orders WHERE oid = ?";
		Orders orders = qr.query(sql, new BeanHandler<Orders>(Orders.class),oid);
		//SELECT * FROM orderitem o,product p WHERE o.pid = p.pid WHERE o.oid = ?
		List<Orderitem> list = getOrderItems(oid);
		orders.setList(list);
		return orders;
	}

	private List<Orderitem> getOrderItems(String oid)
			throws SQLException, IllegalAccessException, InvocationTargetException {
		List<Orderitem> list = new ArrayList<Orderitem>();
		String sql ="SELECT * FROM orderitem o,product p WHERE o.pid = p.pid and o.oid = ?";
		List<Map<String, Object>> items = qr.query(sql, new MapListHandler(), oid);
		System.out.println(items);
		for (Map<String, Object> map : items) {
			Orderitem orderitem = new Orderitem();
			Product product = new Product();
			BeanUtils.populate(product, map);
			BeanUtils.populate(orderitem, map);
			orderitem.setProduct(product);
			list.add(orderitem);
		}
		return list;
	}

	@Override
	public int findTotalRecords(User user) throws Exception {
		//select count(*) from orders where uid = ?
		String sql = "select count(*) from orders where uid = ?";
		Long num = (Long) qr.query(sql, new ScalarHandler(),user.getUid());
		return num.intValue();
	}

	@Override
	public List<Orders> findMyOrderWithPageByUid(User user, int startIndex, int pageSize) throws Exception {
		//select * from orders where uid = ? limt ?,?
		String sql = "select * from orders where uid = ? limit ?,?";
		Object[] params = {user.getUid(),startIndex,pageSize};
		List<Orders> list2 = qr.query(sql, new BeanListHandler<Orders>(Orders.class), params);
		for (Orders orders : list2) {
			List<Orderitem> orderItems = getOrderItems(orders.getOid());
			orders.setList(orderItems);
		}
		return list2;
	}

	@Override
	public void updateOrder(Orders o) throws Exception {
		String sql ="UPDATE orders SET ordertime= ? , total = ?  ,  state = ? , address = ? , NAME= ? , telephone = ? WHERE oid=?";
		Object[] params ={o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getOid()};
		qr.update(sql, params);
	}
	
	
	
}
