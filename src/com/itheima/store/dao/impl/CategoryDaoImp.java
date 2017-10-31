package com.itheima.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.domain.Category;
import com.itheima.store.utils.JDBCUtils;

public class CategoryDaoImp implements com.itheima.store.dao.CategoryDao {

	@Override
	public List<Category> findAllCate() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void delCartByCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="delete from category where cid = ?";
		 int update = qr.update(sql, cid);
	}

	@Override
	public void addCat(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="insert into category values(?,?)";
		qr.update(sql, category.getCid(),category.getCname());
	}

	@Override
	public void updateCat(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="update category set cname =? where cid = ?";
		qr.update(sql, category.getCname(),category.getCid());
	}
	
	
}
