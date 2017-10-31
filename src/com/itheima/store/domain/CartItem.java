package com.itheima.store.domain;

/**
 * 购物项
 * @author 令琦
 *
 */
public class CartItem {
	private Product product;	//购买的商品信息
	private int count;	//购买的数量
	private double subtotal;//购买商品小计 : 商品单价 * 商品数量
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getSubtotal() {
		this.subtotal = count *product.getShop_price();
		return subtotal;
	}
	
	
	//因为是计算所得 ,所以不需要 set 方法
//	public void setSubtotal(double subtotal) {
//		this.subtotal = subtotal;
//	}
}
