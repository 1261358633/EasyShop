package com.itheima.store.domain;

public class Orderitem {
	/*订单项
	 * `itemid` varchar(32) NOT NULL,
  `quantity` int(11) DEFAULT NULL,			#购买数量
  `total` double DEFAULT NULL,			#小计
  `pid` varchar(32) DEFAULT NULL,		#购买商品的id
  `oid` varchar(32) DEFAULT NULL,		#订单项所在订单id
	 */
	private String itemid;
	private int quantity;
	private double total;
	private Product product;
	private Orders orders;
	public Orderitem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orderitem(String itemid, int quantity, double total, Product product, Orders orders) {
		super();
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.orders = orders;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
}
