package com.itheima.store.dao;

import java.util.List;

import com.itheima.store.domain.Product;

public interface ProductDao {

	List<Product> find9News()throws Exception;

	List<Product> find9Hots()throws Exception;

	Product findProductByPid(String pid)throws Exception;

	int findProByCid(String cid)throws Exception;

	List<Product> findListProByCid(String cid, int i, int j)throws Exception;

	int findProCount()throws Exception;

	List<Product> findListPros(int startIndex, int pageSize)throws Exception;

	void addPro(Product product)throws Exception;

}
