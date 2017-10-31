package com.itheima.store.service.impl;

import java.util.List;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.dao.impl.ProductDaoImp;
import com.itheima.store.domain.PageModel;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;

public class ProductServiceImp implements ProductService {

	ProductDao productDao = new ProductDaoImp();
	
	@Override
	public List<Product> find9News() throws Exception {
		
		return productDao.find9News();
	}

	@Override
	public List<Product> find9Hots() throws Exception {
		return productDao.find9Hots();
	}
	
	@Override
	public Product findProductByPid(String pid) throws Exception {
		
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProByCidWithPage(String num, String cid) throws Exception {
		//查询所有的cid 的物品个数
		int allNum = productDao.findProByCid(cid);
		int num2 = Integer.parseInt(num);
		//创建PageModel,计算好分页参数
		PageModel pageModel = new PageModel(num2, allNum, 12);
		//关联集合
		List<Product> list = productDao.findListProByCid(cid,pageModel.getStartIndex(),pageModel.getPageSize());
		pageModel.setList(list);
		
		//关联url
		pageModel.setUrl("ProductServlet?method=findProByCidWithPage&cid="+cid);
		return pageModel;
	}

	@Override
	public PageModel findProPageRows(String page, String rows) throws Exception {
		//查询所有的cid 的物品个数
		int allNum = productDao.findProCount();
		int num2 = Integer.parseInt(page);
		//创建PageModel,计算好分页参数
		PageModel pageModel = new PageModel(num2, allNum, Integer.parseInt(rows));
		//关联集合
		List<Product> list = productDao.findListPros(pageModel.getStartIndex(),pageModel.getPageSize());
		pageModel.setList(list);
		
		return pageModel;
	}

	@Override
	public void addPro(Product product) throws Exception {
		productDao.addPro(product);
	}
	
}
