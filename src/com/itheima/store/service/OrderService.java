package com.itheima.store.service;

import com.itheima.store.domain.Orders;
import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.User;

public interface OrderService {

	void saveOrder(Orders orders)throws Exception;

	Orders findOrderByOid(String oid)throws Exception;

	PageModel findMyOrderWithPage(int num, User user)throws Exception;

	void updateOrder(Orders order)throws Exception;

}
