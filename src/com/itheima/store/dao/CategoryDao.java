package com.itheima.store.dao;

import java.util.List;

import com.itheima.store.domain.Category;

public interface CategoryDao {

	List<Category> findAllCate()throws Exception;

	void delCartByCid(String cid)throws Exception;

	void addCat(Category category)throws Exception;

	void updateCat(Category category)throws Exception;

}
