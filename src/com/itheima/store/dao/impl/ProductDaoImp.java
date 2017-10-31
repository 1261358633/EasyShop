package com.itheima.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.JDBCUtils;

public class ProductDaoImp implements ProductDao {
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	@Override
	public List<Product> find9News() throws Exception {
		String sql ="SELECT * FROM product WHERE pflag = 0 ORDER BY pdate DESC LIMIT 0,9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> find9Hots() throws Exception {
		String sql ="SELECT * FROM product WHERE pflag = 0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0,9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		String sql ="select * from product where pid = ?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public int findProByCid(String cid) throws Exception {
		String sql ="select count(*) from product where cid=?";
		Long num = (Long) qr.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

	@Override
	public List<Product> findListProByCid(String cid, int i, int j) throws Exception {
		String sql ="select * from product where cid=? limit ? ,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,i,j);
	}

	@Override
	public int findProCount() throws Exception {
		String sql ="select count(*) from product ";
		Long num = (Long) qr.query(sql, new ScalarHandler());
		return num.intValue();
	}

	@Override
	public List<Product> findListPros(int startIndex, int pageSize) throws Exception {
		String sql ="select * from product limit ? ,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}

	@Override
	public void addPro(Product p) throws Exception {
		String sql ="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage()
				,p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCid()};
		qr.update(sql, params);
	}

	
	
	
	
}
