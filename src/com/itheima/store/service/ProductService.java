package com.itheima.store.service;

import java.util.List;

import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.Product;

public interface ProductService {

	List<Product> find9News()throws Exception;

	List<Product> find9Hots()throws Exception;

	Product findProductByPid(String pid)throws Exception;

	PageModel findProByCidWithPage(String num, String cid)throws Exception;

	PageModel findProPageRows(String page, String rows)throws Exception;

	void addPro(Product product)throws Exception;

}
