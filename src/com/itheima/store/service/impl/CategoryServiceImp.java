package com.itheima.store.service.impl;

import java.util.List;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.impl.CategoryDaoImp;
import com.itheima.store.domain.Category;

public class CategoryServiceImp implements com.itheima.store.service.CategoryService {

	@Override
	public List<Category> findAllCate() throws Exception {
		CategoryDao categoryDao = new CategoryDaoImp();
		return categoryDao.findAllCate();
	}

	@Override
	public void delCartByCid(String cid) throws Exception {
		CategoryDao categoryDao = new CategoryDaoImp();
		categoryDao.delCartByCid(cid);
	}

	@Override
	public void addCat(Category category) throws Exception {
		CategoryDao categoryDao = new CategoryDaoImp();
		categoryDao.addCat(category);
	}

	@Override
	public void updateCat(Category category) throws Exception {
		CategoryDao categoryDao = new CategoryDaoImp();
		categoryDao.updateCat(category);
	}
	
}
