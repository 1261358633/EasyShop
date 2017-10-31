package com.itheima.store.dao;


import java.util.List;

import com.itheima.store.domain.Orderitem;
import com.itheima.store.domain.Orders;
import com.itheima.store.domain.User;

public interface OrderDao {

	void addOrder( Orders orders)throws Exception;

	void addOrderItem(Orderitem orderitem)throws Exception;

	Orders findOrderByOid(String oid)throws Exception;

	int findTotalRecords(User user)throws Exception;

	List<Orders> findMyOrderWithPageByUid(User user, int startIndex, int pageSize)throws Exception;

	void updateOrder(Orders order)throws Exception;


}
