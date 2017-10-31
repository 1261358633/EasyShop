package com.itheima.store.service.impl;



import java.util.List;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.dao.impl.OrderDaoImp;
import com.itheima.store.domain.Orderitem;
import com.itheima.store.domain.Orders;
import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {
	OrderDao orderDao = new OrderDaoImp();

	@Override
	public void saveOrder(Orders orders) throws Exception {
		// 获得 connection
		
		try {
			JDBCUtils.startTransaction();
			//调用dao层,订单详细信息
			orderDao.addOrder(orders);
			//订单项
			for (Orderitem orderitem : orders.getList()) {
				orderDao.addOrderItem(orderitem);
			}
			//提交事务
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
			e.printStackTrace();
		}
		
	}

	@Override
	public Orders findOrderByOid(String oid) throws Exception {
		
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public PageModel findMyOrderWithPage(int num,User user) throws Exception {
		int totalRecords = orderDao.findTotalRecords(user);
		PageModel pm = new PageModel(num, totalRecords, 3);
		List<Orders> list = orderDao.findMyOrderWithPageByUid(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		pm.setUrl("OrderServlet?method=findMyOrderWithPage");
		return pm;
	}

	@Override
	public void updateOrder(Orders order) throws Exception {
		orderDao.updateOrder(order);
		
	}

}
